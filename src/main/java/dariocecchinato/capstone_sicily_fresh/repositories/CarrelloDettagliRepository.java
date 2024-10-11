package dariocecchinato.capstone_sicily_fresh.repositories;

import dariocecchinato.capstone_sicily_fresh.entities.CarrelloDettaglio;
import dariocecchinato.capstone_sicily_fresh.enums.StatoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarrelloDettagliRepository extends JpaRepository<CarrelloDettaglio, UUID> {
    List<CarrelloDettaglio> findByCarrelloId(UUID carrelloId);

    List<CarrelloDettaglio> findByRicettaId(UUID ricettaId);

    List<CarrelloDettaglio> findByRicettaIdAndStatoOrdine(UUID ricettaId, StatoOrdine statoOrdine);
}
