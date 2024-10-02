package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteIngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteIngredientoResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.RicetteIngredientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/ricetteIngredienti")
public class RicetteIngredientiController {
    @Autowired
    private RicetteIngredientiService ricetteIngredientiService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RicetteIngredientoResponseDTO save(@RequestBody @Validated RicetteIngredientiPayloadDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.ricetteIngredientiService.save(body);
    }


    }

