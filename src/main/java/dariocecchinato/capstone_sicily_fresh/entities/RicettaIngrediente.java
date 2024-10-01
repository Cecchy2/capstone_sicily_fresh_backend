package dariocecchinato.capstone_sicily_fresh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class RicettaIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;

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

    @Override
    public String toString() {
        return "RicettaIngrediente{" +
                "ricetta=" + ricetta +
                ", ingrediente=" + ingrediente +
                ", quantita='" + quantita + '\'' +
                '}';
    }
}
