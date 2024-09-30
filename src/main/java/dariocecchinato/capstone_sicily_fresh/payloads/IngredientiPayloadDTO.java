package dariocecchinato.capstone_sicily_fresh.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record IngredientiPayloadDTO(
        @NotEmpty(message = "Il nome non può essere vuoto")
        @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
        String nome,
        @NotEmpty(message = "La descrizione non può essere vuota")
        @Size(max = 100, message = "La descrizione non può superare i 100 caratteri")
        String descrizione,
        @NotEmpty(message = "Valori nutrizionali non possono essere vuoti")
        @Size(max = 10, message = "La descrizione non può superare i 10 caratteri")
        String valoriNutrizionali) {
}
