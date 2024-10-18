package dariocecchinato.capstone_sicily_fresh.controllers;


import dariocecchinato.capstone_sicily_fresh.entities.CarrelloDettaglio;
import dariocecchinato.capstone_sicily_fresh.enums.StatoOrdine;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.CarrelloDettaglioResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.CarrelloDettagliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carrelloDettagli")
public class CarrelloDettagliController {
    @Autowired
    private CarrelloDettagliService carrelloDettagliService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public CarrelloDettaglioResponseDTO creaCarrelloDettaglio(@RequestBody @Validated CarrelloDettaglioPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + messages);
        }else{
            return new CarrelloDettaglioResponseDTO(this.carrelloDettagliService.creaCarrelloDettaglio(body).getId());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Page<CarrelloDettaglio> getAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortby){
        return this.carrelloDettagliService.getAll(page, size, sortby);
    }

    @GetMapping("/{carrelloId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public List<CarrelloDettaglio> getCarrelloDettaglioByCarrello(@PathVariable UUID carrelloId){
        return this.carrelloDettagliService.findByCarrelloId(carrelloId);
    }

    @DeleteMapping("/{carrelloDettaglioId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCArrelloDettaglio(@PathVariable UUID carrelloDettaglioId){
        this.carrelloDettagliService.deleteCarrelloDettaglio(carrelloDettaglioId);
    }

    @GetMapping("/ricetta/{ricettaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public List<CarrelloDettaglio> getCarrelloDettaglioByRicetta(@PathVariable UUID ricettaId){
        return this.carrelloDettagliService.findByRicettaId(ricettaId);
    }

    @GetMapping("/ricetta/{ricettaId}/stato/{statoOrdine}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public List<CarrelloDettaglio> getCarrelloDettaglioByRicettaAndStato(
            @PathVariable UUID ricettaId,
            @PathVariable StatoOrdine statoOrdine) {
        return this.carrelloDettagliService.findByRicettaIdAndStatoOrdine(ricettaId, statoOrdine);
    }

    @PatchMapping("/{carrelloDettaglioId}/stato")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    @ResponseStatus(HttpStatus.OK)
    public CarrelloDettaglioResponseDTO aggiornaStatoOrdine(
            @PathVariable UUID carrelloDettaglioId,
            @RequestBody Map<String, String> body) {

        if (!body.containsKey("statoOrdine")) {
            throw new BadRequestException("Il campo statoOrdine è obbligatorio");
        }

        String statoOrdineStr = body.get("statoOrdine").toUpperCase();

        StatoOrdine statoOrdine;
        try {
            statoOrdine = StatoOrdine.valueOf(statoOrdineStr);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("StatoOrdine non valido: " + statoOrdineStr);
        }

        CarrelloDettaglio carrelloDettaglioAggiornato = carrelloDettagliService.aggiornaStatoOrdine(carrelloDettaglioId, statoOrdine);

        return new CarrelloDettaglioResponseDTO(carrelloDettaglioAggiornato.getId());
    }

    @GetMapping("/fornitore/{fornitoreId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    @ResponseStatus(HttpStatus.OK)
    public List<CarrelloDettaglio> getCarrelloDettagliByFornitoreId(@PathVariable UUID fornitoreId) {
        List<CarrelloDettaglio> carrelloDettagli = carrelloDettagliService.findCarrelloDettagliByFornitoreId(fornitoreId);

        if (carrelloDettagli.isEmpty()) {
            throw new NotFoundException("Nessun Carrello trovato per il fornitore con id : " + fornitoreId);
        }

        return carrelloDettagli;
    }



}
