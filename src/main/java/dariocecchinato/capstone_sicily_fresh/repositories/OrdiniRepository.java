package dariocecchinato.capstone_sicily_fresh.repositories;

import dariocecchinato.capstone_sicily_fresh.entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdiniRepository extends JpaRepository<Ordine, UUID> {
}
