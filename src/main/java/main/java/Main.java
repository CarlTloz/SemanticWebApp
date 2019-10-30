package main.java;// https://github.com/BorderCloud/SPARQL-JAVA
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<String, HashMap> retrieveData(String endpointUrl, String query) throws EndpointException {
        Endpoint sp = new Endpoint(endpointUrl, false);
        HashMap<String, HashMap> rs = sp.query(query);
        return rs;
    }

    public static void printResult(HashMap<String, HashMap> rs , int size) {
        for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
            System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }
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

    public static void main(String[] args) {
        externalSparklQuery();
    }


}
