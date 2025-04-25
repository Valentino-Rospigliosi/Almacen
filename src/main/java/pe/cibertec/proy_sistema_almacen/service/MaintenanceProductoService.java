// MaintenanceProductoService.java
package pe.cibertec.proy_sistema_almacen.service;

import pe.cibertec.proy_sistema_almacen.dto.ProductoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProductoListarDto;

import java.util.List;
import java.util.Optional;

public interface MaintenanceProductoService {
    List<ProductoListarDto> listarProductos() throws Exception;
    Optional<ProductoListarDto> listarProductoId(int id) throws Exception;
    boolean agregarProducto(ProductoCrearDto producto) throws Exception;
    boolean actualizarProducto(ProductoCrearDto producto) throws Exception;
    boolean borrarProductoId(int id) throws Exception;
}
