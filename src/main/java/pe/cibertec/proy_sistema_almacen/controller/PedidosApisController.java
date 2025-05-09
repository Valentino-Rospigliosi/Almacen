package pe.cibertec.proy_sistema_almacen.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.proy_sistema_almacen.dto.PedidoCrearDto;
import pe.cibertec.proy_sistema_almacen.dto.PedidoListarDto;
import pe.cibertec.proy_sistema_almacen.service.MaintenancePedidosService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/pedidos")
public class PedidosApisController {
    @Autowired
    private MaintenancePedidosService maintenancePedidosService;

    // GET: Listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoListarDto>> listarPedidos() throws Exception {
        List<PedidoListarDto> pedidos = maintenancePedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // GET: Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoListarDto> listarPedidoPorId(@PathVariable int id) throws Exception {
        Optional<PedidoListarDto> pedido = maintenancePedidosService.listarIdPedido(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<Void> guardarPedido(@RequestBody PedidoCrearDto pedidoCrearDto) throws Exception {
        maintenancePedidosService.agregarPedido(pedidoCrearDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT: Actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPedido(@PathVariable Integer id, @RequestBody PedidoCrearDto pedidoCrearDto) throws Exception {
        // Setear el id al DTO
        PedidoCrearDto pedidoActualizado = new PedidoCrearDto(
                id,
                pedidoCrearDto.idProveedor(),
                pedidoCrearDto.fechaRecepcion(),
                pedidoCrearDto.estado(),
                pedidoCrearDto.observacion(),
                pedidoCrearDto.idUsuario()
        );

        boolean actualizado = maintenancePedidosService.actualizarPedido(pedidoActualizado);
        if (actualizado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPedido(@PathVariable int id) throws Exception {
        maintenancePedidosService.borrarPedidoId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportarPedidosExcel() throws Exception {
        List<PedidoListarDto> pedidos = maintenancePedidosService.listarPedidos();

        // Crear libro de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pedidos");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Proveedor");
        header.createCell(1).setCellValue("Fecha Recepción");
        header.createCell(2).setCellValue("Estado");
        header.createCell(3).setCellValue("Observación");
        header.createCell(4).setCellValue("Usuario");

        // Llenar filas con datos
        int rowNum = 1;
        for (PedidoListarDto pedido : pedidos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(pedido.nombreProveedor());
            row.createCell(1).setCellValue(pedido.fechaRecepcion());
            row.createCell(2).setCellValue(pedido.estado());
            row.createCell(3).setCellValue(pedido.observacion());
            row.createCell(4).setCellValue(pedido.nombreUsuario());
        }

        // Escribir en flujo de bytes
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        byte[] bytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=pedidos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    // GET: Listar pedidos con paginación
    @GetMapping("/paginacion")
    public ResponseEntity<Page<PedidoListarDto>> listarPedidosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {

        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoListarDto> pedidos = maintenancePedidosService.listarPedidosPaginados(pageable);

        return ResponseEntity.ok(pedidos);
    }

}
