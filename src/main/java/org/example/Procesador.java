package org.example;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase para procesar un archivo CSV y generar archivos de salida basados en una plantilla.
 */
public class Procesador {

    /**
     * Procesa un archivo CSV y genera archivos de salida con plantillas personalizadas.
     *
     * @param csvArchive       Ruta al archivo CSV de entrada.
     * @param archiveTemplate  Ruta al archivo de plantilla.
     */

    //Variable que indica porque se separan cada dato del archivo csv
    private static String splitSign = ",";

    //Variables para los cambios de variable
    private static String cityReplacer = "%%2%%";
    private static String emailReplacer = "%%3%%";
    private static String companyReplacer = "%%4%%";
    private static String employeeReplacer = "%%5%%";

    //Variable para nombre de la carpeta
    private static String packageName = "salida";

    //Variables para nombrar los archivos
    private static String fileName = "template-";
    private static String fileExtension = ".txt";


    public static void processArchive(String csvArchive, String archiveTemplate){

        File archive = new File(csvArchive);

        try(BufferedReader brArchive = new BufferedReader(new FileReader(archive))){
            String archiveReader;
            while ((archiveReader = brArchive.readLine())!=null){
                String[] archiveData = archiveReader.split(splitSign);
                //Leer los archivos
                if(archiveData.length>=5) {
                    String id = archiveData[0];
                    String company = archiveData[1];
                    String city = archiveData[2];
                    String email = archiveData[3];
                    String employee = archiveData[4];

                    //Creamos la plantilla de los archivos que guardamos en salida
                    ArrayList<String> template = templateLoader(archiveTemplate, city, email, company, employee);

                    //Hacer los archivos de salida
                    createFiles(id, template);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Carga las plantillas desde un archivo y reemplaza las variables con valores proporcionados.
     *
     * @param archiveTemplate  Ruta al archivo de plantilla.
     * @param city            Valor de la variable %%2%%.
     * @param email           Valor de la variable %%3%%.
     * @param company         Valor de la variable %%4%%.
     * @param employee        Valor de la variable %%5%%.
     * @return ArrayList de plantillas personalizadas.
     */

    private static ArrayList<String> templateLoader(String archiveTemplate, String city, String email, String company, String employee) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader brTemplate = new BufferedReader(new FileReader(archiveTemplate))) {
            String templateReader;
            while ((templateReader = brTemplate.readLine()) != null) {
                templateReader = templateReader.replace(cityReplacer, city);
                templateReader = templateReader.replace(emailReplacer, email);
                templateReader = templateReader.replace(companyReplacer, company);
                templateReader = templateReader.replace(employeeReplacer, employee);

                data.add(templateReader + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo " + e);
        }
        return data;
    }

    /**
     * Crea archivos de salida basados en las plantillas personalizadas.
     *
     * @param id        Identificador único.
     * @param data ArrayList de plantillas personalizadas.
     */

    private static void createFiles(String id, ArrayList<String> data) {
        File exit = new File(packageName);
        exit.mkdir();

        try(BufferedWriter mail = new BufferedWriter(new FileWriter(packageName + "/"+ fileName + id + fileExtension))){
            for (String newTemplate : data){
                mail.write(newTemplate);
                mail.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo " + e);
        }
    }
}