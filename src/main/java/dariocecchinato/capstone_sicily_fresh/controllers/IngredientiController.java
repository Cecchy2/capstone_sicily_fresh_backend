package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.IngredientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredienti")
public class IngredientiController {
    @Autowired
    private IngredientiService ingredientiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientiResponseDTO creaIngrediente (@RequestBody @Validated IngredientiPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }else{
                return new IngredientiResponseDTO(this.ingredientiService.saveIngrediente(body).getUuid());
            }
        }
    }


