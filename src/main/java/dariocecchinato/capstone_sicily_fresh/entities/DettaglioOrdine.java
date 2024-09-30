package dariocecchinato.capstone_sicily_fresh.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DettaglioOrdine {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_ordine")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;

    private int quantita;

    public DettaglioOrdine(Ordine ordine, Ricetta ricetta, int quantita) {
        this.ordine = ordine;
        this.ricetta = ricetta;
        this.quantita = quantita;
    }
}
