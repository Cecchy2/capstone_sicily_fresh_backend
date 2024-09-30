package dariocecchinato.capstone_sicily_fresh.entities;

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
public class Ricetta {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String descrizione;
    private String immaginePiatto;
    private String difficolta;
    private String tempo;
    private String valoriNutrizionali;

    @OneToMany(mappedBy = "ricetta")
    private List<PassaggioDiPreparazione> passaggi;

    @OneToMany(mappedBy = "ricetta")
    private List<RicettaIngrediente> ricettaIngredienti;

    @ManyToOne
    @JoinColumn(name = "fornitore_id")
    private Utente fornitore;

    @OneToMany(mappedBy = "ricetta")
    private List<DettaglioOrdine> dettagliOrdini;

    public Ricetta(String immaginePiatto, String descrizione, String difficolta, String tempo, String valoriNutrizionali) {
        this.immaginePiatto = immaginePiatto;
        this.descrizione = descrizione;
        this.difficolta = difficolta;
        this.tempo = tempo;
        this.valoriNutrizionali = valoriNutrizionali;
    }
}
