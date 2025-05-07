package pe.cibertec.proy_sistema_almacen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.ProductoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProductoListarDto;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorListarDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProductoService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductosApisController {

    @Autowired
    private MaintenanceProductoService productoService;

    // GET: Listar productos
    @GetMapping
    public ResponseEntity<List<ProductoListarDto>> listarProductos() throws Exception {
        List<ProductoListarDto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    // POST: Registrar producto
    @PostMapping
    public ResponseEntity<Void> registrarProducto(@RequestBody ProductoCrearDto dto) throws Exception {
        productoService.agregarProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT: Editar producto
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarProducto(@PathVariable Integer id, @RequestBody ProductoCrearDto dto) throws Exception {
        productoService.actualizarProducto(dto);
        return ResponseEntity.ok().build();
    }

    // DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) throws Exception {
        productoService.borrarProductoId(id);
        return ResponseEntity.noContent().build();
    }

    // GET: Obtener un producto por ID (opcional pero recomendado)ProductoListarDto
    //@GetMapping("/{id}")
    //public ResponseEntity<ProductoListarDto> obtenerProducto(@PathVariable Integer id) throws Exception {
    //     Optional<ProveedorListarDto> producto = productoService.listarProductoId(id);
      // return ResponseEntity.ok(producto);
    //}
}
