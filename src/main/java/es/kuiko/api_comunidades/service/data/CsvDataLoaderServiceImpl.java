package es.kuiko.api_comunidades.service.data;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class CsvDataLoaderServiceImpl implements DataLoaderService {

    @Value("${csv.comunidades.path}")
    private String comunidadesCsvPath;

    @Value("${csv.provincias.path}")
    private String provinciasCsvPath;
	
	private final ResourceLoader resourceLoader;
    private final ComunidadAutonomaRepository comunidadAutonomaRepository;


    public CsvDataLoaderServiceImpl(ResourceLoader resourceLoader, ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.resourceLoader = resourceLoader;
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

    @Override
    public List<ComunidadAutonoma> loadComunidades(String comunidadesCsvPath) {
        List<ComunidadAutonoma> comunidadesAutonomas = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource(comunidadesCsvPath);
            if (!resource.exists()) {
                throw new FileNotFoundException("Archivo COMUNIDAD_AUTONOMA.csv no encontrado.");
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
                        provincia.setNombre_provincia(nombreProvincia);
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
