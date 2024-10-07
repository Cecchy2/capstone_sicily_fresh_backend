package dariocecchinato.capstone_sicily_fresh.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Carrello {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @OneToOne
    private Utente cliente;

    @OneToMany(mappedBy = "carrello")
    @JsonIgnore
    List<CarrelloDettaglio> carrelloDettaglioList;

    public Carrello(Utente cliente) {
        this.cliente = cliente;
    }
}
