package dariocecchinato.capstone_sicily_fresh.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class IngredientiService {
    @Autowired
    private IngredientiRepository ingredientiRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Ingrediente> findAll(int page, int size, String sortby) {
        if (page > 10) page = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return ingredientiRepository.findAll(pageable);
    }

    public Ingrediente saveIngrediente(IngredientiPayloadDTO body){
        if (ingredientiRepository.existsByNome(body.nome()))
            throw new BadRequestException("L'ingrediente " + body.nome() + " già è presente");
        String immagine = "https://fastly.picsum.photos/id/848/200/300.jpg?hmac=cNClhUSP4IM6ZT6RTqdeCOLWYEJYBNXaqdflgf_EqD8";
        Ingrediente ingrediente= new Ingrediente(body.nome(), body.descrizione(), body.valoriNutrizionali(), immagine);
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

    public Ingrediente  uploadImmagine(UUID ingredienteId, MultipartFile immagine) throws IOException{
        Ingrediente found= this.findById(ingredienteId);
        String url = (String) cloudinary.uploader().upload(immagine.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("Url " + url);
        found.setImmagine(url);
        return this.ingredientiRepository.save(found);

    }
}
