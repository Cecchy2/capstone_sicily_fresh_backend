package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelliRepository;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public Carrello creaCarrello(CarrelloPayloadDTO body) {
        Optional<Carrello> existingCarrello = carrelliRepository.findByClienteId(body.cliente());

        if (existingCarrello.isPresent()) {
            return existingCarrello.get();
        }
        Utente cliente = this.utentiService.findUtenteById(body.cliente());
        Carrello newCarrello = new Carrello(cliente);
        return this.carrelliRepository.save(newCarrello);
    }

    public Carrello findByClienteId(UUID clienteId){
        return this.carrelliRepository.findByClienteId(clienteId).orElseThrow(()-> new NotFoundException(clienteId));
    }
}
