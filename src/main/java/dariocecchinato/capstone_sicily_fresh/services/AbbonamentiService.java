package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Abbonamento;
import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.AbbonamentoPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.AbbonamentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AbbonamentiService {
    @Autowired
    private AbbonamentiRepository abbonamentiRepository;
    @Autowired
    private UtentiService utentiService;

    public Abbonamento creaAbbonamento (AbbonamentoPayloadDTO body){
        Utente cliente = this.utentiService.findUtenteById(body.cliente());
        Abbonamento abbonamento = new Abbonamento(body.nome(),body.numeroRicette(),body.prezzo(), LocalDate.now(),LocalDate.now().plusYears(1),cliente);
        return this.abbonamentiRepository.save(abbonamento);
    }


    public Page<Abbonamento> getAllAbbonamenti (int page, int size, String sortby){
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return this.abbonamentiRepository.findAll(pageable);
    }

    public Abbonamento findByIdAndUpdate (UUID abbonamentoId, AbbonamentoPayloadDTO body){
        Abbonamento found = this.abbonamentiRepository.findById(abbonamentoId).orElseThrow(()-> new NotFoundException(abbonamentoId));
        found.setNome(body.nome());
        found.setNumeroRicette(body.numeroRicette()); // Assicurati di aggiornare numeroRicette
        found.setPrezzo(body.prezzo());


        return this.abbonamentiRepository.save(found);
    }
}
