package dariocecchinato.capstone_sicily_fresh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(columnDefinition = "TEXT")
    private String descrizione;
    @Column(columnDefinition = "TEXT")
    private String immaginePassaggio;

    @ManyToOne
    @JoinColumn(name = "ricetta_id")
    @JsonIgnore
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
