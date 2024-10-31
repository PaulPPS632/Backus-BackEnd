package com.example.backus.service.inventario;

import com.example.backus.exception.ResourceNotFoundException;
import com.example.backus.models.dto.inventario.CategoriaMarcaResponse;
import com.example.backus.models.dto.inventario.MarcaRequest;
import com.example.backus.models.dto.inventario.MarcaResponse;
import com.example.backus.models.dto.inventario.MarcasRequest;
import com.example.backus.models.entity.inventario.Marca;
import com.example.backus.repository.inventario.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaService {
    
    private final MarcaRepository marcaRepository;

    public List<MarcaResponse> getAllPaged(Pageable pageable){
        Page<Marca> marca = marcaRepository.findAll(pageable);
        return marca.stream().map(this::mapToMarcaResponse).toList();
    }
    public List<MarcaResponse> getAll(){
        List<Marca> marcas = marcaRepository.findAll();
        //marcas.forEach(marca -> Hibernate.initialize(marca.getCategoriamarcas()));
        return marcas.stream().map(this::mapToMarcaResponse).toList();
    }
    public MarcaResponse getById(Long id){
        Optional<Marca> marca = marcaRepository.findById(id);
        MarcaResponse result = null;
        if(marca.isPresent()){
            result = mapToMarcaResponse(marca.get());
        }else{
            result = mapToMarcaResponse(null);
        }
        return result;
    }
    private MarcaResponse mapToMarcaResponse(Marca marca){
        return new MarcaResponse(marca.getId(), marca.getNombre());
    }

    public void save(MarcaRequest marcaRequest){
        marcaRepository.save(new Marca().builder().nombre(marcaRequest.nombre()).build());
    }
    public void savesAll(MarcasRequest marcasRequest){
        marcaRepository.saveAll(marcasRequest.marcas().stream().map(this::mapToMarca).toList());
    }
    private Marca mapToMarca(MarcaRequest marcaRequest){
        return new Marca().builder().nombre(marcaRequest.nombre()).build();
    }

    public void update(Long id, MarcaRequest marca) {
        Optional<Marca> m = marcaRepository.findById(id);
        if(m.isPresent()){
            Marca actual = m.get();
            actual.setNombre(marca.nombre());
            marcaRepository.save(actual);
        }
    }
    public void delete(Long id){
        Optional<Marca> marca = marcaRepository.findById(id);
        if(marca.isEmpty()) throw new ResourceNotFoundException("Marca no encontrada con id: " + marca.get().getId());
        marcaRepository.deleteById(id);
    }
}
