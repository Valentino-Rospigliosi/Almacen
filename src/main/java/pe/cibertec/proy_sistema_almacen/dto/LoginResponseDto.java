package pe.cibertec.proy_sistema_almacen.dto;

public record LoginResponseDto(
        String nombreUsuario,
        String correo,
        String rol)  {
}
