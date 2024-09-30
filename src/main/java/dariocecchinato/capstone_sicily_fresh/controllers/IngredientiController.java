package dariocecchinato.capstone_sicily_fresh.controllers;

import dariocecchinato.capstone_sicily_fresh.entities.Ingrediente;
import dariocecchinato.capstone_sicily_fresh.exceptions.BadRequestException;
import dariocecchinato.capstone_sicily_fresh.exceptions.NotFoundException;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiPayloadDTO;
import dariocecchinato.capstone_sicily_fresh.payloads.IngredientiResponseDTO;
import dariocecchinato.capstone_sicily_fresh.services.IngredientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredienti")
public class IngredientiController {
    @Autowired
    private IngredientiService ingredientiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientiResponseDTO creaIngrediente (@RequestBody @Validated IngredientiPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }else{
                return new IngredientiResponseDTO(this.ingredientiService.saveIngrediente(body).getId());
            }
        }

    @GetMapping
    public Page<Ingrediente> findAll (@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortby){
        return ingredientiService.findAll(page, size, sortby);
        }

    @GetMapping("/{ingredienteId}")
    public Ingrediente findById (@PathVariable UUID ingredienteId){
            Ingrediente found = this.ingredientiService.findById(ingredienteId);
            if (found == null) throw new NotFoundException(ingredienteId);
            return found;
        }

    @PutMapping("/{ingredienteId}")
    public Ingrediente findByIdAndUpdate(@PathVariable UUID ingredienteId, @RequestBody @Validated IngredientiPayloadDTO body, BindingResult validationResult){
            if (validationResult.hasErrors()) {
                String messages = validationResult.getAllErrors().stream()
                        .map(objectError -> objectError.getDefaultMessage())
                        .collect(Collectors.joining(". "));
                throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
            }else{
                return this.ingredientiService.findByIdAndUpdate(ingredienteId, body);
            }
        }


    @DeleteMapping("/{ingredienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete (@PathVariable UUID ingredienteId){
        this.ingredientiService.findByIdAndDelete(ingredienteId);
    }
}




