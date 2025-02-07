package dariocecchinato.capstone_sicily_fresh.repositories;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IngredientiRepository extends JpaRepository<Ingrediente, UUID> {
    boolean existsByNome(String nome);

    Optional<Ingrediente> findByNome (String nome);

}

