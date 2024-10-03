package dariocecchinato.capstone_sicily_fresh.services;
import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.entities.RicettaIngrediente;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
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
        // Trova la ricetta dal database usando l'ID
        Ricetta ricetta = this.ricetteService.findById(body.ricetta());

        // Trova l'ingrediente o creane uno nuovo se non esiste
        Ingrediente ingrediente;
        try {
            ingrediente = this.ingredientiService.findById(body.ingrediente());
        } catch (NotFoundException e) {
            // Se l'ingrediente non esiste, crea un nuovo Ingrediente
            ingrediente = new Ingrediente();
            ingrediente.setNome(body.nome());
            ingrediente.setDescrizione(body.descrizione());
            ingrediente.setValoriNutrizionali(body.valoriNutrizionali());
            ingrediente.setImmagine(body.immagine() != null ? body.immagine() : "https://placehold.co/600x400");
            ingrediente = this.ingredientiService.saveIngrediente(new IngredientiPayloadDTO(
                    ingrediente.getNome(),
                    ingrediente.getDescrizione(),
                    ingrediente.getValoriNutrizionali(),
                    ingrediente.getImmagine()
            ));
        }

        // Crea e configura l'entità RicettaIngrediente
        RicettaIngrediente ricettaIngrediente = new RicettaIngrediente();
        ricettaIngrediente.setRicetta(ricetta);
        ricettaIngrediente.setIngrediente(ingrediente);
        ricettaIngrediente.setQuantita(body.quantita());

        // Salva RicettaIngrediente nel database
        RicettaIngrediente savedRicettaIngrediente = this.ricettaIngredientiRepository.save(ricettaIngrediente);

        return new RicetteIngredientoResponseDTO(savedRicettaIngrediente.getId());
    }
}
