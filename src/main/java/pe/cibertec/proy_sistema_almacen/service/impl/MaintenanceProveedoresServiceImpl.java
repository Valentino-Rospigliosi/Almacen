package pe.cibertec.proy_sistema_almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.ProveedorListarDto;
import pe.cibertec.proy_sistema_almacen.entity.Proveedores;
import pe.cibertec.proy_sistema_almacen.repository.ProveedoresRepository;
import pe.cibertec.proy_sistema_almacen.service.MaintenanceProveedoresService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceProveedoresServiceImpl implements MaintenanceProveedoresService  {

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Override
    public List<ProveedorListarDto> listarProveedores() throws Exception {

        List<ProveedorListarDto> provedoresDtoListar = new ArrayList<>();
        Iterable<Proveedores> iterable = proveedoresRepository.findAll();
        iterable.forEach(prov -> {
            ProveedorListarDto proveedoresListar = new ProveedorListarDto(
                    prov.getIdProveedor(),
                    prov.getNombreProveedor(),
                    prov.getContacto(),
                    prov.getTelefono(),
                    prov.getDireccion()

            );
            provedoresDtoListar.add(proveedoresListar);

        });
        return provedoresDtoListar;
    }

    @Override
    public Optional<ProveedorListarDto> listarIdProveedor(int id) throws Exception {
        Optional<Proveedores> optional = proveedoresRepository.findById(id);
        return optional.map(prov -> new ProveedorListarDto(
                prov.getIdProveedor(),
                prov.getNombreProveedor(),
                prov.getContacto(),
                prov.getTelefono(),
                prov.getDireccion()
        ));
    }

    @Override
    public boolean agregarProveedor(ProveedorCrearDto proveedorCrearDto) throws Exception {
        Proveedores proveedor = new Proveedores();
        proveedor.setNombreProveedor(proveedorCrearDto.nombreProveedor());
        proveedor.setContacto(proveedorCrearDto.contacto());
        proveedor.setTelefono(proveedorCrearDto.telefono());
        proveedor.setDireccion(proveedorCrearDto.direccion());
        proveedoresRepository.save(proveedor);
        return true;
    }

    @Override
    public boolean actualizarProveedor(ProveedorCrearDto proveedorCrearDto) throws Exception {

        Optional<Proveedores> optional = proveedoresRepository.findById(proveedorCrearDto.idProveedor());

        return optional.map(prov -> {

            prov.setNombreProveedor(proveedorCrearDto.nombreProveedor());
            prov.setContacto(proveedorCrearDto.contacto());
            prov.setTelefono(proveedorCrearDto.telefono());
            prov.setDireccion(proveedorCrearDto.direccion());

            proveedoresRepository.save(prov);
            return true;
        }).orElse(false);

    }

    @Override
    public boolean borrarProveedorId(int id) throws Exception {

        Optional<Proveedores> optional = proveedoresRepository.findById(id);
        return optional.map(prov -> {
            proveedoresRepository.delete(prov);
            return true;
        }).orElse(false);

    }


}
