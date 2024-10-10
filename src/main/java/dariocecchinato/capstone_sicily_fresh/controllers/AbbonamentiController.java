package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Abbonamento;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.AbbonamentoPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.AbbonamentoResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.AbbonamentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abbonamenti")
public class AbbonamentiController {
    @Autowired
    private AbbonamentiService abbonamentiService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public AbbonamentoResponseDTO creaAbbonamento(@RequestBody @Validated AbbonamentoPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }else{
            return new AbbonamentoResponseDTO(this.abbonamentiService.creaAbbonamento(body).getId());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Page<Abbonamento> getAllAbbonamenti(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String sortby){
        return this.abbonamentiService.getAllAbbonamenti(page, size, sortby);
    }

    @PutMapping("/{abbonamentoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Abbonamento findByIdAndUpdate(@PathVariable UUID abbonamentoId, @RequestBody @Validated AbbonamentoPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }else{
            return  this.abbonamentiService.findByIdAndUpdate(abbonamentoId, body);
        }
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public List<Abbonamento> findByClienteId(@PathVariable UUID clienteId){
        return this.abbonamentiService.findByClienteId(clienteId);
    }

}
