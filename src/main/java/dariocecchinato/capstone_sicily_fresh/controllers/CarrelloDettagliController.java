package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.CarrelloDettagliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("carrelloDettagli")
public class CarrelloDettagliController {
    @Autowired
    private CarrelloDettagliService carrelloDettagliService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public CarrelloDettaglioResponseDTO creaCarrelloDettaglio(@RequestBody @Validated CarrelloDettaglioPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + messages);
        }else{
            return new CarrelloDettaglioResponseDTO(this.carrelloDettagliService.creaCarrelloDettaglio(body).getId());
        }
    }
}
