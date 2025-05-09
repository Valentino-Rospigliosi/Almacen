package pe.cibertec.proy_sistema_almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.proy_sistema_almacen.entity.MovimientoStock;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {
}
