package main.java;// https://github.com/BorderCloud/SPARQL-JAVA
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalQueryMain {

    //Elementos para la gestion de archivos
    private FileReader aProcesar;
    private	FileWriter salidaTokens;
    private PrintWriter printLineTokens;


    public static HashMap<String, HashMap> retrieveData(String endpointUrl, String query) throws EndpointException {
        Endpoint sp = new Endpoint(endpointUrl, false);
        HashMap<String, HashMap> rs = sp.query(query);
        return rs;
    }



    public static void printResult(HashMap<String, HashMap> rs , int size) {

        //Antes de la  consulta creamos el header para poder poner archivo
        generateHtmlHeader ();

        for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {

            System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }
        String html = "<HTML>"+rs.+"</HTML>"
        System.out.print("\n");
        for (HashMap value : (ArrayList<HashMap>) rs.get("result").get("rows")) {
            for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
                System.out.print(String.format("%-"+size+"."+size+"s", value.get(variable)) + " | ");
            }
            System.out.print("\n");
        }
    }


    public static void externalSparklQuery(){
        String endpointUrl = "https://query.wikidata.org/sparql";

        String querySelect = "\n" +
                "SELECT  ?postalCode ?pais ?Coordenadas ?administraciones ?poblacion\n" +
                "{\n" +
                "# Salamanca  \"has population\"  poblacion\n" +
                "\n" +
                "  wd:Q1773521  wdt:P281 ?postalCode.\n" +
                "  wd:Q1773521 wdt:P17 ?pais.\n" +
                "  wd:Q1773521 wdt:P625 ?Coordenadas.\n" +
                "  wd:Q1773521 wdt:P150 ?administraciones.\n" +
                "   wd:Q1773521 wdt:P1082 ?poblacion\n" +
                "               \n" +
                "}\n" +
                "";

        try {
            HashMap data = retrieveData(endpointUrl, querySelect);
            printResult(data, 30);
        } catch (EndpointException eex) {
            eex.printStackTrace();
        }

    }

    //+++++++++++++++++++++++++++++++++++ METODOS PARA GESTIONAR EL HTML +++++++++++++++++++++++++++++++

    //+++++++++++++++++++++++++++++++++++  CREACION DEL ARCHIVO +++++++++++++++++++++++++++++++

    public static FileWriter createHtmlFile() throws IOException {
        String rutaSalida = "/OutPut.txt";
        FileWriter queryOutputFile = new FileWriter(rutaSalida);
        return queryOutputFile ;
    }

    public static PrintWriter createHtmlStream(FileWriter a ){
        PrintWriter outFilePrinter = new   PrintWriter(a);
        return outFilePrinter;
    }

    public void registerToHtml (String token) {
        this.printLineTokens.println(token);
    }


    //+++++++++++++++++++++++++++++++++++ APERTURA DEL ARCHIVO +++++++++++++++++++++++++++++++

    public  FileReader openHtmlFile (String ruta) {

        FileReader fichero = null;
        try {
            //Abrir el fichero indicado en la variable nombreFichero
            fichero = new FileReader(ruta);
            //Leer el primer carácter
            //Se debe almacenar en una variable de tipo int

            return fichero;
            //leeFichero(fichero);


        }
        catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                //if(fichero != null)
                //	fichero.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }

        return fichero;
        }


        //+++++++++++++++++++++++++++++++++++ GENERACION DE PARTES DEL ARCHIVO+++++++++++++++++++++++++++++++

    public static void generateHtmlHeader(){
        String cabezera_html = "<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>El primer documento HTML</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>";
    }



    public static void generateHtmlFooter(){
        String cabezera_html = "<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>El primer documento HTML</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>";
    }


    //+++++++++++++++++++++++++++++++++++ ESCRITURA SOBRE EL ARCHIVO ++++++++++++++++++++++++++++++

    public static void main(String[] args) {
        externalSparklQuery();
    }


}
