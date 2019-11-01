package main.java;// https://github.com/BorderCloud/SPARQL-JAVA
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalQueryMain {
    private static File fichero;
    private static FileWriter fichero_escritura;
    private static String nombre_fichero;

    public static HashMap<String, HashMap> retrieveData(String endpointUrl, String query) throws EndpointException {
        Endpoint sp = new Endpoint(endpointUrl, false);
        HashMap<String, HashMap> rs = sp.query(query);
        return rs;
    }

    private static void open_file(String filename){
        try{
            fichero = new File (filename);
            fichero_escritura = new FileWriter(fichero);
        }catch(Exception error){
            System.out.println("Error al abrir el fichero");
        }
    }

    private static void close_file() throws IOException {
        if(fichero_escritura != null)
            fichero_escritura.close();
    }

    public static void printResult(HashMap<String, HashMap> rs , int size) throws IOException {


        fichero_escritura.write("<HTML>");

        for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {

            fichero_escritura.write(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }

        fichero_escritura.write("</HTML>");
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
                "SELECT   ?pais ?Coordenadas ?postalCode ?poblacion ?area  ?pic         \n" +
                "{\n" +
                "# Salamanca  \"has population\"  poblacion\n" +
                "  wd:Q1773521 wdt:P17 ?pais.\n" +
                "  wd:Q1773521 wdt:P625 ?Coordenadas.\n" +
                "  wd:Q1773521 wdt:P281 ?postalCode.\n" +
                "  wd:Q1773521 wdt:P1082 ?poblacion.\n" +
                "  wd:Q1773521 wdt:P2046 ?area.\n" +
                "   wd:Q1773521 wdt:P18 ?pic\n" +
                " \n" +
                "}";

        try {

            HashMap data = retrieveData(endpointUrl, querySelect);
            nombre_fichero = "index.html";
            open_file(nombre_fichero);
            try {
                printResult(data, 30);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                close_file();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (EndpointException eex) {
            eex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        externalSparklQuery();
    }


}
