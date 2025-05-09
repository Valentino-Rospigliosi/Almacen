package pe.cibertec.proy_sistema_almacen.dto;

public record LoginResponseDto(
        Integer idUsuario,
        String nombreUsuario,
        String correo,
        String rol)  {
}
