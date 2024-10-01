package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazioneResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.PassaggiDiPreparazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
}
