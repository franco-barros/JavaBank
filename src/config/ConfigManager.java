package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private final Properties configProperties;

    // Constructor privado para Singleton
    private ConfigManager() {
        configProperties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("El archivo 'config.properties' no se encontró en el classpath.");
            }
            configProperties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo 'config.properties': " + e.getMessage(), e);
        }
    }

    // Inicialización del Singleton mediante una clase estática interna
    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    // Método para acceder a la instancia
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    // Obtener propiedad del archivo config.properties
    public String getProperty(String key) {
        String value = configProperties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("La propiedad '" + key + "' no está definida en 'config.properties'.");
        }
        return value;
    }
}
