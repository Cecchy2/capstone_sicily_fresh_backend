package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.entities.RicettaIngrediente;

import java.util.List;
import java.util.UUID;

public record RicetteResponseDTO(UUID ricettaId, List<PassaggioDiPreparazione>passaggi) {
}
