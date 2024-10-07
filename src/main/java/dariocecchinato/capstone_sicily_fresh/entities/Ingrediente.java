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
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String descrizione;
    private String valoriNutrizionali;
    private String immagine;

    public Ingrediente(String nome, String descrizione, String valoriNutrizionali, String immagine) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.valoriNutrizionali = valoriNutrizionali;
        this.immagine= immagine;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", valoriNutrizionali='" + valoriNutrizionali + '\'' +
                '}';
    }
}
