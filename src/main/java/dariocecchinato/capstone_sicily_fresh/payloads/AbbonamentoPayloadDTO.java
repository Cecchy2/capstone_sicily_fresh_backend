package dariocecchinato.capstone_sicily_fresh.payloads;

import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AbbonamentoPayloadDTO(
        @NotEmpty(message = "Il nome non può essere vuoto")
        @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
        String nome,
        @Min(value = 3, message = "Il numero minimo di ricette sono 3")
        int numeroRicette,
        double prezzo,

        LocalDate dataInizio,
        LocalDate dataScadenza,
        @NotNull(message = "Il cliente non può essere nullo")
        UUID cliente
) {
}
