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


    public void saves(List<MultipartFile> files) {

        //archivoRepository.saveAll();
    }
}
