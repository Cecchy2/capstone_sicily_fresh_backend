package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.payloads.RicettePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.RicetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ricette")
public class RicettaController {
    @Autowired
    private RicetteService ricetteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RicetteResponseDTO creaRicetta(@RequestBody @Validated RicettePayloadDTO ricettaDTO) {
        Ricetta savedRicetta = ricetteService.salvaRicetta(ricettaDTO);
        return new RicetteResponseDTO(savedRicetta.getUuid());
    }
}
