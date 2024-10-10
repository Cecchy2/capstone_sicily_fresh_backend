package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.enums.StatoOrdine;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarrelloDettaglioPayloadDTO(
        @NotNull(message = "Il carrello è obbligatorio")
        UUID carrello,
        @NotNull(message = "La ricetta è obbligatoria")
        UUID ricetta,
        @NotNull(message = "La quantità è obbligatoria")
        int quantita
        ) {
}


