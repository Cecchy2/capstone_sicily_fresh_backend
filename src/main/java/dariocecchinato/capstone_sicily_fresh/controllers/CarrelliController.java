package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.CarrelliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrelli")
public class CarrelliController {
    @Autowired
    private CarrelliService carrelliService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE')")
    public CarrelloResponseDTO creaCarrello(@RequestBody @Validated CarrelloPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String messages= validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload " + messages);
        }else{
            return new CarrelloResponseDTO(this.carrelliService.creaCarrello(body).getId());
        }
    }

}
