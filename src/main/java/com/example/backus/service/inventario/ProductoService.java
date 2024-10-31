package com.example.backus.service.inventario;

import com.example.backus.exception.ResourceNotFoundException;
import com.example.backus.models.dto.inventario.CategoriaDTO;
import com.example.backus.models.dto.inventario.CategoriaResponse;
import com.example.backus.models.dto.inventario.ProductoRequest;
import com.example.backus.models.dto.inventario.ProductoResponse;
import com.example.backus.models.entity.globales.Archivo;
import com.example.backus.models.entity.inventario.Categoria;
import com.example.backus.models.entity.inventario.Marca;
import com.example.backus.models.entity.inventario.Producto;
import com.example.backus.repository.globales.ArchivoRepository;
import com.example.backus.repository.inventario.CategoriaRepository;
import com.example.backus.repository.inventario.MarcaRepository;
import com.example.backus.repository.inventario.ProductoRepository;
import com.example.backus.repository.inventario.ProductoSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ArchivoRepository archivoRepository;

    //@Value("${aws.namebucket}")
    //private String namebucket;

    //@Value("${aws.region}")
    //private String region;

    public List<ProductoResponse> getAll(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToProductoResponse).toList();
    }

    public ProductoResponse getById(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            return mapToProductoResponse(producto.get());
        }else {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
    }

    public void save(ProductoRequest producto, List<MultipartFile> files, MultipartFile fileprincipal){
        Optional<Marca> categoriamarcaoptional = marcaRepository.findById(producto.id_marca());
        if(categoriamarcaoptional.isEmpty())throw new ResourceNotFoundException("CategoriaMarca no encontrado con id: " + producto.id_marca());
        Optional<Categoria> subCategoriaOptional = categoriaRepository.findById(producto.id_categoria());
        if(subCategoriaOptional.isEmpty()) throw new ResourceNotFoundException("SubCategoria no encontrado con id: " + producto.id_categoria());

        Marca marca = categoriamarcaoptional.get();
        Categoria categoria = subCategoriaOptional.get();

        /*
        List<Archivo> archivos = new ArrayList<>();
        for (MultipartFile file : files) {
            archivos.add(s3service.uploadObject(file,"imagen_producto", "producto"));
        }
        */

        Producto nuevo = new Producto().builder()
                .nombre(producto.nombre())
                .pn(producto.pn())
                .descripcion(producto.descripcion())
                .Stock(producto.stock())
                .precio(producto.precio())
                .marca(marca)
                .categoria(categoria)
                .garantia_cliente(producto.garantia_cliente())
                .garantia_total(producto.garantia_total())
                //.archivo_Principal(s3service.uploadObject(fileprincipal,"imagen_producto", "producto"))
                //.archivos(archivos)
                .build();

        if(marca.getProductos() == null){
            marca.setProductos(new ArrayList<>());
        }
        if(categoria.getProducto() == null){
            categoria.setProducto(new ArrayList<>());
        }
        categoria.getProducto().add(nuevo);
        marca.getProductos().add(nuevo);

        productoRepository.save(nuevo);
        categoriaRepository.save(categoria);
        marcaRepository.save(marca);
    }

    private void uploadFile(MultipartFile file)throws IOException{
//        try {
//            String filename = file.getOriginalFilename();
//            AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
//            //PutObjectRequest putObjectrequest = new PutObjectRequest(namebucket,filename,file.getInputStream(), null);
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            s3.putObject(namebucket, filename, file.getInputStream(),metadata);
//        }catch (IOException e){
//            throw new IOException(e.getMessage());
//        }
    }
    public void update(ProductoRequest productoRequest, List<MultipartFile> files, MultipartFile fileprincipal) {
        Optional<Producto> productoOptional = productoRepository.findById(productoRequest.id());

        if (productoOptional.isEmpty()) throw new ResourceNotFoundException("Producto no encontrado con id: " + productoRequest.id());

        Producto productoActual = productoOptional.get();
        Marca categoriaMarcaActual = productoActual.getMarca();
        Categoria subCategoriaActual = productoActual.getCategoria();

        // Actualizamos los datos del producto existente
        productoActual.setNombre(productoRequest.nombre());
        productoActual.setPn(productoRequest.pn());
        productoActual.setDescripcion(productoRequest.descripcion());
        productoActual.setStock(productoRequest.stock());
        productoActual.setPrecio(productoRequest.precio());
        productoActual.setGarantia_cliente(productoRequest.garantia_cliente());
        productoActual.setGarantia_total(productoRequest.garantia_total());

        // Verificamos si la categoría ha cambiado
        if (!categoriaMarcaActual.getId().equals(productoRequest.id_marca())) {
            // Removemos el producto de la lista de la categoría actual
            categoriaMarcaActual.getProductos().remove(productoActual);

            // Buscamos la nueva categoría
            Optional<Marca> nuevaCategoriaOptional = marcaRepository.findById(productoRequest.id_marca());

            if (nuevaCategoriaOptional.isEmpty()) throw new ResourceNotFoundException("Categoría nueva no encontrada con id: " + productoRequest.id_marca());

            Marca nuevaCategoria = nuevaCategoriaOptional.get();

            // Asignamos la nueva categoría al producto
            productoActual.setMarca(nuevaCategoria);
            nuevaCategoria.getProductos().add(productoActual);

            // Guardamos la nueva categoría
            marcaRepository.save(nuevaCategoria);
        }
        // Verificamos si la subcategoría ha cambiado
        if (!subCategoriaActual.getId().equals(productoRequest.id_categoria())) {
            // Removemos el producto de la lista de la subcategoría actual
            subCategoriaActual.getProducto().remove(productoActual);

            // Buscamos la nueva subcategoría
            Optional<Categoria> nuevaSubCategoriaOptional = categoriaRepository.findById(productoRequest.id_categoria());

            if (nuevaSubCategoriaOptional.isEmpty()) throw new ResourceNotFoundException("SubCategoria nueva no encontrada con id: " + productoRequest.id_categoria());

            Categoria nuevaSubCategoria = nuevaSubCategoriaOptional.get();

            // Asignamos la nueva subcategoría al producto
            productoActual.setCategoria(nuevaSubCategoria);
            nuevaSubCategoria.getProducto().add(productoActual);

            // Guardamos la nueva subcategoría
            categoriaRepository.save(nuevaSubCategoria);
        }
        /*
        List<Archivo> eliminados = productoActual.getArchivos().stream().filter(p -> !productoRequest.imageurl().contains(p.getUrl())).collect(Collectors.toList());
        productoActual.getArchivos().removeAll(eliminados);

        Archivo principal = productoActual.getArchivo_Principal();
        if(fileprincipal != null && !fileprincipal.isEmpty()){

            productoActual.setArchivo_Principal(null);
            productoActual.setArchivo_Principal(s3service.uploadObject(fileprincipal,"imagen_producto", "producto"));
        }
        if(files != null && files.stream().anyMatch(file -> file != null && !file.getOriginalFilename().isEmpty())){
            productoActual.getArchivos().addAll(s3service.uploadsObjects(files, "imagen_producto", "producto"));
        }
        */
        // Guardamos el producto actualizado
        productoRepository.save(productoActual);

        // Guardamos la categoría actual si ha cambiado
        if (!categoriaMarcaActual.getId().equals(productoRequest.id_marca())) {
            marcaRepository.save(categoriaMarcaActual);
        }
        // Guardamos la Subcategoría actual si ha cambiado
        if (!subCategoriaActual.getId().equals(productoRequest.id_categoria())) {
            categoriaRepository.save(subCategoriaActual);
        }
        /*
        if(principal != null){
            archivoRepository.delete(principal);
            s3service.deleteFile(principal);
        }
        if(!eliminados.isEmpty()){
            archivoRepository.deleteAll(eliminados);
            s3service.deleteFiles(eliminados);
        }
*/
    }

    public void delete(String id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.delete(productoOptional.get());
    }



    public List<ProductoResponse> Busqueda(String keyboard){
        List<Producto> productos = productoRepository.findByNombreOrDescripcionContaining(keyboard);
        return productos.stream().map(this::mapToProductoResponse).toList();
    }

    public List<ProductoResponse> getAllPaged(String search,List<String> marca, List<String> categoria,List<String> subcategoria, Pageable pageable) {

        Specification<Producto> specification = ProductoSpecifications.withFilters(marca, categoria, subcategoria, search);
        Page<Producto> productos = productoRepository.findAll(specification, pageable);

        return productos.stream().map(this::mapToProductoResponse).toList();
    }
    public List<ProductoResponse> getByCategoria(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if(categoria.isEmpty()) throw new ResourceNotFoundException("ID de categoria incorrecto");
        List<Producto> productos = productoRepository.findByCategoria(categoria.get());
        return productos.stream().map(this::mapToProductoResponse).toList();

    }
    public List<CategoriaDTO> getForCategories(){
        List<Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream().map(categoria -> {
            List<Producto> productos = productoRepository.findTop10ByCategoria(categoria);
            List<ProductoResponse> productoDTOs = productos.stream().map(this::mapToProductoResponse).collect(Collectors.toList());
            return new CategoriaDTO(maptoCategoriaResponse(categoria), productoDTOs);
        }).collect(Collectors.toList());
    }
    private CategoriaResponse maptoCategoriaResponse(Categoria categoria){
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
    public ProductoResponse mapToProductoResponse(Producto producto){
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getPn(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getMarca().getNombre(),
                producto.getCategoria().getNombre(),
                producto.getGarantia_cliente(),
                producto.getGarantia_total(),
                producto.getArchivo_Principal() != null ? producto.getArchivo_Principal().getUrl() : "",
                producto.getArchivos().stream().map(Archivo::getUrl).collect(Collectors.toList())
        );
    }
    public Map<String, List<ProductoResponse>> getProductosGroupedByCategoria(int limit) {
        List<Object[]> results = productoRepository.findAllGroupedByCategoriaWithLimit(limit);
        Map<String, List<ProductoResponse>> productosPorCategoria = new HashMap<>();
        for (Object[] result : results) {
            String categoriaNombre = (String) result[9];
            ProductoResponse productoResponse = new ProductoResponse(
                    (String) result[0], // id
                    (String) result[1], // nombre
                    (String) result[2], // pn
                    (String) result[3], // descripción
                    result[4] != null ? ((Number) result[4]).doubleValue() : null, // stock
                    result[5] != null ? ((Number) result[5]).doubleValue() : null, // precio
                    (String) result[9],
                    (String) result[6],
                    result[7] != null ? ((Number) result[7]).doubleValue() : null,//garantia_cliente
                    result[8] != null ? ((Number) result[8]).doubleValue() : null,//garantia_total
                    (String) result[10], // archivo_principal_url
                    result[11] != null ? Arrays.asList(((String) result[11]).split(",")) : Collections.emptyList() // archivos_urls
            );

            productosPorCategoria
                    .computeIfAbsent(categoriaNombre, k -> new ArrayList<>())
                    .add(productoResponse);
        }

        return productosPorCategoria;
    }

    public List<ProductoResponse> buscarProductos(int page, int size, String search, String sort, Long marcaId, Long categoriaId) {
        Pageable pageable = PageRequest.of(page, size); // Configura la paginación

        // Realiza la búsqueda en el repositorio
        return productoRepository.findByFiltros(search,marcaId, categoriaId, pageable).stream().map(this::mapToProductoResponse).toList();
    }
}
