package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientiService {
    @Autowired
    private IngredientiRepository ingredientiRepository;

    public Page<Ingrediente> findAll(int page, int size, String sortby) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return ingredientiRepository.findAll(pageable);
    }

    public Ingrediente saveIngrediente(IngredientiPayloadDTO body){
        if (ingredientiRepository.existsByNome(body.nome()))
            throw new BadRequestException("L'ingrediente " + body.nome() + " già è presente");
        Ingrediente ingrediente= new Ingrediente(body.nome(), body.descrizione(), body.valoriNutrizionali());
        return ingredientiRepository.save(ingrediente);

    }

    public Ingrediente findById(UUID ingredienteId){
        return ingredientiRepository.findById(ingredienteId).orElseThrow(()-> new NotFoundException(ingredienteId));
    };

    public Ingrediente findByIdAndUpdate (UUID ingredienteId, IngredientiPayloadDTO body){
        Ingrediente found = this.findById(ingredienteId);
        found.setNome(body.nome());
        found.setDescrizione(body.descrizione());
        found.setValoriNutrizionali(body.valoriNutrizionali());
        return ingredientiRepository.save(found);
    }

    public void findByIdAndDelete(UUID ingredienteId){
        Ingrediente found = this.findById(ingredienteId);
        this.ingredientiRepository.delete(found);
    }

    public Ingrediente findByNome (String nome){
        return ingredientiRepository.findByNome(nome).orElseThrow(()->new NotFoundException("Ingrediente" + nome + " non trovato"));
    }
}
