package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.enums.RuoloUtente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UtentiPayloadDTO(
        @NotEmpty(message = "Devi inserire una email")
        @Email(message = "Devi inserire una email valida")
        String email,
        @NotEmpty(message = "Devi inserire una email")
        String password,
        @NotEmpty(message = "Devi inserire un nome")
        @Size(min = 3, max = 20, message = "Il nome deve avere dai 3 ai 20 caratteri")
        String nome,
        @NotEmpty(message = "Devi inserire un cognome")
        @Size(min = 3, max = 40, message = "Il cognome deve avere dai 3 ai 40 caratteri")
        String cognome,
        @NotEmpty(message = "Devi inserire uno username")
        @Size(min = 3, max = 20, message = "Lo username deve avere dai 3 ai 20 caratteri")
        String username,
        @NotNull(message = "Devi inserire una data di nascita")
        LocalDate dataDiNascita,
        @NotNull(message = "Devi inserire un ruolo, CLIENTE o FORNITORE")
        RuoloUtente ruolo,
        @NotEmpty(message = "Devi inserire un indirizzo")
        String indirizzo,
        @NotEmpty(message = "Devi inserire una citta")
        String citta
) {
}
