package dariocecchinato.capstone_sicily_fresh.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.UtentiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder bcrypt;

    public Utente saveUtente(UtentiPayloadDTO body, MultipartFile avatar)throws IOException {
       Utente newUtente = new Utente();
       newUtente.setNome(body.nome());
       newUtente.setCognome(body.cognome());
       newUtente.setEmail(body.email());
       newUtente.setUsername(body.username());
       newUtente.setPassword(bcrypt.encode(body.password()));
       newUtente.setDataDiNascita(body.dataDiNascita());
       newUtente.setRuolo(body.ruolo());

        newUtente = utentiRepository.save(newUtente);

        if (avatar != null && !avatar.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
            String avatarUrl = (String) uploadResult.get("secure_url");
            newUtente.setAvatar(avatarUrl);

            newUtente = utentiRepository.save(newUtente);
        }

        return newUtente;
    }



    public Page<Utente> findAll (int page, int size, String sortBy){
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    public Utente findUtenteById(UUID utenteId) {
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        return found;
    }

    public Utente findByIdAndUpdate(UUID utenteId, UtentiPayloadDTO body) {
        String avatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(bcrypt.encode(body.password()));
        found.setNome(body.nome());
        found.setCognome(body.cognome());

        return utentiRepository.save(found);
    }

    public void findByIdAndDeleteUtente(UUID utenteId) {
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        this.utentiRepository.delete(found);
    }

    public Utente uploadAvatar(UUID utenteId, MultipartFile avatar) throws IOException{
        Utente found= this.findUtenteById(utenteId);
        String url= (String) cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("Url " + url);
        found.setAvatar(url);
        return this.utentiRepository.save(found);
    }
    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }
}
