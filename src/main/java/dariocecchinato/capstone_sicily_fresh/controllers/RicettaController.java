package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.*;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicettePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.IngredientiService;
import dariocecchinato.capstone_sicily_fresh.services.RicetteService;
import dariocecchinato.capstone_sicily_fresh.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/ricette")
public class RicettaController {
    @Autowired
    private RicetteService ricetteService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private IngredientiService ingredientiService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RicetteResponseDTO creaRicetta(@RequestBody @Validated RicettePayloadDTO body) {
        Ricetta savedRicetta = ricetteService.salvaRicetta(body);
        return new RicetteResponseDTO(savedRicetta.getId());
    }

    @GetMapping
    public Page<Ricetta> findAll (@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortby){
        return ricetteService.findAll(page, size, sortby);
    }

    @GetMapping("/{ricettaId}")
    public Ricetta findById (@PathVariable UUID ricettaId){
        Ricetta found = this.ricetteService.findById(ricettaId);
        return found;
    }

    @PutMapping("/{ricettaId}")
    public Ricetta findByIdAndUpdate(@PathVariable UUID ricettaId, @RequestBody @Validated RicettePayloadDTO body){
        Utente fornitore = utentiService.findUtenteById(body.fornitoreId());

        Ricetta found = this.ricetteService.findById(ricettaId);
        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setImmaginePiatto(body.immaginePiatto());
        found.setDifficolta(body.difficolta());
        found.setTempo(body.tempo());
        found.setValoriNutrizionali(body.valoriNutrizionali());
        found.setFornitore(fornitore);

        List<PassaggioDiPreparazione> passaggi = body.passaggi().stream()
                .map(passaggiDto -> {
                    PassaggioDiPreparazione passaggio = new PassaggioDiPreparazione();
                    passaggio.setDescrizione(passaggiDto.descrizione());
                    passaggio.setImmaginePassaggio(passaggiDto.immaginePassaggio());
                    passaggio.setRicetta(found);
                    return passaggio;
                }).collect(Collectors.toList());
        found.setPassaggi(passaggi);

        List<RicettaIngrediente> nuovaListaRicettaIngredienti = new ArrayList<>();
        for (IngredientiPayloadDTO ingredienteDto : body.ingredienti()) {
            Ingrediente ingrediente = this.ingredientiService.findByNome(ingredienteDto.nome());
            if (ingrediente == null) {
                throw new BadRequestException("Ingrediente non trovato: " + ingredienteDto.nome());
            }
            RicettaIngrediente ricettaIngrediente = new RicettaIngrediente(found, ingrediente, ingredienteDto.quantita());
            nuovaListaRicettaIngredienti.add(ricettaIngrediente);
        }
        found.setRicettaIngredienti(nuovaListaRicettaIngredienti);
        return found;
    }

}
