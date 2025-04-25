package pe.cibertec.proy_sistema_almacen.repository;

import org.springframework.data.repository.CrudRepository;
import pe.cibertec.proy_sistema_almacen.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
}
