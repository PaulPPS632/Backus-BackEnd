package com.example.backus.controllers.inventario;

import com.example.backus.models.dto.inventario.CategoriaDTO;
import com.example.backus.models.dto.inventario.ProductoRequest;
import com.example.backus.models.dto.inventario.ProductoResponse;
import com.example.backus.service.inventario.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse> getAll(){
        return productoService.getAll();
    }

    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse> getAllMarca(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "") Long MarcaId,
            @RequestParam(defaultValue = "") Long CategoriaId) {

        return productoService.buscarProductos(page, size, search, sort, MarcaId, CategoriaId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoResponse getById(@PathVariable("id") String id){
        return productoService.getById(id);
    }


    @GetMapping("/categoria/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse> getByCategoria(@PathVariable("id") Long id){
        return productoService.getByCategoria(id);
    }

    @GetMapping("/search/{keyboard}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse> getByKey(@PathVariable("keyboard") String keyboard){return productoService.Busqueda(keyboard);}

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestPart("producto") String productoJson,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("fileprincipal") MultipartFile fileprincipal){
        ProductoRequest producto = null;
        try {
            producto = new ObjectMapper().readValue(productoJson, ProductoRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productoService.save(producto, files, fileprincipal);
    }

    @PutMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @RequestPart("producto") String productoJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value="fileprincipal", required = false) MultipartFile fileprincipal
    ){
        ProductoRequest producto = null;
        try {
            producto = new ObjectMapper().readValue(productoJson, ProductoRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productoService.update(producto, files,fileprincipal);
    }

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaDTO> getHome(){
        return  productoService.getForCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") String id){
        productoService.delete(id);
    }

    @GetMapping("/CategoriaProducto")
    public ResponseEntity<Map<String, List<ProductoResponse>>> getProductosGroupedByCategoria(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        Map<String, List<ProductoResponse>> productosPorCategoria = productoService.getProductosGroupedByCategoria(limit);
        return ResponseEntity.ok(productosPorCategoria);
    }
}
