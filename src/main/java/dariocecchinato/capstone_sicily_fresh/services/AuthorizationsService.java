package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.UnauthorizedException;
import dariocecchinato.capstone_sicily_fresh.payloads.UtenteLoginDTO;
import dariocecchinato.capstone_sicily_fresh.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationsService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiEGeneraToken(UtenteLoginDTO body){
        Utente found = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())){
            return this.jwtTools.createToken(found);
        } else{
            throw new UnauthorizedException("Errore nelle credenziali che hai fornito");
        }
    }
}
