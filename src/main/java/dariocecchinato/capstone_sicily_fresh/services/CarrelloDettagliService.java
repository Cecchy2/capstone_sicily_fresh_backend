package dariocecchinato.capstone_sicily_fresh.services;

import dariocecchinato.capstone_sicily_fresh.entities.Carrello;
import dariocecchinato.capstone_sicily_fresh.entities.CarrelloDettaglio;
import dariocecchinato.capstone_sicily_fresh.entities.Ricetta;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioResponseDTO;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelliRepository;
import dariocecchinato.capstone_sicily_fresh.repositories.CarrelloDettagliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CarrelloDettagliService {
    @Autowired
    private CarrelloDettagliRepository carrelloDettagliRepository;
    @Autowired
    private CarrelliService carrelliService;
    @Autowired
    private RicetteService ricetteService;


    public CarrelloDettaglio creaCarrelloDettaglio(CarrelloDettaglioPayloadDTO body){
        Carrello carrello =  this.carrelliService.findById(body.carrello());
        Ricetta ricetta = this.ricetteService.findById(body.ricetta());
        CarrelloDettaglio carrelloDettaglio = new CarrelloDettaglio(carrello,ricetta, body.quantita());

        return this.carrelloDettagliRepository.save(carrelloDettaglio);
    }
}
