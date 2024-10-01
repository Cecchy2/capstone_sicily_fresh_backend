package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.PassaggiDiPreparazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PassaggiDiPreparazioneService {
    @Autowired
    private PassaggiDiPreparazioneRepository passaggiDiPreparazioneRepository;
    @Autowired
    private RicetteService ricetteService;

    public PassaggioDiPreparazione salvaPassaggio(PassaggiDiPreparazionePayloadDTO body){
        PassaggioDiPreparazione passaggioDiPreparazione = new PassaggioDiPreparazione(body.descrizione(),body.immaginePassaggio(), body.ordinePassaggio());
       return passaggiDiPreparazioneRepository.save(passaggioDiPreparazione);
    }

    public Page<PassaggioDiPreparazione> findAll (int page, int size, String sortBy){
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.passaggiDiPreparazioneRepository.findAll(pageable);
    }

    public List<PassaggioDiPreparazione> findPassaggiByRicettaId(UUID ricettaId) {
        return passaggiDiPreparazioneRepository.findByRicettaId(ricettaId);
    }

    public PassaggioDiPreparazione findByIdAndUpdate(UUID passaggioDiPreparazioneId, PassaggiDiPreparazionePayloadDTO body){
        return this.findByIdAndUpdate(passaggioDiPreparazioneId, body);
    }
}
