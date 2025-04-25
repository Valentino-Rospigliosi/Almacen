package pe.cibertec.proy_sistema_almacen.service;

import pe.cibertec.proy_sistema_almacen.dto.UsuariosCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosListarDto;

import java.util.List;
import java.util.Optional;

public interface MaintenanceUsuariosService {

    // Creacion de métodos
    /**
     * listar
     * crear
     * modificar
     * eliminar
     * buscar
     * */

    List<UsuariosListarDto> listarUsuarios() throws Exception;

    Optional<UsuariosListarDto> listarIdUsuarios(int id) throws Exception;

    boolean agregarUsuarios(UsuariosCrearDto usuariosCrearDto) throws Exception;

    boolean actualizarUsuario(UsuariosCrearDto usuariosCrearDto) throws Exception;

    boolean borrarUsuarioId(int id) throws Exception;

}
