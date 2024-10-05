package dariocecchinato.capstone_sicily_fresh.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dariocecchinato.capstone_sicily_fresh.entities.*;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicettePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.PassaggiDiPreparazioneRepository;
import dariocecchinato.capstone_sicily_fresh.repositories.RicetteIngredientiRepository;
import dariocecchinato.capstone_sicily_fresh.repositories.RicetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RicetteService {
    @Autowired
    private RicetteRepository ricetteRepository;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private PassaggiDiPreparazioneRepository passaggiDiPreparazioneRepository;
    @Autowired
    private RicetteIngredientiRepository ricetteIngredientiRepository;
    @Autowired
    private IngredientiService ingredientiService;
    @Autowired
    private Cloudinary cloudinary;


    public Ricetta salvaRicetta(RicettePayloadDTO body) {

        if (ricetteRepository.existsByTitolo(body.titolo())) {
            throw new BadRequestException("La ricetta " + body.titolo() + " è già presente");
        }

        Utente fornitore = utentiService.findUtenteById(body.fornitoreId());
        if (fornitore == null) {
            throw new BadRequestException("Il fornitore non è stato trovato con l'id: " + body.fornitoreId());
        }

        String immaginePiatto = body.immaginePiatto() != null ? body.immaginePiatto() : "https://placehold.co/600x400";
        Ricetta ricetta = new Ricetta();
        ricetta.setTitolo(body.titolo());
        ricetta.setDescrizione(body.descrizione());
        ricetta.setImmaginePiatto(immaginePiatto);
        ricetta.setDifficolta(body.difficolta());
        ricetta.setTempo(body.tempo());
        ricetta.setValoriNutrizionali(body.valoriNutrizionali());
        ricetta.setFornitore(fornitore);

        Ricetta savedRicetta = ricetteRepository.save(ricetta);


        if (body.passaggi() != null && !body.passaggi().isEmpty()) {
            List<PassaggioDiPreparazione> passaggi = body.passaggi().stream()
                    .map(passaggioDTO -> {
                        PassaggioDiPreparazione passaggio = new PassaggioDiPreparazione();
                        passaggio.setDescrizione(passaggioDTO.descrizione());
                        passaggio.setImmaginePassaggio(passaggioDTO.immaginePassaggio());
                        passaggio.setOrdinePassaggio(passaggioDTO.ordinePassaggio());
                        passaggio.setRicetta(savedRicetta);
                        return passaggiDiPreparazioneRepository.save(passaggio);
                    })
                    .collect(Collectors.toList());

            savedRicetta.setPassaggi(passaggi);
        }


        if (body.ricetteIngredienti() != null && !body.ricetteIngredienti().isEmpty()) {
            List<RicettaIngrediente> ricettaIngredienti = body.ricetteIngredienti().stream()
                    .map(ingredienteDTO -> {

                        Ingrediente ingrediente;
                        try {

                            ingrediente = ingredientiService.findByNome(ingredienteDTO.nome());
                        } catch (NotFoundException e) {

                            IngredientiPayloadDTO ingredientePayloadDTO = new IngredientiPayloadDTO(
                                    ingredienteDTO.nome(),
                                    ingredienteDTO.descrizione(),
                                    ingredienteDTO.valoriNutrizionali(),
                                    ingredienteDTO.immagine() != null ? ingredienteDTO.immagine() : "https://placehold.co/600x400"
                            );

                            ingrediente = ingredientiService.saveIngrediente(ingredientePayloadDTO);
                        }


                        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente();
                        ricettaIngrediente.setRicetta(savedRicetta);
                        ricettaIngrediente.setIngrediente(ingrediente);
                        ricettaIngrediente.setQuantita(ingredienteDTO.quantita());
                        return ricetteIngredientiRepository.save(ricettaIngrediente);
                    })
                    .collect(Collectors.toList());

            savedRicetta.setRicettaIngredienti(ricettaIngredienti);
        }

        return savedRicetta;
    }





    public Ricetta aggiornaRicetta(UUID ricettaId, RicettePayloadDTO body) {

        Ricetta ricettaEsistente = ricetteRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta non trovata con id: " + ricettaId));

        if (!ricettaEsistente.getTitolo().equals(body.titolo()) && ricetteRepository.existsByTitolo(body.titolo())) {
            throw new BadRequestException("La ricetta " + body.titolo() + " è già presente");
        }

        Utente fornitore = utentiService.findUtenteById(body.fornitoreId());
        if (fornitore == null) {
            throw new BadRequestException("Il fornitore non è stato trovato con l'id: " + body.fornitoreId());
        }

        ricettaEsistente.setTitolo(body.titolo());
        ricettaEsistente.setDescrizione(body.descrizione());
        ricettaEsistente.setImmaginePiatto(body.immaginePiatto() != null ? body.immaginePiatto() : "https://placehold.co/600x400");
        ricettaEsistente.setDifficolta(body.difficolta());
        ricettaEsistente.setTempo(body.tempo());
        ricettaEsistente.setValoriNutrizionali(body.valoriNutrizionali());
        ricettaEsistente.setFornitore(fornitore);

        if (body.passaggi() != null && !body.passaggi().isEmpty()) {
            passaggiDiPreparazioneRepository.deleteAll(ricettaEsistente.getPassaggi());

            List<PassaggioDiPreparazione> passaggi = body.passaggi().stream()
                    .map(passaggioDTO -> {
                        PassaggioDiPreparazione passaggio = new PassaggioDiPreparazione();
                        passaggio.setDescrizione(passaggioDTO.descrizione());
                        passaggio.setImmaginePassaggio(passaggioDTO.immaginePassaggio());
                        passaggio.setOrdinePassaggio(passaggioDTO.ordinePassaggio());
                        passaggio.setRicetta(ricettaEsistente);
                        return passaggiDiPreparazioneRepository.save(passaggio);
                    })
                    .collect(Collectors.toList());

            ricettaEsistente.setPassaggi(passaggi);
        }
        if (body.ricetteIngredienti() != null && !body.ricetteIngredienti().isEmpty()) {
            ricetteIngredientiRepository.deleteAll(ricettaEsistente.getRicettaIngredienti());

            List<RicettaIngrediente> ricettaIngredienti = body.ricetteIngredienti().stream()
                    .map(ingredienteDTO -> {
                        Ingrediente ingrediente;
                        try {

                            ingrediente = ingredientiService.findByNome(ingredienteDTO.nome());
                        } catch (NotFoundException e) {

                            IngredientiPayloadDTO ingredientePayloadDTO = new IngredientiPayloadDTO(
                                    ingredienteDTO.nome(),
                                    ingredienteDTO.descrizione(),
                                    ingredienteDTO.valoriNutrizionali(),
                                    ingredienteDTO.immagine() != null ? ingredienteDTO.immagine() : "https://placehold.co/600x400"
                            );
                            ingrediente = ingredientiService.saveIngrediente(ingredientePayloadDTO);
                        }


                        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente();
                        ricettaIngrediente.setRicetta(ricettaEsistente);
                        ricettaIngrediente.setIngrediente(ingrediente);
                        ricettaIngrediente.setQuantita(ingredienteDTO.quantita());
                        return ricetteIngredientiRepository.save(ricettaIngrediente);
                    })
                    .collect(Collectors.toList());

            ricettaEsistente.setRicettaIngredienti(ricettaIngredienti);
        }

        return ricetteRepository.save(ricettaEsistente);
    }



    public Page<Ricetta> findAll (int page, int size, String sortBy){
        if (page> 10) page=10;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return ricetteRepository.findAll(pageable);
    }

    public Ricetta findById(UUID ricettaId){
        return this.ricetteRepository.findById(ricettaId).orElseThrow(()->new NotFoundException(ricettaId));
    }

    public void findByIdAndDelete(UUID ricettaId){
        Ricetta found = this.findById(ricettaId);
        List<RicettaIngrediente> ricettaIngredienti= found.getRicettaIngredienti();
        List<PassaggioDiPreparazione> passaggi = found.getPassaggi();
        this.passaggiDiPreparazioneRepository.deleteAll(passaggi);
        this.ricetteIngredientiRepository.deleteAll(ricettaIngredienti);
        this.ricetteRepository.delete(found);
    }

    public Ricetta uploadimmaginePiatto (UUID ricettaId, MultipartFile immaginePiatto) throws IOException {
        Ricetta found= this.ricetteRepository.findById(ricettaId).orElseThrow(()->new NotFoundException(ricettaId));
        String url = (String) cloudinary.uploader().upload(immaginePiatto.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("Url " + url);
        found.setImmaginePiatto(url);
        return this.ricetteRepository.save(found);

    }

    public List<Ricetta> findRicetteByFornitoreId (UUID fornitoreId){
        return this.ricetteRepository.findRicetteByFornitoreId(fornitoreId);
    }
}
