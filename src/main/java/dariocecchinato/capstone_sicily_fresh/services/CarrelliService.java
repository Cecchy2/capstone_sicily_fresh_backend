package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarrelliService {
    @Autowired
    private CarrelliRepository carrelliRepository;
    @Autowired
    private UtentiService utentiService;

    public Carrello findById(UUID carrelloId){
        return this.carrelliRepository.findById(carrelloId).orElseThrow(()-> new NotFoundException(carrelloId));
    }

    public Carrello creaCarrello(CarrelloPayloadDTO body){
        Utente cliente = this.utentiService.findUtenteById(body.cliente());
        Carrello carrello = new Carrello(cliente);
        return this.carrelliRepository.save(carrello);
    }
}
