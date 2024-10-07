package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;

import java.util.UUID;

public record CarrelloDettaglioPayloadDTO(
        UUID carrello,
        UUID ricetta,
        int quantita) {
}


//TODO AGGIUNGERE VALIDAZIONI