package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;

import java.util.UUID;

public record RicetteIngredientiPayloadDTO(
        UUID ricetta,
        UUID ingrediente,
        String quantita) {
}
