package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.LoginRequestDto;
import pe.cibertec.proy_sistema_almacen.dto.LoginResponseDto;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosListarDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceUsuariosService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuariosApisController {

    @Autowired
    private MaintenanceUsuariosService maintenanceUsuariosService;

    // GET: Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuariosListarDto>> listarUsuarios() throws Exception {
        List<UsuariosListarDto> usuarios = maintenanceUsuariosService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET: Obtener un usuario por ID (DETALLE)
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosListarDto> listarUsuariosPorId(@PathVariable int id) throws Exception {
        Optional<UsuariosListarDto> usuario = maintenanceUsuariosService.listarIdUsuarios(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Void> guardarUsuario(@RequestBody UsuariosCrearDto usuariosCrearDto) throws Exception {
        maintenanceUsuariosService.agregarUsuarios(usuariosCrearDto);
        return ResponseEntity.status(201).build(); // HTTP 201 Created
    }

    // PUT: Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuariosCrearDto usuariosCrearDto) throws Exception {
        UsuariosCrearDto dtoActualizado = new UsuariosCrearDto(
                id,
                usuariosCrearDto.nombreUsuario(),
                usuariosCrearDto.correo(),
                usuariosCrearDto.contrasenia(),
                usuariosCrearDto.rol(),
                usuariosCrearDto.estado()
        );

        boolean actualizado = maintenanceUsuariosService.actualizarUsuario(dtoActualizado);
        return actualizado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // DELETE: Borrar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable int id) throws Exception {
        maintenanceUsuariosService.borrarUsuarioId(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Optional<LoginResponseDto> loginResult = maintenanceUsuariosService
                    .loginUsuario(loginRequestDto.usuario(), loginRequestDto.contrasenia());

            if (loginResult.isPresent()) {
                return ResponseEntity.ok(loginResult.get()); // 200 OK con datos
            } else {
                // 401 con mensaje personalizado
                return ResponseEntity.status(401)
                        .body(Collections.singletonMap("mensaje", "Credenciales incorrectas. Revise su correo y contraseña"));
            }

        } catch (Exception e) {
            // 500 con mensaje de error
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("mensaje", "Error interno del servidor"));
        }
    }


}
