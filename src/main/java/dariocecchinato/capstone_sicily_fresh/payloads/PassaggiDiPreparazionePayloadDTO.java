package dariocecchinato.capstone_sicily_fresh.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record PassaggiDiPreparazionePayloadDTO(
        @NotEmpty(message = "La descrizione del passaggio non può essere vuota")
        @Size(max = 1000, message = "La descrizione non deve superare i 1000 caratteri")
        String descrizione,
        String immaginePassaggio,
        @NotNull(message = "L'ordine del passaggio è obbligatorio")
        int ordinePassaggio

) {
}
