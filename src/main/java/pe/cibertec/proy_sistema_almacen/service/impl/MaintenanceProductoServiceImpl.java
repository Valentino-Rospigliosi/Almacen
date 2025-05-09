package pe.cibertec.proy_sistema_almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.proy_sistema_almacen.dto.ProductoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProductoListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Producto;
import pe.cibertec.proy_sistema_almacen.repository.ProductoRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceProductoServiceImpl implements MaintenanceProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoListarDto> listarProductos() {
        List<ProductoListarDto> dtos = new ArrayList<>();
        productoRepository.findAll().forEach(p -> dtos.add(new ProductoListarDto(
                p.getIdProducto(), p.getNombreProducto(), p.getDescripcion(),
                p.getStockActual(), p.getStockMinimo(), p.getEstado(), p.getCodigoPedido()
        )));
        return dtos;
    }

    @Override
    public Optional<ProductoListarDto> listarProductoId(int id) {
        return productoRepository.findById(id).map(p -> new ProductoListarDto(
                p.getIdProducto(), p.getNombreProducto(), p.getDescripcion(),
                p.getStockActual(), p.getStockMinimo(), p.getEstado(), p.getCodigoPedido()
        ));
    }

    @Override
    public boolean agregarProducto(ProductoCrearDto dto) {
        Producto p = new Producto(null, dto.nombreProducto(), dto.descripcion(),
                dto.stockActual(), dto.stockMinimo(), dto.estado(),dto.codigoPedido());
        productoRepository.save(p);
        return true;
    }

    @Override
    public boolean actualizarProducto(ProductoCrearDto dto) {
        Optional<Producto> optional = productoRepository.findById(dto.idProducto());
        return optional.map(p -> {
            p.setNombreProducto(dto.nombreProducto());
            p.setDescripcion(dto.descripcion());
            p.setStockActual(dto.stockActual());
            p.setStockMinimo(dto.stockMinimo());
            p.setEstado(dto.estado());
            p.setCodigoPedido(dto.codigoPedido());
            productoRepository.save(p);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean borrarProductoId(int id) {
        Optional<Producto> optional = productoRepository.findById(id);
        return optional.map(p -> {
            productoRepository.delete(p);
            return true;
        }).orElse(false);
    }
}

