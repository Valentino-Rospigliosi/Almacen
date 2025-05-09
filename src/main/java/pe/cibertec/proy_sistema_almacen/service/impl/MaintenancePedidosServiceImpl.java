package pe.cibertec.proy_sistema_almacen.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.cibertec.proy_sistema_almacen.dto.PedidoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.PedidoListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Pedido;
import pe.cibertec.proy_sistema_almacen.entity.Proveedores;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;
import pe.cibertec.proy_sistema_almacen.repository.PedidoRepository;
import pe.cibertec.proy_sistema_almacen.repository.ProveedoresRepository;
import pe.cibertec.proy_sistema_almacen.repository.UsuariosRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenancePedidosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MaintenancePedidosServiceImpl implements MaintenancePedidosService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public List<PedidoListarDto> listarPedidos() {
        List<PedidoListarDto> dtos = new ArrayList<>();
        pedidoRepository.findAll().forEach(p -> dtos.add(new PedidoListarDto(
                p.getIdPedido(),
                p.getProveedor().getNombreProveedor(),
                p.getProveedor().getIdProveedor(),
                p.getFechaRecepcion(),
                p.getEstado(),
                p.getObservacion(),
                p.getUsuario().getIdUsuario(),
                p.getUsuario().getNombreUsuario()
        )));
        return dtos;
    }

    @Override
    public Optional<PedidoListarDto> listarIdPedido(int id) {
        return pedidoRepository.findById(id).map(p -> new PedidoListarDto(
                p.getIdPedido(),
                p.getProveedor().getNombreProveedor(),
                p.getProveedor().getIdProveedor(),
                p.getFechaRecepcion(),
                p.getEstado(),
                p.getObservacion(),
                p.getUsuario().getIdUsuario(),
                p.getUsuario().getNombreUsuario()
        ));
    }

    @Transactional
    @Override
    public boolean agregarPedido(PedidoCrearDto dto) {
        Optional<Proveedores> proveedorOpt = proveedoresRepository.findById(dto.idProveedor());
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(dto.idUsuario());

        System.out.println("Guardando pedido: recien guardar" );

        if (proveedorOpt.isPresent() && usuarioOpt.isPresent()) {
            Pedido pedido = new Pedido(
                    null,
                    proveedorOpt.get(),
                    dto.fechaRecepcion(),
                    dto.estado(),
                    dto.observacion(),
                    usuarioOpt.get()
            );
            System.out.println("Guardando pedido: " + pedido);
            pedidoRepository.save(pedido);
            return true;
        }

        return false;
    }

    @Override
    public boolean actualizarPedido(PedidoCrearDto dto) {
        Optional<Pedido> optional = pedidoRepository.findById(dto.idPedido());
        Optional<Proveedores> proveedorOpt = proveedoresRepository.findById(dto.idProveedor());
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(dto.idUsuario());

        return optional.map(p -> {
            if (proveedorOpt.isPresent() && usuarioOpt.isPresent()) {
                p.setProveedor(proveedorOpt.get());
                p.setFechaRecepcion(dto.fechaRecepcion());
                p.setEstado(dto.estado());
                p.setObservacion(dto.observacion());
                p.setUsuario(usuarioOpt.get());
                pedidoRepository.save(p);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public boolean borrarPedidoId(int id) {
        Optional<Pedido> optional = pedidoRepository.findById(id);
        return optional.map(p -> {
            pedidoRepository.delete(p);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<PedidoListarDto> listarPedidosPaginados(Pageable pageable) throws Exception {
        return pedidoRepository.findAll(pageable)
                .map(p -> new PedidoListarDto(
                        p.getIdPedido(),
                        p.getProveedor().getNombreProveedor(),
                        p.getProveedor().getIdProveedor(),
                        p.getFechaRecepcion(),
                        p.getEstado(),
                        p.getObservacion(),
                        p.getUsuario().getIdUsuario(),
                        p.getUsuario().getNombreUsuario()
                ));
    }

}
