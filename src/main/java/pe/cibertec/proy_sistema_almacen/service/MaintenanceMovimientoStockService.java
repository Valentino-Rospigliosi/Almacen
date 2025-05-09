package pe.cibertec.proy_sistema_almacen.service;

import pe.cibertec.proy_sistema_almacen.dto.MovimientoStockResponseDto;
import pe.cibertec.proy_sistema_almacen.entity.TipoMovimiento;

import java.util.List;

public interface MaintenanceMovimientoStockService {

    void registrarMovimiento(Integer idProducto, TipoMovimiento tipo, int cantidad, Integer idUsuario, String observacion);

    List<MovimientoStockResponseDto> listarMovimientos();

}
