package pe.cibertec.proy_sistema_almacen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.MovimientoStockRequestDto;
import pe.cibertec.proy_sistema_almacen.dto.MovimientoStockResponseDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceMovimientoStockService;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientosStockApiController {

    @Autowired
    private MaintenanceMovimientoStockService movimientoStockService;

    @PostMapping
    public ResponseEntity<String> registrarMovimiento(@RequestBody MovimientoStockRequestDto request) {

        try {
            movimientoStockService.registrarMovimiento(
                    request.idProducto(),
                    request.tipoMovimiento(),
                    request.cantidad(),
                    request.idUsuario(),
                    request.observacion()
            );
            return ResponseEntity.ok( "✅ Movimiento registrado correctamente" );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body( "❌ Error: " + e.getMessage() );
        }

    }

    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDto>> listarMovimientos() {
        return ResponseEntity.ok(movimientoStockService.listarMovimientos());
    }


}
