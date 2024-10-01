package dariocecchinato.capstone_sicily_fresh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    private String descrizione;
    private String valoriNutrizionali;

    public Ingrediente(String nome, String descrizione, String valoriNutrizionali) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.valoriNutrizionali = valoriNutrizionali;
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
