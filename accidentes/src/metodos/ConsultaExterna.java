package metodos;// https://github.com/BorderCloud/SPARQL-JAVA
public class ConsultaExterna {
    public static void main(String [] args){
        ExternalQueryMain externa = new ExternalQueryMain();
        externa.externalSparklQuery("Q1001991");

        System.out.println("postalCoode " + externa.getPostalCode());
        System.out.println("poblacion: " + externa.getPoblacion());
        System.out.println("pic: " + externa.getPic());
        System.out.println("coordenadas" + externa.getCoordenadas());
        System.out.println("pais: " + externa.getPais());
        System.out.println("-------------------------------------");
        externa.externalSparklQuery("Q1773521");
        System.out.println("postalCoode " + externa.getPostalCode());
        System.out.println("poblacion: " + externa.getPoblacion());
        System.out.println("pic: " + externa.getPic());
        System.out.println("coordenadas" + externa.getCoordenadas());
        System.out.println("pais: " + externa.getPais());
        System.out.println("area: " + externa.getArea());
        System.out.println("-------------------------------------");
        externa.externalSparklQuery("Q1928529");
        System.out.println("postalCoode " + externa.getPostalCode());
        System.out.println("poblacion: " + externa.getPoblacion());
        System.out.println("pic: " + externa.getPic());
        System.out.println("coordenadas" + externa.getCoordenadas());
        System.out.println("pais: " + externa.getPais());
        System.out.println("area: " + externa.getArea());
        System.out.println("-------------------------------------");
        externa.externalSparklQuery("Q2000929");
        System.out.println("postalCoode " + externa.getPostalCode());
        System.out.println("poblacion: " + externa.getPoblacion());
        System.out.println("pic: " + externa.getPic());
        System.out.println("coordenadas" + externa.getCoordenadas());
        System.out.println("pais: " + externa.getPais());
        System.out.println("area: " + externa.getArea());

    }
}
