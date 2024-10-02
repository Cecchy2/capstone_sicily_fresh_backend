package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.entities.RicettaIngrediente;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteIngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.RicetteIngredientoResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.RicetteIngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RicetteIngredientiService {
    @Autowired
    private RicetteIngredientiRepository ricettaIngredientiRepository;
    @Autowired
    private RicetteService ricetteService;
    @Autowired
    private IngredientiService ingredientiService;

    public RicetteIngredientoResponseDTO save(RicetteIngredientiPayloadDTO body) {
        // Trova la ricetta e l'ingrediente dal database usando gli ID
        Ricetta ricetta = this.ricetteService.findById(body.ricetta());
        Ingrediente ingrediente = this.ingredientiService.findById(body.ingrediente());

        // Crea e configura l'entità RicettaIngrediente
        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente();
        ricettaIngrediente.setRicetta(ricetta);
        ricettaIngrediente.setIngrediente(ingrediente);
        ricettaIngrediente.setQuantita(body.quantita());

        // Salva RicettaIngrediente nel database
        this.ricettaIngredientiRepository.save(ricettaIngrediente);

        return new RicetteIngredientoResponseDTO(ricettaIngrediente.getId());
    }
}
