package pe.cibertec.proy_sistema_almacen.dto;

public record PedidoCrearDto(
        Integer idPedido,
        Integer idProveedor,
        String fechaRecepcion,
        String estado,
        String observacion,
        Integer idUsuario) {
}
