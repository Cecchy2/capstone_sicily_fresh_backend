package dariocecchinato.capstone_sicily_fresh.payloads;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarrelloPayloadDTO(
        @NotNull(message = "Inserisci il cliente")
        UUID cliente
) {
}
