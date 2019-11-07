package metodos;

//https://jena.apache.org/documentation/javadoc/

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static String ns = "<http://biciaccident.es/ontology#>";
    public static String owl = "<http://www.w3.org/2002/07/owl#>";
    public static String rdf = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    public static String rdfs = "<http://www.w3.org/2000/01/rdf-schema#>";
    public static String xml = "<http://www.w3.org/XML/1998/namespace>";
    public static String xsd = "<http://www.w3.org/2001/XMLSchema#>";

    private Scanner myObj = new Scanner(System.in);
    private Scanner menu = new Scanner(System.in);
    public ArrayList<ObjectConsulta> consultas = new ArrayList<ObjectConsulta>();

    public ObjectConsulta showInfo (String uri, OntModel model) {

        ObjectConsulta consulta = new ObjectConsulta();
        consulta.setUri(uri);

        //  String res = "<p>Procesando información para la URI " + uri + "</p>";

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
        //ResultSetFormatter.out(//System.out, results, query);

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




        // Important ‑ free up resources used running the query
        qe.close();


        consulta.setExpedient(expedient);
        consulta.setDate(date);
        consulta.setSteet(street);
        consulta.setDistrict(district);
        consulta.setWikidataDistrict(wikidata.substring(30));
        consulta.setInjuryStatus(injury);
        consulta.setWeatherCondition(weather);
        consulta.setTypeAccident(typeA);
        //System.out.println("Expedient: " + expedient);
        //System.out.println("Date: " + date);
        //System.out.println("Street: " + street);
        //System.out.println("District: " + district);
        //System.out.println("Wikidata District: " + wikidata.substring(30));
        //System.out.println("Injury Status: " + injury);
        //System.out.println("Weather Condition: " + weather);
        //System.out.println("Type Accident: " + typeA);
        return consulta;

    }

    public String streetAccidents (OntModel model,String tipo_busqueda) {

        // Create a new query 1. Accidentes por calle

        //System.out.println("Escriba el nombre de la calle a buscar");

        // String calle = myObj.nextLine();
        //String calle = "Call.Castello-Call.DonRamonDeLaCruz";
        String queryString =
                "PREFIX ns:   " + ns + "\n" +
                        "PREFIX rdfs:   " + rdfs + "\n" +
                        "SELECT DISTINCT ?accident WHERE {\n" +
                        "    ?accident a ns:Accident .\n" +
                        "    ?accident ns:occursOn ?uri .\n" +
                        "    ?uri a ns:street .\n" +
                        "    ?uri rdfs:label \"" + tipo_busqueda + "\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);
        //System.out.println(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(//System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");
        this.consultas = new ArrayList<ObjectConsulta>();
        String res = "";
        //System.out.println("ok distrito");
        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            consultas.add( showInfo(uri, model));
            //System.out.println("District" + i);

        }

        qe.close();
        return res;
    }

    public void neighborhoodAccidents (OntModel model, String distrito) {

        // Create a new query 2. Accidentes por distrito

        //System.out.println("Escriba el nombre del distrito a buscar");

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
        //System.out.println(queryString);
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(//System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");


        //System.out.println("DDDDDDDDDDDDD START" );

        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            consultas.add(showInfo(uri, model));
            //System.out.println("DDDDDDDDDDDDD" + i);
        }
        //System.out.println("DDDDDDDDDDDDD FIN" );
        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void weatherAccidents (OntModel model,String weather) {

        // Create a new query 3. Accidentes por clima

        //System.out.println("Escriba el tiempo meteorologico a buscar");

      //  String weather = myObj.nextLine();

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
        //ResultSetFormatter.out(//System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);
        JSONObject sparqlResults = jsonObject.getJSONObject("results");
        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");
        
        this.consultas = new ArrayList<ObjectConsulta>();
        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);
            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");
            String uri = sparqlAccident.getString("value");
            this.consultas.add( showInfo(uri, model));

        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void injuryAccidents (OntModel model, String lesividad) {

        // Create a new query 4. Accidentes por grado de lesividad

        //System.out.println("Escriba la lesividad a buscar");

       // String lesividad = myObj.nextLine();

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
        //ResultSetFormatter.out(//System.out, results, query);

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

           consultas.add( showInfo(uri, model));
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void typeAccidents(OntModel model, String typeA) {

        // Create a new query 5. Accidentes por tipo de accidente

        //System.out.println("Escriba el tipo de accidente a buscar");

        //String typeA = myObj.nextLine();

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
        //ResultSetFormatter.out(//System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");
        this.consultas = new ArrayList<ObjectConsulta>();
        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            consultas.add(showInfo(uri, model));
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public void monthAccidents (OntModel model,String mes) {

        // Create a new query 6. Accidentes por mes

        System.out.println("Escriba la fecha a buscar");

        String queryString =
                "PREFIX ns:   <http://biciaccident.es/ontology#>\n" +
                        "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
                        "SELECT ?accident \n" +
                        "WHERE {\n" +
                        "      ?accident a ns:Accident .\n" +
                        "      ?accident ns:date ?date .\n" +
                        "      FILTER (?date = \"" + mes + "\"^^xsd:dateTime ) .\n" +
                        "}";

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

            consultas.add(showInfo(uri, model));
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }
    public void idAccidents(OntModel model, String typeA) {

        // Create a new query 5. Accidentes por tipo de accidente

        System.out.println("Escriba el ID de accidente a buscar");

        //String typeA = myObj.nextLine();

     String queryString =
                "PREFIX ns:   <http://biciaccident.es/ontology#>\n" +
                        "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
                        "SELECT ?accident ?date \n" +
                        "WHERE {\n" +
                        "      ?accident a ns:Accident .\n" +
                        "      ?accident ns:expedient \"" + typeA + "\" .\n" +
                        "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        //ResultSetFormatter.out(//System.out, results, query);

        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());

        //Now convert to JSONObject
        JSONObject jsonObject = new JSONObject(json);

        JSONObject sparqlResults = jsonObject.getJSONObject("results");

        JSONArray sparqlBindings = sparqlResults.getJSONArray("bindings");
        this.consultas = new ArrayList<ObjectConsulta>();
        for (int i = 0; i<sparqlBindings.length(); i++) {

            JSONObject sparqlBindingsIndex = sparqlBindings.getJSONObject(i);

            JSONObject sparqlAccident = sparqlBindingsIndex.getJSONObject("accident");

            String uri = sparqlAccident.getString("value");

            consultas.add(showInfo(uri, model));
        }

        // Important ‑ free up resources used running the query
        qe.close();
    }

    public  String toString(String name_file,String cadena_a_buscar,String tipo_busqueda,boolean id) throws IllegalArgumentException {

        //Resource file (cuidado con el ttl que no lo deja abrir, por lo que veo tiene que ser rdf/xml por temas de la codificacion)
        String filename = name_file;

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: "+filename+" not found");

        // Read the RDF/XML file
        //model.read(in, null);
        model.read(filename,null,"TTL");
        String res ="";
        if(id) {
            System.out.println("pasa");
            idAccidents(model,cadena_a_buscar);
            System.out.println("acaba");
            return res;
        }

        if("street".compareTo(tipo_busqueda)==0){
            streetAccidents(model,cadena_a_buscar);
        }else if ("weather".compareTo(tipo_busqueda)==0) {
             weatherAccidents(model,cadena_a_buscar);
        }else if ("lesivity".compareTo(tipo_busqueda)==0) {
            this.injuryAccidents(model,cadena_a_buscar);
        }else if ("accident".compareTo(tipo_busqueda)==0) {
            this.typeAccidents(model,cadena_a_buscar);
        }else if ("date".compareTo(tipo_busqueda)==0) {
            this.monthAccidents(model,cadena_a_buscar);
        }else if ("district".compareTo(tipo_busqueda)==0) {
            this.neighborhoodAccidents(model,cadena_a_buscar);
        }

        return res;

    }



}
