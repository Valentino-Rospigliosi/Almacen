package pe.cibertec.proy_sistema_almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.UsuariosListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Usuarios;
import pe.cibertec.proy_sistema_almacen.repository.UsuariosRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceUsuariosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceUsuariosServiceImpl implements MaintenanceUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;


    @Override
    public List<UsuariosListarDto> listarUsuarios() throws Exception {

        List<UsuariosListarDto> usuariosDtoListar = new ArrayList<>();
        Iterable<Usuarios> iterable = usuariosRepository.findAll();
        iterable.forEach(usu -> {
            UsuariosListarDto usuariosListar = new UsuariosListarDto(
                    usu.getIdUsuario(),
                    usu.getNombreUsuario(),
                    usu.getCorreo(),
                    usu.getContrasenia(),
                    usu.getRol(),
                    usu.getEstado()

            );
            usuariosDtoListar.add(usuariosListar);

        });
        return usuariosDtoListar;
    }

    @Override
    public Optional<UsuariosListarDto> listarIdUsuarios(int id) throws Exception {

        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(id);
        return optionalUsuario.map(usu -> new UsuariosListarDto(
                usu.getIdUsuario(),
                usu.getNombreUsuario(),
                usu.getCorreo(),
                usu.getContrasenia(),
                usu.getRol(),
                usu.getEstado()
        ));

    }

    @Override
    public boolean agregarUsuarios(UsuariosCrearDto usuariosCrearDto) throws Exception {

        Optional<Usuarios> optional = usuariosRepository.findByCorreo(usuariosCrearDto.correo());
        if (optional.isPresent())
            return false; //validacion de correo
        else {

            Usuarios usu = new Usuarios();

            usu.setNombreUsuario(usuariosCrearDto.nombreUsuario());
            usu.setCorreo(usuariosCrearDto.correo());
            usu.setContrasenia(usuariosCrearDto.contrasenia());
            usu.setRol(usuariosCrearDto.rol());
            usu.setEstado(usuariosCrearDto.estado());

            usuariosRepository.save(usu);
            return true;
        }
    }

    @Override
    public boolean actualizarUsuario(UsuariosCrearDto usuariosCrearDto) throws Exception {

        Optional<Usuarios> optional = usuariosRepository.findByCorreo(usuariosCrearDto.correo());

        return optional.map(usu -> {

            usu.setNombreUsuario(usuariosCrearDto.nombreUsuario());
            usu.setCorreo(usuariosCrearDto.correo());
            usu.setContrasenia(usuariosCrearDto.contrasenia());
            usu.setRol(usuariosCrearDto.rol());
            usu.setEstado(usuariosCrearDto.estado());

            usuariosRepository.save(usu);
            return true;
        }).orElse(false);

    }

    @Override
    public boolean borrarUsuarioId(int id) throws Exception {

        Optional<Usuarios> optional = usuariosRepository.findById(id);
        return optional.map(usu -> {
            usuariosRepository.delete(usu);
            return true;
        }).orElse(false);

    }

}
