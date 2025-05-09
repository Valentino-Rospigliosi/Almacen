package pe.cibertec.proy_sistema_almacen.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombreProducto;
    private String descripcion;
    private Integer stockActual;
    private Integer stockMinimo;
    private String estado;
    private Integer codigoPedido;

//    @OneToMany(mappedBy = "producto")
//    private List<DetallePedido> detalles;

//    @OneToMany(mappedBy = "producto")
//    private List<AlertaStock> alertas;

}
