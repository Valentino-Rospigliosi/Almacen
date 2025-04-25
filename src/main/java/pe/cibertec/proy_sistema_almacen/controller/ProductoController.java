
package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.ProductoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProductoListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Producto;
import pe.cibertec.proy_sistema_almacen.repository.ProductoRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProductoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private MaintenanceProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/listar")
    public String listarProductos(Model model) throws Exception {
        List<ProductoListarDto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "productos-listar";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("producto", new ProductoCrearDto(null, "", "", 0, 0, "Activo"));
        return "productos-crear";
    }

    @PostMapping("/registrar")
    public String registrarProducto(@ModelAttribute("producto") ProductoCrearDto dto) throws Exception {
        productoService.agregarProducto(dto);
        return "redirect:/productos/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Optional<Producto> optional = productoRepository.findById(id);
        if (optional.isPresent()) {
            Producto p = optional.get();
            model.addAttribute("producto", new ProductoCrearDto(
                    p.getIdProducto(), p.getNombreProducto(), p.getDescripcion(),
                    p.getStockActual(), p.getStockMinimo(), p.getEstado()
            ));
            return "productos-editar";
        } else {
            return "redirect:/productos/listar";
        }
    }

    @PostMapping("/editar")
    public String editarProducto(@ModelAttribute("producto") ProductoCrearDto dto) throws Exception {
        productoService.actualizarProducto(dto);
        return "redirect:/productos/listar";
    }

    @PostMapping("/borrarProducto")
    public String eliminarProducto(@RequestParam("idProducto") Integer id) throws Exception {
        productoService.borrarProductoId(id);
        return "redirect:/productos/listar";
    }
}
