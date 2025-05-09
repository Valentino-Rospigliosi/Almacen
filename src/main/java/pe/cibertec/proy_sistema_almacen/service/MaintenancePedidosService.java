package pe.cibertec.proy_sistema_almacen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.cibertec.proy_sistema_almacen.dto.PedidoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.PedidoListarDto;

import java.util.List;
import java.util.Optional;

public interface MaintenancePedidosService {
    List<PedidoListarDto> listarPedidos() throws Exception;
    Optional<PedidoListarDto> listarIdPedido(int id) throws Exception;
    boolean agregarPedido(PedidoCrearDto pedido) throws Exception;
    boolean actualizarPedido(PedidoCrearDto pedido) throws Exception;
    boolean borrarPedidoId(int id) throws Exception;
    Page<PedidoListarDto> listarPedidosPaginados(Pageable pageable) throws Exception;
}
