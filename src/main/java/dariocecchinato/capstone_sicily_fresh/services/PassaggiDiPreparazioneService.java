package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.PassaggiDiPreparazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassaggiDiPreparazioneService {
    @Autowired
    private PassaggiDiPreparazioneRepository passaggiDiPreparazioneRepository;

    public PassaggioDiPreparazione salvaPassaggio(PassaggiDiPreparazionePayloadDTO body){
        PassaggioDiPreparazione passaggioDiPreparazione = new PassaggioDiPreparazione(body.descrizione(),body.immaginePassaggio(), body.ordinePassaggio());
       return passaggiDiPreparazioneRepository.save(passaggioDiPreparazione);
    }
}
