package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.enums.RuoloUtente;


import java.util.UUID;

public record UtenteLoginResponseDTO(String accessToken, RuoloUtente role, UUID utenteId) {
}
