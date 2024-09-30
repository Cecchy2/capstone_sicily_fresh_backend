package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.RicettePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.RicetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RicetteService {
    @Autowired
    private RicetteRepository ricetteRepository;

    public Ricetta salvaRicetta(RicettePayloadDTO body) {

        if (ricetteRepository.existsByTitolo(body.titolo())) {
            throw new BadRequestException("La ricetta " + body.titolo() + " è già presente");
        }

        String immaginePiatto = body.immaginePiatto() != null ? body.immaginePiatto() : "https://placehold.co/600x400";
        Ricetta ricetta = new Ricetta();
        ricetta.setTitolo(body.titolo());
        ricetta.setDescrizione(body.descrizione());
        ricetta.setImmaginePiatto(immaginePiatto);
        ricetta.setDifficolta(body.difficolta());
        ricetta.setTempo(body.tempo());
        ricetta.setValoriNutrizionali(body.valoriNutrizionali());

        Ricetta savedRicetta = ricetteRepository.save(ricetta);

        if (body.passaggi() != null && !body.passaggi().isEmpty()) {
            List<PassaggioDiPreparazione> passaggi = body.passaggi().stream()
                    .map(passaggioDTO -> {
                        PassaggioDiPreparazione passaggio = new PassaggioDiPreparazione();
                        passaggio.setDescrizione(passaggioDTO.descrizione());
                        passaggio.setImmaginePassaggio(passaggioDTO.immaginePassaggio());
                        passaggio.setOrdinePassaggio(passaggioDTO.ordinePassaggio());
                        passaggio.setRicetta(savedRicetta); // Associa il passaggio alla ricetta
                        return passaggio;
                    })
                    .collect(Collectors.toList());

            savedRicetta.setPassaggi(passaggi);
        }

        return ricetteRepository.save(savedRicetta);
    }
}
