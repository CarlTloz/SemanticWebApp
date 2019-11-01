package main.java;

//https://jena.apache.org/documentation/javadoc/

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.jena.base.Sys;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;
import org.json.JSONArray;
import org.json.JSONObject;

public class App {

    public static String ns = "<http://biciaccident.es/ontology#>";
    public static String owl = "<http://www.w3.org/2002/07/owl#>";
    public static String rdf = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    public static String rdfs = "<http://www.w3.org/2000/01/rdf-schema#>";
    public static String xml = "<http://www.w3.org/XML/1998/namespace>";
    public static String xsd = "<http://www.w3.org/2001/XMLSchema#>";

    private Scanner myObj = new Scanner(System.in);

    public void showInfo (String uri, OntModel model) {

        System.out.println("\nProcesando información para la URI " + uri + " ...\n");

        String queryInfo =
                "PREFIX ns:   " + ns + "\n" +
                        "PREFIX rdfs:   " + rdfs + "\n" +
                        "PREFIX owl:   " + owl + "\n" +
                        "SELECT DISTINCT ?expedient ?date ?street ?district ?wikidatadistrict ?injury ?weather ?typeA WHERE {\n" +
                        "<" + uri + "> a ns:Accident .\n" +
                        "<" + uri + "> ns:date ?date .\n" +
                        "<" + uri + "> ns:occursOn ?streetUri .\n" +
                        "<" + uri + "> ns:injuryStatus ?injury .\n" +
                        "<" + uri + "> ns:weatherCondition ?weather .\n" +
                        "<" + uri + "> ns:typeAccident ?typeA .\n" +
                        "<" + uri + "> rdfs:label ?expedient .\n" +
                        "    ?streetUri ns:locatedIn ?districtUri .\n" +
                        "    ?streetUri rdfs:label ?street .\n" +
                        "    ?districtUri a ns:neighborhood .\n" +
                        "    ?districtUri rdfs:label ?district .\n" +
                        "    ?districtUri owl:sameAs ?wikidatadistrict .\n" +
                        "}";

        Query query = QueryFactory.create(queryInfo);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(0);

        JSONObject sparqlDate = sparqlBindingsIndex.getJSONObject("date");

        String date = sparqlDate.getString("value");

        JSONObject sparqlWikidata = sparqlBindingsIndex.getJSONObject("wikidatadistrict");

        String wikidata = sparqlWikidata.getString("value");

        JSONObject sparqlStreet = sparqlBindingsIndex.getJSONObject("street");

        String street = sparqlStreet.getString("value");

        JSONObject sparqlDistrict = sparqlBindingsIndex.getJSONObject("district");

        String district = sparqlDistrict.getString("value");

        JSONObject sparqlWeather = sparqlBindingsIndex.getJSONObject("weather");

        String weather = sparqlWeather.getString("value");

        JSONObject sparqlInjury = sparqlBindingsIndex.getJSONObject("injury");

        String injury = sparqlInjury.getString("value");

        JSONObject sparqlExpedient = sparqlBindingsIndex.getJSONObject("expedient");

        String expedient = sparqlExpedient.getString("value");

        JSONObject sparqlTypeA = sparqlBindingsIndex.getJSONObject("typeA");

        String typeA = sparqlTypeA.getString("value");

        /*System.out.println(jsonObject);
        System.out.println(sparqlResults);
        System.out.println(sparqlBindings);
        System.out.println(sparqlBindingsIndex);*/

        System.out.println("Expedient: " + expedient);
        System.out.println("Date: " + date);
        System.out.println("Street: " + street);
        System.out.println("District: " + district);
        System.out.println("Wikidata District: " + wikidata.substring(30));
        System.out.println("Injury Status: " + injury);
        System.out.println("Weather Condition: " + weather);
        System.out.println("Type Accident: " + typeA);

        // Important ‑ free up resources used running the query
        qe.close();

        //https://stackoverflow.com/questions/6856120/building-html-in-java-code-only
        /*StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>");
        htmlBuilder.append("<head><title>Hello World</title></head>");
        htmlBuilder.append("<body><p>Look at my body!</p></body>");
        htmlBuilder.append("</html>");
        String html = htmlBuilder.toString();*/
    }

    public void streetAccidents (OntModel model) {

        // Create a new query 1. Accidentes por calle

        System.out.println("Escriba el nombre de la calle a buscar");

        String calle = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "PREFIX rdfs:   " + rdfs + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:occursOn ?uri .\n" +
                        "    ?uri a ns:street .\n" +
                        "    ?uri rdfs:label \"" + calle + "\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void neighborhoodAccidents (OntModel model) {

        // Create a new query 2. Accidentes por distrito

        System.out.println("Escriba el nombre del distrito a buscar");

        String distrito = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "PREFIX rdfs:   " + rdfs + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:occursOn ?street .\n" +
                        "    ?street ns:locatedIn ?uri .\n" +
                        "    ?uri a ns:neighborhood .\n" +
                        "    ?uri rdfs:label \"" + distrito + "\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void weatherAccidents (OntModel model) {

        // Create a new query 3. Accidentes por clima

        System.out.println("Escriba el tiempo meteorologico a buscar");

        String weather = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:weatherCondition \"" + weather + "\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void injuryAccidents (OntModel model) {

        // Create a new query 4. Accidentes por grado de lesividad

        System.out.println("Escriba la lesividad a buscar");

        String lesividad = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:injuryStatus \"" + lesividad +"\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void typeAccidents (OntModel model) {

        // Create a new query 5. Accidentes por tipo de accidente

        System.out.println("Escriba el tipo de accidente a buscar");

        String typeA = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:typeAccident \"" + typeA +"\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void monthAccidents (OntModel model) {

        // Create a new query 6. Accidentes por mes

        System.out.println("Escriba el mes a buscar");

        String mes = myObj.nextLine();

        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "PREFIX xsd:   " + xsd + "\n" +
                        "SELECT ?accident\n" +
                        "WHERE {\n" +
                        "      ?accident a ns:Accident .\n" +
                        "      ?accident ns:date ?date .\n" +
                        "      FILTER (?date >= \"2019-" + mes + "-01T00:00Z\"^^xsd:dateTime && " +
                        "?date < \"2019-" + mes + "-30T23:59ZZ\"^^xsd:dateTime)\n" +
                        "      }";

        System.out.println(queryString);

        /*
        "SELECT DISTINCT ?x WHERE {\n" +
                        "    ?x a <http://biciaccident.es/ontology#Accident> .\n" +
                        "    ?x <http://biciaccident.es/ontology#date> \"" + mes + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime> .\n" +
                        "}";
        */

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            showInfo(uri, model);

            /*System.out.println(jsonObject);
            System.out.println(sparqlResults);
            System.out.println(sparqlBindings);
            System.out.println(sparqlBindingsIndex);
            System.out.println(sparqlAccident);
            System.out.println(sparqlValue);*/
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public static void main(String args[]) {

        //Resource file (cuidado con el ttl que no lo deja abrir, por lo que veo tiene que ser rdf/xml por temas de la codificacion)
        String filename = "resources/biciaccidents-with-links.ttl";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: "+filename+" not found");

        // Read the RDF/XML file
        //model.read(in, null);
        model.read(filename,null,"TTL");

        /*
        https://developer.ibm.com/articles/j-sparql/
        1. Accidentes por calle
        2. Accidentes por distrito
        3. Accidentes por edad
        4. Accidentes por grado de lesividad
        5. Accidentes por tipo de accidente
        6. Accidentes por mes
         */

        App test = new App();
        test.neighborhoodAccidents(model);
    }
}
