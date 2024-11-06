package es.kuiko.api_comunidades.service.data;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio de carga de datos desde archivos CSV para las entidades {@link ComunidadAutonoma} y {@link Provincia}.
 * 
 * <p>La clase {@code CsvDataLoaderServiceImpl} implementa {@link DataLoaderService} y se encarga de leer datos 
 * desde archivos CSV, mapeándolos a entidades de la aplicación. Proporciona métodos para cargar listas de 
 * Comunidades Autónomas y Provincias con validación de datos y manejo de errores.
 * </p>
 * 
 * <p>Los archivos CSV se especifican mediante las propiedades de configuración {@code csv.comunidades.path}
 * y {@code csv.provincias.path}, y se leen con {@link ResourceLoader} para soportar diferentes rutas de recursos.
 * </p>
 */
@Service
public class CsvDataLoaderServiceImpl implements DataLoaderService {

    @Value("${csv.comunidades.path}")
    private String comunidadesCsvPath;

    @Value("${csv.provincias.path}")
    private String provinciasCsvPath;

    private final ResourceLoader resourceLoader;
    private final ComunidadAutonomaRepository comunidadAutonomaRepository;

    /**
     * Constructor para inyectar dependencias de {@link ResourceLoader} y {@link ComunidadAutonomaRepository}.
     *
     * @param resourceLoader Servicio para cargar recursos desde el sistema de archivos.
     * @param comunidadAutonomaRepository Repositorio para verificar la existencia de Comunidades Autónomas.
     */
    public CsvDataLoaderServiceImpl(ResourceLoader resourceLoader, ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.resourceLoader = resourceLoader;
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

    /**
     * Carga los datos de Comunidades Autónomas desde un archivo CSV y los convierte a entidades.
     *
     * <p>Este método lee el archivo CSV especificado en {@code comunidadesCsvPath}, valida que cada registro 
     * tenga un código y nombre de comunidad, y guarda las entidades en una lista. Cualquier error de validación
     * en una fila se registra y se continúa con el siguiente registro.
     * </p>
     *
     * @param comunidadesCsvPath Ruta del archivo CSV de Comunidades Autónomas.
     * @return Lista de entidades {@link ComunidadAutonoma} cargadas desde el archivo.
     */
    @Override
    public List<ComunidadAutonoma> loadComunidades(String comunidadesCsvPath) {
        List<ComunidadAutonoma> comunidadesAutonomas = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource(comunidadesCsvPath);
            if (!resource.exists()) {
                throw new FileNotFoundException("Archivo COMUNIDAD_AUTONOMA no encontrado.");
            }
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
            try (InputStream inputStream = resource.getInputStream();
                 CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                         .withCSVParser(parser).build()) {

                List<String[]> records = csvReader.readAll();
                records.remove(0); // Ignorar cabecera
                int fila = 1;

                for (String[] data : records) {
                    try {
                        String codigoCa = data[0].trim();
                        String nombreCa = data[1].trim();
                        if (codigoCa.isEmpty() || nombreCa.isEmpty()) {
                            throw new IllegalArgumentException("Código o nombre de comunidad vacío.");
                        }
                        ComunidadAutonoma comunidad = new ComunidadAutonoma();
                        comunidad.setCodigoCa(codigoCa);
                        comunidad.setNombreCa(nombreCa);
                        comunidadesAutonomas.add(comunidad);
                    } catch (Exception e) {
                        String error = "Error en fila " + fila + ": " + e.getMessage();
                        errores.add(error);
                        System.err.println(error);
                    }
                    fila++;
                }
            }
        } catch (Exception e) {
            errores.add("Error general al cargar COMUNIDAD_AUTONOMA.csv: " + e.getMessage());
            e.printStackTrace();
        }
        if (!errores.isEmpty()) {
            System.out.println("Errores en la carga de comunidades:");
            errores.forEach(System.out::println);
        }
        return comunidadesAutonomas;
    }

    /**
     * Carga los datos de Provincias desde un archivo CSV y los convierte a entidades.
     *
     * <p>Este método lee el archivo CSV especificado en {@code provinciasCsvPath}, valida que cada registro 
     * tenga un código de provincia válido, y asocia cada provincia con una Comunidad Autónoma. Los errores 
     * en los registros se manejan y se muestran en la consola.
     * </p>
     *
     * @param provinciasCsvPath Ruta del archivo CSV de Provincias.
     * @return Lista de entidades {@link Provincia} cargadas desde el archivo.
     */
    @Override
    public List<Provincia> loadProvincias(String provinciasCsvPath) {
        List<Provincia> provincias = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource(provinciasCsvPath);
            if (!resource.exists()) {
                throw new FileNotFoundException("Archivo PROVINCIA.csv no encontrado.");
            }
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
            try (InputStream inputStream = resource.getInputStream();
                 CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                         .withCSVParser(parser).build()) {

                List<String[]> records = csvReader.readAll();
                records.remove(0); // Ignorar cabecera
                int fila = 1;

                for (String[] data : records) {
                    try {
                        String codigoCa = data[0].trim();
                        int codigoProvincia = Integer.parseInt(data[1].trim());
                        String nombreProvincia = data[2].trim();

                        // Validación y extracción de la comunidad autónoma
                        ComunidadAutonoma comunidad = comunidadAutonomaRepository.findByCodigoCa(codigoCa)
                                .orElseThrow(() -> new NoSuchElementException("Comunidad Autónoma no encontrada para el código: " + codigoCa));

                        Provincia provincia = new Provincia();
                        provincia.setCodigoProvincia(codigoProvincia);
                        provincia.setNombreProvincia(nombreProvincia);
                        provincia.setComunidadAutonoma(comunidad);

                        provincias.add(provincia);
                    } catch (NumberFormatException e) {
                        String error = "Error en fila " + fila + ": el código de la provincia no es un número válido.";
                        errores.add(error);
                        System.err.println(error);
                    } catch (NoSuchElementException e) {
                        String error = "Error en fila " + fila + ": " + e.getMessage();
                        errores.add(error);
                        System.err.println(error);
                    } catch (Exception e) {
                        String error = "Error inesperado en fila " + fila + ": " + e.getMessage();
                        errores.add(error);
                        System.err.println(error);
                    }
                    fila++;
                }
            }
        } catch (Exception e) {
            errores.add("Error general al cargar PROVINCIA.csv: " + e.getMessage());
            e.printStackTrace();
        }
        if (!errores.isEmpty()) {
            System.out.println("Errores en la carga de provincias:");
            errores.forEach(System.out::println);
        }
        return provincias;
    }
}
