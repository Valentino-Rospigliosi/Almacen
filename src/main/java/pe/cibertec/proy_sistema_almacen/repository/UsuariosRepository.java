package pe.cibertec.proy_sistema_almacen.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;

import java.util.Optional;

public interface UsuariosRepository extends CrudRepository<Usuarios, Integer> {

    Optional<Usuarios> findByCorreo(String correo);

    @Query("SELECT u FROM Usuarios u WHERE (u.correo = :usuario) AND u.contrasenia = :contrasenia")
    Optional<Usuarios> loginUsuario(@Param("usuario") String usuario, @Param("contrasenia") String contrasenia);

}
