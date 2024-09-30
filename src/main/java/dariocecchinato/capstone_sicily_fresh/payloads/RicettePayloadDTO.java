package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.enums.Difficolta;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record RicettePayloadDTO(
        @NotEmpty(message = "Il titolo non può essere vuoto")
        @Size(max = 100, message = "Il titolo non può superare i 100 caratteri")
         String titolo,
        @NotEmpty(message = "La descrizione non può essere vuota")
         String descrizione,

         String immaginePiatto,
         Difficolta difficolta,
        @NotEmpty(message = "Il tempo di preparazione è obbligatorio")
         String tempo,
        @NotEmpty(message = "I valori nutrizionali sono obbligatori")

        String valoriNutrizionali,
        @NotNull(message = "Il fornitore è obbligatorio")

        UUID fornitoreId,
        @NotEmpty(message = "I passaggi di preparazione non possono essere vuoti")

        List<PassaggiDiPreparazionePayloadDTO> passaggi,
        @NotEmpty(message = "La lista degli ingredienti non può essere vuota")

        List<IngredientiPayloadDTO> ingredienti) {
}
