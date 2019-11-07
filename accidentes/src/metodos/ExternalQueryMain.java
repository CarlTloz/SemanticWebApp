package metodos;// https://github.com/BorderCloud/SPARQL-JAVA
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalQueryMain {
    private File fichero;
    private FileWriter fichero_escritura;
    private String nombre_fichero;
    private String pais;
    private String coordenadas;
    private String postalCode;
    private String area;
    private String pic;
    private String poblacion;
    private Endpoint sp;
    public boolean esta = false;

    public HashMap<String, HashMap> retrieveData(String endpointUrl, String query) throws EndpointException {
        esta = false;
        sp = new Endpoint(endpointUrl, false);
        HashMap<String, HashMap> rs = sp.query(query);
        System.out.println("DATA:  "+ rs.toString());
        return rs;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    private void open_file(String filename){
        try{
            fichero = new File (filename);
            fichero_escritura = new FileWriter(fichero);
        }catch(Exception error){
            //System.out.println("Error al abrir el fichero");
        }
    }

    private void close_file() throws IOException {
        if(fichero_escritura != null)
            fichero_escritura.close();
    }

    public void printResult(HashMap<String, HashMap> rs , int size) throws IOException {


      //  fichero_escritura.write("<HTML>");
        //System.out.println("pasa");
        for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
       //     fichero_escritura.write(String.format("%-"+size+"."+size+"s", variable ) + " | ");
            //System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }

    //    fichero_escritura.write("</HTML>");

        //System.out.print("\n");
        for (HashMap value : (ArrayList<HashMap>) rs.get("result").get("rows")) {
            int c = 0;
            for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
              switch(c) {
                    case 0:
                       this.pais =  String.format("%-" + size + "." + size  + "s", value.get(variable));;break;
                    case 1:
                 //      this.coordenadas = String.format("%-" + size + "." + size  + "s", value.get(variable)); ;break;
                    case 2:
                 //       this.postalCode = String.format("%-" + size + "." + size  + "s", value.get(variable));;break;
                    case 3:
                        this.poblacion = String.format("%-" + size + "." + size  + "s", value.get(variable));break;
                    case 4:
                   //     this.area = String.format("%-" + size + "." + size  + "s", value.get(variable));break;
                    case 5:
                        this.pic = String.format("%-" + size + "." + size  + "s", value.get(variable));break;
                    default: ;break;
                }
                c++;

            }

        }

        esta =true;

    }


    public void externalSparklQuery(String district){
        String endpointUrl = "https://query.wikidata.org/sparql";

        String querySelect = "\n" +
                "SELECT   ?pais ?Coordenadas ?postalCode ?poblacion ?area  ?pic         \n" +
                "{\n" +
                "# Salamanca  \"has population\"  poblacion\n" +
                "  wd:"+district+" wdt:P17 ?pais.\n" +
      //          "  wd:"+district+" wdt:P625 ?Coordenadas.\n" +
       //         "  wd:"+district+" wdt:P281 ?postalCode.\n" +
                "  wd:"+district+" wdt:P1082 ?poblacion.\n" +
       //         "  wd:"+district+" wdt:P2046 ?area.\n" +
                "   wd:"+district+" wdt:P18 ?pic\n" +
                " \n" +
                "}";

        try {

            HashMap data = retrieveData(endpointUrl, querySelect);
         //   nombre_fichero = "index.html";
         //   open_file(nombre_fichero);
            try {
                printResult(data, 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* try {
                close_file();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } catch (EndpointException eex) {
            eex.printStackTrace();
        }

    }



}
