package dariocecchinato.capstone_sicily_fresh.entities;

import dariocecchinato.capstone_sicily_fresh.enums.Difficolta;
import dariocecchinato.capstone_sicily_fresh.enums.Portata;
import dariocecchinato.capstone_sicily_fresh.enums.Tipo;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(columnDefinition = "TEXT")
    private String titolo;
    @Column(columnDefinition = "TEXT")
    private String descrizione;
    @Column(columnDefinition = "TEXT")
    private String immaginePiatto;
    @Enumerated(EnumType.STRING)
    private Difficolta difficolta;
    @Enumerated(EnumType.STRING)
    private Portata portata;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private String tempo;
    private String valoriNutrizionali;

    @OneToMany(mappedBy = "ricetta")
    private List<PassaggioDiPreparazione> passaggi;

    @OneToMany(mappedBy = "ricetta")
    private List<RicettaIngrediente> ricettaIngredienti;

    @ManyToOne
    @JoinColumn(name = "fornitore_id")
    private Utente fornitore;




    public Ricetta(String titolo, String descrizione, String immaginePiatto, Difficolta difficolta, String tempo, String valoriNutrizionali, List<PassaggioDiPreparazione> passaggi, List<RicettaIngrediente> ricettaIngredienti, Utente fornitore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.immaginePiatto = immaginePiatto;
        this.difficolta = difficolta;
        this.tempo = tempo;
        this.valoriNutrizionali = valoriNutrizionali;
        this.passaggi = passaggi;
        this.ricettaIngredienti = ricettaIngredienti;
        this.fornitore = fornitore;

    }

    @Override
    public String toString() {
        return "Ricetta{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", immaginePiatto='" + immaginePiatto + '\'' +
                ", difficolta=" + difficolta +
                ", tempo='" + tempo + '\'' +
                ", valoriNutrizionali='" + valoriNutrizionali + '\'' +
                ", passaggi=" + passaggi +
                ", ricettaIngredienti=" + ricettaIngredienti +
                ", fornitore=" + fornitore +
                '}';
    }
}
