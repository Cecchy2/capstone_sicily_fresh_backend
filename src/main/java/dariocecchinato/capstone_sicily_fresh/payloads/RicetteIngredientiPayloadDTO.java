package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RicetteIngredientiPayloadDTO(
        @NotNull(message = "Devi inserire il riferimento alla ricetta")
        UUID ricetta,
        @NotNull(message = "Devi inserire il riferimento all'ingrediente")
        UUID ingrediente,
        @NotEmpty(message = "Inserisci la quantità")
        String quantita) {
}
