package dariocecchinato.capstone_sicily_fresh.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PassaggiDiPreparazionePayloadDTO(
        @NotEmpty(message = "La descrizione del passaggio non può essere vuota")
        String descrizione,
        String immaginePassaggio,
        @NotNull(message = "L'ordine del passaggio è obbligatorio")

        int ordinePassaggio) {
}
