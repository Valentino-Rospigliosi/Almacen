package pe.cibertec.proy_sistema_almacen.dto;

public record ProductoCrearDto(
        Integer idProducto,
        String nombreProducto,
        String descripcion,
        Integer stockActual,
        Integer stockMinimo,
        String estado,
        Integer codigoPedido) {
}