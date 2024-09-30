package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
