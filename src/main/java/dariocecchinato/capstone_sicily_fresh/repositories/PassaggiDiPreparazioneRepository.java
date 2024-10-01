package dariocecchinato.capstone_sicily_fresh.repositories;

import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PassaggiDiPreparazioneRepository extends JpaRepository<PassaggioDiPreparazione, UUID> {

    List<PassaggioDiPreparazione> findByRicettaId(UUID ricettaId);
}
