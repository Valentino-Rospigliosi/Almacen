package pe.cibertec.proy_sistema_almacen.dto;

public record PedidoListarDto(
        Integer idPedido,
        String nombreProveedor,
        String fechaRecepcion,
        String estado,
        String observacion,
        String nombreUsuario) {
}
