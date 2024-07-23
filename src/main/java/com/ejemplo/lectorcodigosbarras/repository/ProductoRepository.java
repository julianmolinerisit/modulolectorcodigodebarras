package com.ejemplo.lectorcodigosbarras.repository;

import com.ejemplo.lectorcodigosbarras.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoDeBarras(String codigoDeBarras);
}
