package com.ejemplo.lectorcodigosbarras.controller;

import com.ejemplo.lectorcodigosbarras.model.Producto;
import com.ejemplo.lectorcodigosbarras.service.BarcodeReaderService;
import com.ejemplo.lectorcodigosbarras.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/barcode")
public class BarcodeController {
    @Autowired
    private BarcodeReaderService barcodeReaderService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/configure")
    public String configurePortPage(Model model) {
        String[] ports = barcodeReaderService.getAvailablePorts();
        model.addAttribute("ports", ports);
        return "configure";
    }

    @PostMapping("/configure")
    public String configurePort(@RequestParam("portName") String portName, Model model) {
        barcodeReaderService.configurePort(portName);
        model.addAttribute("message", "Port " + portName + " configured successfully.");
        return "redirect:/barcode/scan";
    }

    @GetMapping("/scan")
    public String scanPage() {
        return "scan";
    }

    @GetMapping("/read")
    public String readBarcode(Model model) {
        try {
            String codigoDeBarras = barcodeReaderService.readBarcode();
            Optional<Producto> producto = productoService.getProductoByCodigoDeBarras(codigoDeBarras);
            if (producto.isPresent()) {
                model.addAttribute("producto", producto.get());
                return "barcode";
            } else {
                return "redirect:/productos/nuevo?codigoDeBarras=" + codigoDeBarras;
            }
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "barcode";
        }
    }
}
