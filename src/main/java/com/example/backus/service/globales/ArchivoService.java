package com.example.backus.service.globales;

import com.example.backus.models.entity.globales.Archivo;
import com.example.backus.repository.globales.ArchivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchivoService {

    private final ArchivoRepository archivoRepository;

    public Map<String, List<String>>  ImagenesPublicitarias(){
        //recolectalos datos por tipo y los ordena en un mar con clave descripcion y valor las url
        return archivoRepository.findAllByTipoOrderByDescripcion("imagenes_publicitarias")
                .stream().collect(Collectors.groupingBy(
                        Archivo::getDescripcion,
                        Collectors.mapping(Archivo::getUrl, Collectors.toList())));
    }

    public void save(String tipo, List<MultipartFile> files) {

        //archivoRepository.saveAll();
    }
}
