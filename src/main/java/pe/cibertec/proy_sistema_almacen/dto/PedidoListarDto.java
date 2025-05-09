package pe.cibertec.proy_sistema_almacen.dto;

public record PedidoListarDto(
        Integer idPedido,
        String nombreProveedor,
        Integer idProveedor,
        String fechaRecepcion,
        String estado,
        String observacion,
        Integer idUsuario,
        String nombreUsuario) {
}
