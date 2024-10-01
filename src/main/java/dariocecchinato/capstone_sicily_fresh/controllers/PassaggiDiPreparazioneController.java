package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazioneResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.PassaggiDiPreparazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/passaggidipreparazione")
public class PassaggiDiPreparazioneController {
    @Autowired
    private PassaggiDiPreparazioneService passaggiDiPreparazioneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassaggiDiPreparazioneResponseDTO creaPassaggio(@RequestBody PassaggiDiPreparazionePayloadDTO body){
        String immagine = "https://placehold.co/400";
        PassaggioDiPreparazione passaggio = new PassaggioDiPreparazione(body.descrizione(), immagine,body.ordinePassaggio());
        PassaggioDiPreparazione savedPassaggio = passaggiDiPreparazioneService.salvaPassaggio(body);
        return new PassaggiDiPreparazioneResponseDTO(savedPassaggio.getId());
    }

    @GetMapping
    public Page<PassaggioDiPreparazione> findAll (@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "15") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy){
        return this.passaggiDiPreparazioneService.findAll(page, size, sortBy);
    }

    @GetMapping("/{ricettaId}")
    public List<PassaggioDiPreparazione> getPassaggiByRicettaId(@PathVariable UUID ricettaId) {
        return passaggiDiPreparazioneService.findPassaggiByRicettaId(ricettaId);
    }

    @PutMapping("/{passaggioDiPreparazioneId}")
    public PassaggioDiPreparazione findByIdAndUpdate (@PathVariable UUID passaggioDiPreparazioneId, @RequestBody @Validated PassaggiDiPreparazionePayloadDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.passaggiDiPreparazioneService.findByIdAndUpdate(passaggioDiPreparazioneId,body);
    }

    @DeleteMapping("/{passaggioDiPreparazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID passaggioDiPreparazioneId) {
        this.passaggiDiPreparazioneService.findByIdAndDelete(passaggioDiPreparazioneId);
    }

    }

