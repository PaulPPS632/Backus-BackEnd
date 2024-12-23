package com.example.backus.controllers.inventario;

import com.example.backus.models.dto.inventario.*;
import com.example.backus.service.inventario.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/marca")
@RequiredArgsConstructor
public class MarcaController {
    
    private final MarcaService marcaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarcaResponse> getAllMarca() {
        return marcaService.getAll();
    }

    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    public List<MarcaResponse> getAllMarca(Pageable pageable) {
        return marcaService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarcaResponse getMarcaById(@PathVariable("id") Long id) {
        return marcaService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody MarcaRequest Marca) {
        marcaService.save(Marca);
    }

    @PostMapping("/saves")
    @ResponseStatus(HttpStatus.CREATED)
    public void savesAll(@RequestBody MarcasRequest marcas) {
        marcaService.savesAll(marcas);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody MarcaRequest marca) {
        marcaService.update(marca);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id) {marcaService.delete(id);}
}
