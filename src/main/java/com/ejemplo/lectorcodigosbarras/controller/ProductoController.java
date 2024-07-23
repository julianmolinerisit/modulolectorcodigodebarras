package com.ejemplo.lectorcodigosbarras.controller;

import com.ejemplo.lectorcodigosbarras.model.Producto;
import com.ejemplo.lectorcodigosbarras.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String getAllProductos(Model model) {
        model.addAttribute("productos", productoService.getAllProductos());
        return "productos";
    }

    @GetMapping("/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "nuevo_producto";
    }

    @PostMapping
    public String saveProducto(@ModelAttribute Producto producto) {
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProductoForm(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.getProductoById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "editar_producto";
        } else {
            return "redirect:/productos";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        producto.setId(id);
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return "redirect:/productos";
    }
}
