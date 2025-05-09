package pe.cibertec.proy_sistema_almacen.dto;

import pe.cibertec.proy_sistema_almacen.entity.TipoMovimiento;

import java.time.LocalDateTime;

public record MovimientoStockResponseDto(Integer idMovimiento,
                                         String nombreProducto,
                                         TipoMovimiento tipoMovimiento,
                                         Integer cantidad,
                                         LocalDateTime fechaMovimiento,
                                         String realizadoPor,
                                         String observacion) {
}
