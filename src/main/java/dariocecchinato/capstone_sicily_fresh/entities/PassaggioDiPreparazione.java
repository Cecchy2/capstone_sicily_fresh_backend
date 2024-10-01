package dariocecchinato.capstone_sicily_fresh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PassaggioDiPreparazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String descrizione;
    private String immaginePassaggio;

    @ManyToOne
    @JoinColumn(name = "ricetta_id")
    private Ricetta ricetta;

    private int ordinePassaggio;

    public PassaggioDiPreparazione(String descrizione, String immaginePassaggio, int ordinePassaggio) {
        this.descrizione = descrizione;
        this.immaginePassaggio = immaginePassaggio;
        this.ordinePassaggio = ordinePassaggio;

    }

    @Override
    public String toString() {
        return "PassaggioDiPreparazione{" +
                "descrizione='" + descrizione + '\'' +
                ", immaginePassaggio='" + immaginePassaggio + '\'' +
                ", ordinePassaggio=" + ordinePassaggio +
                '}';
    }
}
