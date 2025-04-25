package pe.cibertec.proy_sistema_almacen.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedores proveedor;

    private String fechaRecepcion;
    private String estado;
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuarios usuario;

//    @OneToMany(mappedBy = "pedido")
//    private List<DetallePedido> detalles;

//    @OneToMany(mappedBy = "pedido")
//    private List<Recepcion> recepciones;

}
