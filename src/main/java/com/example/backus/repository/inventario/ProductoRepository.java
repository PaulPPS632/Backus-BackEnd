package com.example.backus.repository.inventario;

import com.example.backus.models.dto.inventario.CategoriaProductoDTO;
import com.example.backus.models.entity.inventario.Categoria;
import com.example.backus.models.entity.inventario.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, String>, JpaSpecificationExecutor<Producto> {


    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:keyword% OR p.descripcion LIKE %:keyword%")
    List<Producto> findByNombreOrDescripcionContaining(@Param("keyword") String keyword);

    @Query(value = "CALL GetProductosGroupedByCategoriaWithLimit(:limit)", nativeQuery = true)
    List<Object[]> findAllGroupedByCategoriaWithLimit(@Param("limit") int limit);

    List<Producto> findTop10ByCategoria(Categoria categoria);

    List<Producto> findByCategoria(Categoria categoria);


    @Query("SELECT p FROM Producto p WHERE " +
            "(:search IS NULL OR p.nombre LIKE %:search%) AND " +
            "(:marcaId IS NULL OR p.marca.id = :marcaId) AND " +
            "(:categoriaId IS NULL OR p.categoria.id = :categoriaId)")
    List<Producto> findByFiltros(
            @Param("search") String search,
            @Param("marcaId") Long marcaId,
            @Param("categoriaId") Long categoriaId,
            Pageable pageable
    );
    /*
    Page<Producto> findByCategoriamarca_Marca_NombreInAndSubcategoria_Categoria_NombreIn(List<String> marca, List<String> categoria, Pageable pageable);

    Page<Producto> findByCategoriamarca_Marca_NombreIn(List<String> marca, Pageable pageable);
    Page<Producto> findBySubcategoria_Categoria_NombreIn(List<String> categoria, Pageable pageable);

    Page<Producto> findByCategoriamarca_Marca_NombreAndSubcategoria_Nombre(String marca, String subcategoria, Pageable pageable);


    //Page<Producto> findByCategoriamarca_Marca_NombreInAndSubcategoria_Categoria_NombreInOrSubcategoria_NombreInAndNombreContaining(List<String> marca, List<String> categoria, List<String> subcategoria,String nombre, Pageable pageable);

    // Consulta para buscar por marca y nombre
    Page<Producto> findByCategoriamarca_Marca_NombreInAndNombreContaining(
            List<String> marca,
            String nombre,
            Pageable pageable);

    // Consulta para buscar por categoría, subcategoría y nombre
    Page<Producto> findBySubcategoria_Categoria_NombreInOrSubcategoria_NombreInAndNombreContaining(
            List<String> categoria,
            List<String> subcategoria,
            String nombre,
            Pageable pageable);

     */
}
