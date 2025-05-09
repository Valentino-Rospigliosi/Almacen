package pe.cibertec.proy_sistema_almacen.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.proy_sistema_almacen.dto.MovimientoStockResponseDto;
import pe.cibertec.proy_sistema_almacen.entity.MovimientoStock;
import pe.cibertec.proy_sistema_almacen.entity.Producto;
import pe.cibertec.proy_sistema_almacen.entity.TipoMovimiento;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;
import pe.cibertec.proy_sistema_almacen.repository.MovimientoStockRepository;
import pe.cibertec.proy_sistema_almacen.repository.ProductoRepository;
import pe.cibertec.proy_sistema_almacen.repository.UsuariosRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceMovimientoStockService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceMovimientoStockServiceImp implements MaintenanceMovimientoStockService {

    @Autowired
    private MovimientoStockRepository movimientoStockRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;


    @Override
    @Transactional
    public void registrarMovimiento(Integer idProducto, TipoMovimiento tipo, int cantidad, Integer idUsuario, String observacion) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Usuarios usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (tipo == TipoMovimiento.SALIDA && producto.getStockActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente para la salida");
        }

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setRealizadoPor(usuario);
        movimiento.setObservacion(observacion);
        movimientoStockRepository.save(movimiento);

        int nuevoStock = tipo == TipoMovimiento.INGRESO
                ? producto.getStockActual() + cantidad
                : producto.getStockActual() - cantidad;

        producto.setStockActual(nuevoStock);
        productoRepository.save(producto);
    }

    @Override
    public List<MovimientoStockResponseDto> listarMovimientos() {
        return movimientoStockRepository.findAll()
                .stream()
                .map(m -> new MovimientoStockResponseDto(
                        m.getIdMovimiento(),
                        m.getProducto().getNombreProducto(),
                        m.getTipoMovimiento(),
                        m.getCantidad(),
                        m.getFechaMovimiento(),
                        m.getRealizadoPor().getNombreUsuario(),
                        m.getObservacion()
                ))
                .toList();
    }


}
