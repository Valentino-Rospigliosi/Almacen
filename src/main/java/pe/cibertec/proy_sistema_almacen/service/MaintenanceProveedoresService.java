package pe.cibertec.proy_sistema_almacen.service;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorListarDto;

import java.util.List;
import java.util.Optional;

public interface MaintenanceProveedoresService {
    List<ProveedorListarDto> listarProveedores() throws Exception;
    Optional<ProveedorListarDto> listarIdProveedor(int id) throws Exception;
    boolean agregarProveedor(ProveedorCrearDto proveedorDto) throws Exception;
    boolean actualizarProveedor(ProveedorCrearDto proveedorDto) throws Exception;
    boolean borrarProveedorId(int id) throws Exception;
}
