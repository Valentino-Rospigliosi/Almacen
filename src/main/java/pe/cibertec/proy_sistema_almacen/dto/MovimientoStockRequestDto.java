package pe.cibertec.proy_sistema_almacen.dto;

import pe.cibertec.proy_sistema_almacen.entity.TipoMovimiento;

public record MovimientoStockRequestDto(Integer idProducto,
                                        TipoMovimiento tipoMovimiento,
                                        Integer cantidad,
                                        Integer idUsuario,
                                        String observacion) {
}
