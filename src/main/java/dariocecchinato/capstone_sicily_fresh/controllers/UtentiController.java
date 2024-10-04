package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.UtentiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

    /*@PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtentiResponseDTO save(@RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {

            return new UtentiResponseDTO(this.utentiService.saveUtente(body).getId());
        }
    }*/

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE', 'FORNITORE')")
    public Utente findById(@PathVariable UUID utenteId) {
        Utente found = this.utentiService.findUtenteById(utenteId);
        if (found == null) throw new NotFoundException(utenteId);
        return found;
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE', 'FORNITORE')")
    public Utente findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono errori con il payload " + message);
        } else {
            return this.utentiService.findByIdAndUpdate(utenteId, body);
        }
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDeleteUtente(utenteId);
    }

    @PatchMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE', 'FORNITORE')")
    public Utente uploadAvatar(@PathVariable UUID utenteId, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        //return this.clienteService.uploadLogoAziendale(clienteId, avatar);
        return this.utentiService.uploadAvatar(utenteId,avatar);

    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody UtentiPayloadDTO body) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utentiService.findByIdAndDeleteUtente(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Utente uploadAvatarPic(@AuthenticationPrincipal Utente utente, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        return this.utentiService.uploadAvatar(utente.getId(), avatar);
    }

}
