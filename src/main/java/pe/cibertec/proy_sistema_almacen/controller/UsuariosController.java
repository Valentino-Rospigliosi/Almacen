package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;
import pe.cibertec.proy_sistema_almacen.repository.UsuariosRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceUsuariosService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    MaintenanceUsuariosService maintenanceUsuariosService;
    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {

        try {
            List<UsuariosListarDto> usuarios = maintenanceUsuariosService.listarUsuarios();
            model.addAttribute("usuarios", usuarios);

        }catch (Exception e) {

            e.printStackTrace();
            model.addAttribute("error", "Ocurrio un error al obtener los datos de la busqueda");
        }

        return "usuarios-listar";

    }

    @GetMapping("/{id}")
    public String listarUsuariosPorId(@PathVariable int id, Model model) throws Exception {

        Optional<UsuariosListarDto> usua = maintenanceUsuariosService.listarIdUsuarios(id);

        if (usua.isPresent()) {
            model.addAttribute("usuarios", usua.get());
        } else {
            model.addAttribute("error", "Usuario no encontrado con ID: " + id);
        }

        return "usuarios-listar";
    }

    @GetMapping("/registrar")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new UsuariosCrearDto(null, "", "", "", "", false));
        return "usuarios-crear";
    }

    @PostMapping("/registrar")
    public String guardarUsuario(@ModelAttribute("usuario") UsuariosCrearDto usuariosCrearDto) throws Exception {
        maintenanceUsuariosService.agregarUsuarios(usuariosCrearDto);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        Optional<Usuarios> optional = usuariosRepository.findById(id);

        if (optional.isPresent()) {
            Usuarios usuario = optional.get();

            UsuariosCrearDto usuariodto = new UsuariosCrearDto(
                    usuario.getIdUsuario(),
                    usuario.getNombreUsuario(),
                    usuario.getCorreo(),
                    usuario.getContrasenia(),
                    usuario.getRol(),
                    usuario.getEstado()
            );

            model.addAttribute("usuario", usuariodto);
            return "usuarios-editar";
        } else {
            return "redirect:/usuarios/listar";
        }
    }

    @PostMapping("/editar")
    public String actualizarUsuario(@ModelAttribute("usuario") UsuariosCrearDto usuariosCrearDto) throws Exception {

        boolean actualizado = maintenanceUsuariosService.actualizarUsuario(usuariosCrearDto);

        if (actualizado) {
            return "redirect:/usuarios/listar";
        } else {
            System.out.println(" No se encontró el usuario con ID: " + usuariosCrearDto.idUsuario());
            return "redirect:/usuarios/listar";
        }
    }

    @PostMapping("/borrarUsuario")
    public String borrarUsuario(@RequestParam("idUsuario") int id, Model model) throws Exception {
        try {
            maintenanceUsuariosService.borrarUsuarioId(id);
            System.out.println("✅ Usuario con ID: " + id + " eliminado correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar el usuario con ID: " + id);
            throw new RuntimeException(e);
        }
        return "redirect:/usuarios/listar";
    }


}
