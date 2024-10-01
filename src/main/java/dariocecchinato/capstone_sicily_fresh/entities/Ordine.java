package dariocecchinato.capstone_sicily_fresh.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    private LocalDateTime dataOrdine;

    private String stato;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<DettaglioOrdine> dettagliOrdini;

    public Ordine(Utente utente, LocalDateTime dataOrdine, String stato) {
        this.utente = utente;
        this.dataOrdine = dataOrdine;
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "utente=" + utente +
                ", dataOrdine=" + dataOrdine +
                ", stato='" + stato + '\'' +
                ", dettagliOrdini=" + dettagliOrdini +
                '}';
    }
}
