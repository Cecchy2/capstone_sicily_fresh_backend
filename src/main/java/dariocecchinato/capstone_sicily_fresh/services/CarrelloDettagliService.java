package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Abbonamento;
import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.CarrelloDettaglio;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelliRepository;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelloDettagliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

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


    public CarrelloDettaglio creaCarrelloDettaglio(CarrelloDettaglioPayloadDTO body) {
        Carrello carrello = this.carrelliService.findById(body.carrello());
        Ricetta ricetta = this.ricetteService.findById(body.ricetta());

        UUID clienteId = carrello.getCliente().getId();
        Abbonamento abbonamento = abbonamentiService.findByClienteId(clienteId);

        int quantitaDaAggiungere = body.quantita();
        if (abbonamento.getNumeroRicette() < quantitaDaAggiungere) {
            throw new BadRequestException("Non ci sono abbastanza ricette disponibili nell'abbonamento.");
        }

        CarrelloDettaglio carrelloDettaglio = new CarrelloDettaglio(carrello, ricetta, quantitaDaAggiungere);
        CarrelloDettaglio savedCarrelloDettaglio = this.carrelloDettagliRepository.save(carrelloDettaglio);

        abbonamento.setNumeroRicette(abbonamento.getNumeroRicette() - quantitaDaAggiungere);
        abbonamentiService.updateAbbonamento(abbonamento);

        return savedCarrelloDettaglio;
    }

    public Page<CarrelloDettaglio> getAll(int page, int size, String sortby){
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return this.carrelloDettagliRepository.findAll(pageable);
    }
}
