package dariocecchinato.capstone_sicily_fresh.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RicetteIngredientiPayloadDTO(
        @NotNull(message = "Devi inserire il riferimento alla ricetta")
        UUID ricetta,  // Questo è l'ID della ricetta, necessario per creare la relazione
        @NotNull(message = "Devi inserire il riferimento all'ingrediente")
        UUID ingrediente,  // Questo è l'ID dell'ingrediente
        @NotEmpty(message = "Il nome dell'ingrediente non può essere vuoto")
        @NotEmpty(message = "Devi inserire il nome dell'ingrediente")
        String nome,
        @NotEmpty(message = "Devi inserire la descrizione dell'ingrediente")
        String descrizione,
        @NotEmpty(message = "Devi inserire i valori nutrizionali dell'ingrediente")
        String valoriNutrizionali,
        String immagine,
        @NotEmpty(message = "Inserisci la quantità")
        String quantita) {
}
