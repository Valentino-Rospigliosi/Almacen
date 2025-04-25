package pe.cibertec.proy_sistema_almacen.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    private String nombreProveedor;
    private String contacto;
    private String telefono;
    private String direccion;

    @OneToMany(mappedBy = "proveedor")
    private List<Pedido> pedidos;

}
