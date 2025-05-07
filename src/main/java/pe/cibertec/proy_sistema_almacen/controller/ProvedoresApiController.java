package pe.cibertec.proy_sistema_almacen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorListarDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProveedoresService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/proveedores")
public class ProvedoresApiController {

    @Autowired
    private MaintenanceProveedoresService maintenanceProveedoresService;

    // GET: Listar todos los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorListarDto>> listarProveedores() throws Exception {
        List<ProveedorListarDto> proveedores = maintenanceProveedoresService.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }

    // GET: Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorListarDto> listarProveedorPorId(@PathVariable int id) throws Exception {
        Optional<ProveedorListarDto> proveedor = maintenanceProveedoresService.listarIdProveedor(id);
        return proveedor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<Void> guardarProveedor(@RequestBody ProveedorCrearDto proveedorCrearDto) throws Exception {
        maintenanceProveedoresService.agregarProveedor(proveedorCrearDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT: Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarProveedor(@PathVariable Integer id, @RequestBody ProveedorCrearDto proveedorCrearDto) throws Exception {
        // Setear el id al DTO
        ProveedorCrearDto proveedorActualizado = new ProveedorCrearDto(
                id,
                proveedorCrearDto.nombreProveedor(),
                proveedorCrearDto.contacto(),
                proveedorCrearDto.telefono(),
                proveedorCrearDto.direccion()
        );

        boolean actualizado = maintenanceProveedoresService.actualizarProveedor(proveedorActualizado);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProveedor(@PathVariable int id) throws Exception {
        maintenanceProveedoresService.borrarProveedorId(id);
        return ResponseEntity.noContent().build();
    }

}
