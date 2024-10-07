package dariocecchinato.capstone_sicily_fresh.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Abbonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private int numeroRicette;
    private double prezzo;
    private LocalDate dataInizio;
    private LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Utente cliente;


    public Abbonamento(String nome, int numeroRicette, double prezzo, LocalDate dataInizio, LocalDate dataScadenza, Utente cliente) {
        this.nome = nome;
        this.numeroRicette = numeroRicette;
        this.prezzo = prezzo;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.cliente = cliente;
    }
}
