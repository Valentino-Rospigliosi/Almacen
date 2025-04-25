package pe.cibertec.proy_sistema_almacen.repository;

import org.springframework.data.repository.CrudRepository;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;

import java.util.Optional;

public interface UsuariosRepository extends CrudRepository<Usuarios, Integer> {
    Optional<Usuarios> findByCorreo(String correo);
}
