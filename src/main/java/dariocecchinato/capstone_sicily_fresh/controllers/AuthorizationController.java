package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.enums.RuoloUtente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.UtenteLoginDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.UtenteLoginResponseDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.UtentiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.UtentiResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.AuthorizationsService;
import dariocecchinato.capstone_sicily_fresh.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AuthorizationsService authorizationsService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body){
        Utente found = this.utentiService.findByEmail(body.email());
        RuoloUtente role= found.getRuolo();
        UUID utenteId = found.getId();
        return new UtenteLoginResponseDTO(this.authorizationsService.checkCredenzialiEGeneraToken(body),role, utenteId);
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public UtentiResponseDTO save(
            @Validated @ModelAttribute UtentiPayloadDTO body,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            BindingResult validationResult) throws IOException {


        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }
        
        Utente newUser = utentiService.saveUtente(body, avatar);
        return new UtentiResponseDTO(newUser.getId());
    }
}
