package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.*;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.RicettePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.IngredientiService;
import dariocecchinato.capstone_sicily_fresh.services.RicetteService;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE')")
    public RicetteResponseDTO creaRicetta(@RequestBody @Validated RicettePayloadDTO body) {

        Ricetta savedRicetta = ricetteService.salvaRicetta(body);
        return new RicetteResponseDTO(savedRicetta.getId());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Page<Ricetta> findAll (@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortby){
        return ricetteService.findAll(page, size, sortby);
    }

    @GetMapping("/{ricettaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Ricetta findById (@PathVariable UUID ricettaId){
        Ricetta found = this.ricetteService.findById(ricettaId);
        return found;
    }

    @PutMapping("/{ricettaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE')")
    public Ricetta findByIdAndUpdate(@PathVariable UUID ricettaId, @RequestBody @Validated RicettePayloadDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.ricetteService.aggiornaRicetta(ricettaId,body);
    }

    @DeleteMapping("/{ricettaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID ricettaId){
       this.ricetteService.findByIdAndDelete(ricettaId);
    }

    @PutMapping("/me/{ricettaId}")
    @PreAuthorize("hasAuthority('FORNITORE')")
    public Ricetta updateMyRicetta(@PathVariable UUID ricettaId, @RequestBody @Validated RicettePayloadDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.ricetteService.aggiornaRicetta(ricettaId,body);
    }
    
    @DeleteMapping("/me/{ricettaId}")
    @PreAuthorize("hasAuthority('FORNITORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyRicetta(@AuthenticationPrincipal Utente fornitore, @PathVariable UUID ricettaId) {
        Ricetta ricetta = ricetteService.findById(ricettaId);
        if (!ricetta.getFornitore().getId().equals(fornitore.getId())) {
            throw new BadRequestException("Non hai l'autorizzazione per cancellare questa ricetta.");
        }
        ricetteService.findByIdAndDelete(ricettaId);
    }

    @PatchMapping("/{ricettaId}/immaginePiatto")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE')")
    public Ricetta uploadImmagginePiatto(@PathVariable UUID ricettaId, @RequestParam("immaginePiatto") MultipartFile immaginePiatto) throws IOException {
        return this.ricetteService.uploadimmaginePiatto(ricettaId,immaginePiatto);
    }
}
