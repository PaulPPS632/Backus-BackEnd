package com.example.backus.service.inventario;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backus.models.entity.globales.Archivo;
import com.example.backus.repository.globales.ArchivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    @Value("${CLOUDINARY_URL}")
    private String cloudinary_url;

    private final ArchivoRepository archivoRepository;
    public Archivo uploadFile(MultipartFile file) {
        try {
            System.out.println(cloudinary_url);
            Cloudinary cloudinary = new Cloudinary(cloudinary_url);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "producto/imagen_producto"));

            return archivoRepository.save(Archivo.builder().nombre(uploadResult.get("display_name").toString()).url(uploadResult.get("secure_url").toString()).tipo_Archivo("producto").build());  // Devuelve la URL de la imagen
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo a Cloudinary", e);
        }
    }
    public void deleteFiles(List<String> urls){
        archivoRepository.deleteByUrlIn(urls);
    }
    public void deleteFile(String url){
        archivoRepository.deleteByUrl(url);
    }
}
