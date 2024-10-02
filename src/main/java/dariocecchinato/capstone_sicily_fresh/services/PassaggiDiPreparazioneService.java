package dariocecchinato.capstone_sicily_fresh.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dariocecchinato.capstone_sicily_fresh.entities.PassaggioDiPreparazione;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.PassaggiDiPreparazionePayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.PassaggiDiPreparazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PassaggiDiPreparazioneService {
    @Autowired
    private PassaggiDiPreparazioneRepository passaggiDiPreparazioneRepository;
    @Autowired
    private RicetteService ricetteService;
    @Autowired
    private Cloudinary cloudinary;

    public PassaggioDiPreparazione salvaPassaggio(PassaggiDiPreparazionePayloadDTO body){
        String immagine= "https://fastly.picsum.photos/id/848/200/300.jpg?hmac=cNClhUSP4IM6ZT6RTqdeCOLWYEJYBNXaqdflgf_EqD8";
        PassaggioDiPreparazione passaggioDiPreparazione = new PassaggioDiPreparazione(body.descrizione(),immagine, body.ordinePassaggio());
       return passaggiDiPreparazioneRepository.save(passaggioDiPreparazione);
    }

    public Page<PassaggioDiPreparazione> findAll (int page, int size, String sortBy){
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.passaggiDiPreparazioneRepository.findAll(pageable);
    }

    public List<PassaggioDiPreparazione> findPassaggiByRicettaId(UUID ricettaId) {
        return passaggiDiPreparazioneRepository.findByRicettaId(ricettaId);
    }

    public PassaggioDiPreparazione findByIdAndUpdate(UUID passaggioDiPreparazioneId, PassaggiDiPreparazionePayloadDTO body){
        return this.findByIdAndUpdate(passaggioDiPreparazioneId, body);
    }

    public void findByIdAndDelete(UUID passaggioDiPreparazioneId){
        PassaggioDiPreparazione found= this.passaggiDiPreparazioneRepository.findById(passaggioDiPreparazioneId).orElseThrow(()-> new NotFoundException(passaggioDiPreparazioneId));
        this.passaggiDiPreparazioneRepository.delete(found);
    }

    public PassaggioDiPreparazione uploadimmaginePassaggio (UUID passaggioDiPreparazioneId, MultipartFile immaginePassaggio) throws IOException{
        PassaggioDiPreparazione found= this.passaggiDiPreparazioneRepository.findById(passaggioDiPreparazioneId).orElseThrow(()->new NotFoundException(passaggioDiPreparazioneId));

        String url = (String) cloudinary.uploader().upload(immaginePassaggio.getBytes(), ObjectUtils.emptyMap()).get("url");

        System.out.println("Url " + url);

        found.setImmaginePassaggio(url);
        return this.passaggiDiPreparazioneRepository.save(found);

    }
}
