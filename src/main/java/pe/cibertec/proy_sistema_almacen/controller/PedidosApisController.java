package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.PedidoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.PedidoListarDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenancePedidosService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosApisController {
    @Autowired
    private MaintenancePedidosService maintenancePedidosService;

    // GET: Listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoListarDto>> listarPedidos() throws Exception {
        List<PedidoListarDto> pedidos = maintenancePedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // GET: Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoListarDto> listarPedidoPorId(@PathVariable int id) throws Exception {
        Optional<PedidoListarDto> pedido = maintenancePedidosService.listarIdPedido(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<Void> guardarPedido(@RequestBody PedidoCrearDto pedidoCrearDto) throws Exception {
        maintenancePedidosService.agregarPedido(pedidoCrearDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT: Actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPedido(@PathVariable Integer id, @RequestBody PedidoCrearDto pedidoCrearDto) throws Exception {
        // Setear el id al DTO
        PedidoCrearDto pedidoActualizado = new PedidoCrearDto(
                id,
                pedidoCrearDto.idProveedor(),
                pedidoCrearDto.fechaRecepcion(),
                pedidoCrearDto.estado(),
                pedidoCrearDto.observacion(),
                pedidoCrearDto.idUsuario()
        );

        boolean actualizado = maintenancePedidosService.actualizarPedido(pedidoActualizado);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPedido(@PathVariable int id) throws Exception {
        maintenancePedidosService.borrarPedidoId(id);
        return ResponseEntity.noContent().build();
    }

}
