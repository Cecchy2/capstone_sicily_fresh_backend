package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazioneResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.PassaggiDiPreparazioneRepository;
import dariocecchinato.capstone_sicily_fresh.services.PassaggiDiPreparazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
