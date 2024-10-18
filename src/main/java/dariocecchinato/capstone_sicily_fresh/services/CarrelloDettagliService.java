package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.*;
import dariocecchinato.capstone_sicily_fresh.enums.StatoOrdine;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelliRepository;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelloDettagliRepository;
import dariocecchinato.capstone_sicily_fresh.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarrelloDettagliService {
    @Autowired
    private CarrelloDettagliRepository carrelloDettagliRepository;
    @Autowired
    private CarrelliService carrelliService;
    @Autowired
    private RicetteService ricetteService;
    @Autowired
    private AbbonamentiService abbonamentiService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private MailgunSender mailgunSender;


    public CarrelloDettaglio creaCarrelloDettaglio(CarrelloDettaglioPayloadDTO body) {

        Carrello carrello = this.carrelliService.findById(body.carrello());
        Ricetta ricetta = this.ricetteService.findById(body.ricetta());

        Utente cliente = this.utentiService.findUtenteById(carrello.getCliente().getId());
        UUID clienteId = cliente.getId();
        List<Abbonamento> abbonamenti = abbonamentiService.findByClienteId(clienteId);

        if (abbonamenti.isEmpty()) {
            throw new BadRequestException("Nessun abbonamento trovato per questo cliente.");
        }
        int totaleRicetteDisponibili = abbonamenti.stream()
                .map(abbonamento -> abbonamento.getNumeroRicette())
                .reduce(0, (subtotal, numeroRicette) -> subtotal + numeroRicette);
        int quantitaDaAggiungere = body.quantita();

        if (totaleRicetteDisponibili < quantitaDaAggiungere) {
            throw new BadRequestException("Non ci sono abbastanza ricette disponibili nell'abbonamento.");
        }
        int ricetteDaSottrarre = quantitaDaAggiungere;
        for (Abbonamento abbonamento : abbonamenti) {
            int ricetteDisponibili = abbonamento.getNumeroRicette();
            if (ricetteDisponibili >= ricetteDaSottrarre) {

                abbonamento.setNumeroRicette(ricetteDisponibili - ricetteDaSottrarre);
                abbonamentiService.updateAbbonamento(abbonamento);
                break;
            } else {
                abbonamento.setNumeroRicette(0);
                abbonamentiService.updateAbbonamento(abbonamento);
                ricetteDaSottrarre -= ricetteDisponibili;
            }
        }

        StatoOrdine statoOrdine = StatoOrdine.valueOf("INCARRELLO");
        
        CarrelloDettaglio carrelloDettaglio = new CarrelloDettaglio(carrello, ricetta, quantitaDaAggiungere, statoOrdine);
        CarrelloDettaglio savedCarrelloDettaglio = this.carrelloDettagliRepository.save(carrelloDettaglio);

        return savedCarrelloDettaglio;
    }


    public Page<CarrelloDettaglio> getAll(int page, int size, String sortby){
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return this.carrelloDettagliRepository.findAll(pageable);
    }

    public List<CarrelloDettaglio> findByCarrelloId(UUID carrelloId){
        return this.carrelloDettagliRepository.findByCarrelloId(carrelloId);
    }

    public void deleteCarrelloDettaglio(UUID carrelloDettaglioId) {
        CarrelloDettaglio found = this.carrelloDettagliRepository.findById(carrelloDettaglioId)
                .orElseThrow(() -> new NotFoundException(carrelloDettaglioId));

        Carrello carrello = found.getCarrello();
        Utente cliente = this.utentiService.findUtenteById(carrello.getCliente().getId());
        UUID clienteId = cliente.getId();

        List<Abbonamento> abbonamenti = abbonamentiService.findByClienteId(clienteId);

        if (abbonamenti.isEmpty()) {
            throw new BadRequestException("Nessun abbonamento trovato per questo cliente.");
        }

        int quantitaDaRestituire = found.getQuantita();

        for (Abbonamento abbonamento : abbonamenti) {
            int ricetteDisponibili = abbonamento.getNumeroRicette();
            abbonamento.setNumeroRicette(ricetteDisponibili + quantitaDaRestituire);
            abbonamentiService.updateAbbonamento(abbonamento);
            break;
        }
        this.carrelloDettagliRepository.delete(found);
    }

    public List<CarrelloDettaglio> findByRicettaId(UUID ricettaId){
        return this.carrelloDettagliRepository.findByRicettaId(ricettaId);
    }

    public List<CarrelloDettaglio> findByRicettaIdAndStatoOrdine(UUID ricettaId, StatoOrdine statoOrdine){
        return this.carrelloDettagliRepository.findByRicettaIdAndStatoOrdine(ricettaId,statoOrdine);
    }

    public CarrelloDettaglio aggiornaStatoOrdine(UUID carrelloDettaglioId, StatoOrdine nuovoStatoOrdine) {
        CarrelloDettaglio carrelloDettaglio = carrelloDettagliRepository.findById(carrelloDettaglioId)
                .orElseThrow(() -> new NotFoundException("CarrelloDettaglio non trovato per id: " + carrelloDettaglioId));

        carrelloDettaglio.setStatoOrdine(nuovoStatoOrdine);

        CarrelloDettaglio carrelloDettaglioSalvato = this.carrelloDettagliRepository.save(carrelloDettaglio);

        if (nuovoStatoOrdine == StatoOrdine.valueOf("ORDINATO")){
            Carrello carrello = carrelloDettaglio.getCarrello();
            Utente cliente = carrello.getCliente();

            this.mailgunSender.sendOrdineRicetteEmail(cliente);
        }

        return carrelloDettaglioSalvato;
    }

    public List<CarrelloDettaglio> findCarrelloDettagliByFornitoreId(UUID fornitoreId) {
        List<Ricetta> ricetteDelFornitore = ricetteService.findRicetteByFornitoreId(fornitoreId);


        List<UUID> ricettaIds = ricetteDelFornitore.stream()
                .map(Ricetta::getId)
                .collect(Collectors.toList());
        return carrelloDettagliRepository.findByRicettaIdIn(ricettaIds);
    }
}

