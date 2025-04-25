package pe.cibertec.proy_sistema_almacen.dto;

public record UsuariosCrearDto(Integer idUsuario,
                               String nombreUsuario,
                               String correo,
                               String contrasenia,
                               String rol,
                               Boolean estado) {
}
