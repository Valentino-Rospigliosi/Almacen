package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Proveedores;
import pe.cibertec.proy_sistema_almacen.repository.ProveedoresRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProveedoresService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    MaintenanceProveedoresService maintenanceProveedoresService;
    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @GetMapping("/listar")
    public String listarProveedores(Model model) {
        try {
            List<ProveedorListarDto> proveedores = maintenanceProveedoresService.listarProveedores();
            model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener la lista de proveedores.");
        }
        return "proveedores-listar";
    }

    @GetMapping("/{id}")
    public String listarProveedorPorId(@PathVariable int id, Model model) throws Exception {
        Optional<ProveedorListarDto> proveedor = maintenanceProveedoresService.listarIdProveedor(id);
        if (proveedor.isPresent()) {
            model.addAttribute("proveedor", proveedor.get());
        } else {
            model.addAttribute("error", "Proveedor no encontrado con ID: " + id);
        }
        return "proveedores-listar";
    }

    @GetMapping("/registrar")
    public String crearProveedor(Model model) {
        model.addAttribute("proveedor", new ProveedorCrearDto(null, "", "", "", ""));
        return "proveedores-crear";
    }

    @PostMapping("/registrar")
    public String guardarProveedor(@ModelAttribute("proveedor") ProveedorCrearDto proveedorCrearDto) throws Exception {
        maintenanceProveedoresService.agregarProveedor(proveedorCrearDto);
        return "redirect:/proveedores/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        Optional<Proveedores> optional = proveedoresRepository.findById(id);
        if (optional.isPresent()) {
            Proveedores proveedor = optional.get();
            ProveedorCrearDto dto = new ProveedorCrearDto(
                    proveedor.getIdProveedor(),
                    proveedor.getNombreProveedor(),
                    proveedor.getContacto(),
                    proveedor.getTelefono(),
                    proveedor.getDireccion()
            );
            model.addAttribute("proveedor", dto);
            return "proveedores-editar";
        } else {
            return "redirect:/proveedores/listar";
        }
    }

    @PostMapping("/editar")
    public String actualizarProveedor(@ModelAttribute("proveedor") ProveedorCrearDto proveedorCrearDto) throws Exception {
        boolean actualizado = maintenanceProveedoresService.actualizarProveedor(proveedorCrearDto);
        if (actualizado) {
            return "redirect:/proveedores/listar";
        } else {
            System.out.println("❌ No se encontró el proveedor con ID: " + proveedorCrearDto.idProveedor());
            return "redirect:/proveedores/listar";
        }
    }

    @PostMapping("/borrarProveedor")
    public String borrarProveedor(@RequestParam("idProveedor") int id) throws Exception {
        try {
            maintenanceProveedoresService.borrarProveedorId(id);
            System.out.println("✅ Proveedor con ID: " + id + " eliminado correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar el proveedor con ID: " + id);
            throw new RuntimeException(e);
        }
        return "redirect:/proveedores/listar";
    }
}
