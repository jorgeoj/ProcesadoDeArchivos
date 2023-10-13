package org.example;


import static org.example.Procesador.processArchive;

/**
 * Clase de aplicación principal que llama al método de procesamiento de archivos.
 */
public class App {

    /**
     * Punto de entrada principal de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        // Llamada al método de procesamiento de archivos con los nombres de los archivos de entrada.
        processArchive("data.csv", "template.txt");
    }
}
