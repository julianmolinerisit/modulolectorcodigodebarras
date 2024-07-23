package com.ejemplo.lectorcodigosbarras.service;

import com.ejemplo.lectorcodigosbarras.model.Producto;
import com.ejemplo.lectorcodigosbarras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> getProductoByCodigoDeBarras(String codigoDeBarras) {
        return productoRepository.findByCodigoDeBarras(codigoDeBarras);
    }

    public Iterable<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
