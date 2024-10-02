package dariocecchinato.capstone_sicily_fresh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dariocecchinato.capstone_sicily_fresh.enums.RuoloUtente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Utente  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String username;
    private LocalDate dataDiNascita;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruolo;
    @OneToMany(mappedBy = "fornitore", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ricetta> ricette;
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Ordine> ordini;

    public Utente(String email, String password, String nome, String cognome, String username, LocalDate dataDiNascita, String avatar, RuoloUtente ruolo) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.dataDiNascita = dataDiNascita;
        this.avatar = avatar;
        this.ruolo = ruolo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }
    @Override
    public String getUsername() {
        return this.email;
    }
}
