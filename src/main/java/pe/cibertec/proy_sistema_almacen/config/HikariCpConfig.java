package pe.cibertec.proy_sistema_almacen.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariCpConfig {

    @Value("${DB_RECEPYALMACEN_URL}")
    private String dbRecepyAlmacenUrl;
    @Value("${DB_RECEPYALMACEN_USER}")
    private String dbRecepyAlmacenUser;
    @Value("${DB_RECEPYALMACEN_PASS}")
    private String dbRecepyAlmacenPass;
    @Value("${DB_RECEPYALMACEN_DRIVER}")
    private String dbRecepyAlmacenDriver;

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();

        /**
         * Configurar propiedad de conexion a BD
         */
        config.setJdbcUrl(dbRecepyAlmacenUrl);
        config.setUsername(dbRecepyAlmacenUser);
        config.setPassword(dbRecepyAlmacenPass);
        config.setDriverClassName(dbRecepyAlmacenDriver);

        /**
         * Configurar propiedades del pool de HikariCP
         * - MaximumPoolSize: Máximo # de conexiones del pool.
         * - MinimumIdle: Mínimo # de conexiones inactivas del pool.
         * - IdleTimeout: Tiempo máximo de espera para
         *      eliminar una conexión inactiva.
         * - ConnectionTimeout: Tiempo máximo de espera
         *      para conectarse a la BD.
         */
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(30000);

        System.out.println("###### HikariCP initialized ######");
        return new HikariDataSource(config);

    }

}
