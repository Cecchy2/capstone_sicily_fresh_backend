package dariocecchinato.capstone_sicily_fresh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RicettaIngrediente {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    private String quantita;

    public RicettaIngrediente(Ricetta ricetta, Ingrediente ingrediente, String quantita) {
        this.ricetta = ricetta;
        this.ingrediente = ingrediente;
        this.quantita = quantita;
    }
}
