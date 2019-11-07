package accidentes;

import metodos.App;
import metodos.ExternalQueryMain;
import metodos.ObjectConsulta;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "resource")
public class resource extends HttpServlet {
    private App a = new App();
    private ExternalQueryMain consulta_externa = new ExternalQueryMain();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String res = "";

    ServletContext context = this.getServletContext();
    String fullPath = context.getRealPath("/WEB-INF/resources/biciaccidents-with-links.ttl");
    String palabra_clave = "";
    String logo_path = context.getRealPath("/WEB-INF/resources/ayuntamiento-madrid-logo.png");
    String[] path = request.getServletPath().split("/");
    String nombre  = path[path.length - 1];
    /*    String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
    */
    //boolean es_colec = isCollectionResource(nombre);
        try {
            a = new App();
            res = res + "<table class=\"table table-striped\">" + "<tbody>" + "<tr><th> ID </th> <th> District </th> " +" <th>Date</th> " + "<th>Street</th> " +"<th>Lesivity</th>"
                   + "<th>Accident Type </th> <th> Wiki data District</th> ";
            palabra_clave = request.getParameter("palabra-clave");
            String[] u_name_a = request.getRequestURI().split("/");

            String u_name = u_name_a[u_name_a.length -1];
          boolean isCollectionResource =   u_name != null && (u_name.compareTo("accident") == 0 ||
                    u_name.compareTo("street") == 0 ||
                    u_name.compareTo("district") == 0 ||
                    u_name.compareTo("weather") == 0 ||
                    u_name.compareTo("lesivity") == 0 ||
                    u_name.compareTo("date")==0);

          if(isCollectionResource)
            a.toString(fullPath, palabra_clave, nombre,false);
          else
              a.toString(fullPath, u_name, u_name,true);

            String consulta_anterior = "";
            int contador = 1;
                for (ObjectConsulta b : a.consultas) {
                    res = res + "<tr>";
                    String[] uri_ = b.getUri().split("/");
                    int c = 0;
                    String uri__ = "/app";
                    for (String u : uri_) {
                        if (c > 2) uri__ = uri__ + "/" + u;
                        c++;
                    }
                    String[] id__ = b.getUri().split("/");
                    String id_ = id__[id__.length - 1];
                  res = res+  " <td><form action=\""+uri__+"\" method=\"post\">"+
                            "                    <input type=\"submit\" value=\""+id_+"\"/>"+
                            "                </form></td>";


                    //res = res + "<td>" + "<a href=\"" + uri__ + "\" style=\"font-size:80%;\">" + id_+ "</a>" + "</td>";
                    res = res + "<td>" + b.getDistrict() + "</td>";
                    res = res + "<td>"+ b.getDate() + "</td>";
                    res = res + "<td>" + b.getSteet() + "</td>";
                    res = res + "<td>"+ b.getInjuryStatus() +"</td>";
                    res = res + "<td>" + b.getTypeAccident() + "</td>";
                    String e = b.getWikidataDistrict();
                    if(consulta_anterior.compareTo(e)!=0) {
                        consulta_externa = new ExternalQueryMain();
                        consulta_externa.esta = false;
                        consulta_externa.externalSparklQuery(b.getWikidataDistrict());
                    }
                    while(!consulta_externa.esta);
                    String href = consulta_externa.getPic();
                    res = res + "<td>"+
                            "<div class=\"container\">"+
                            "  <!-- Trigger the modal with a button -->"+
                            "  <button type=\"button\" class=\"btn btn-info btn-lg\" data-toggle=\"modal\" data-target=\"#myModal"+contador+"\">"+ e +"</button>"+
                            ""+
                            "  <div class=\"modal fade\" id=\"myModal"+contador+"\" role=\"dialog\">"+
                            "    <div class=\"modal-dialog modal-xl\">"+
                            "      <div class=\"modal-content\">"+
                            "        <div class=\"modal-header\">"+
                            "          <h4 class=\"modal-title\">District: "+e+" (reconciled)</h4>"+
                            "          <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>"+
                            "        </div>"+
                            "        <div class=\"modal-body\">"+
                            "                <table>" +
                            "                   <tbody>" +
                     //       "                       <tr><th>Area</th>" +
                    //        "                        <th>Coordenadas</th> " +
                            "                           <th>Pais</th>" +
                            "                        <th>Poblacion</th>" +
                     //       "                    <th>Codigo postal</th></th>" +
                            "                       <tr> " +
                      //      "          <td> "+ consulta_externa.getArea() +"</td>"+
                            //    "          <td> "+ consulta_externa.getCoordenadas() +"</td>"+
                            "          <td> <a href=\""+consulta_externa.getPais()+"\">  Spain </href a></td>"+
                            "          <td> "+ consulta_externa.getPoblacion() +"</td>"+
                       //     "          <td>  "+ consulta_externa.getPostalCode() +"</td> </tr>"+
                            "                    </tbody>" +
                            "                  </table>"+
                            "          <p> Imagen: </p>"+ "<p><img src=\""+href+"\"  width=\"800\" height=\"500\" /></p>"+
                            "        </div>"+
                            "        <div class=\"modal-footer\">"+
                            "          <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>"+
                            "        </div>"+
                            "      </div>"+
                            "    </div>"+
                            "  </div>"
                            + "</td>";
                    res = res + "</tr>";
                    contador++;
                }
                res = res + "</tbody>" + "</table>";


            if(isCollectionResource) {
                System.out.println("pasaB");
                request.setAttribute("contenido", res);
                request.setAttribute("nombre", nombre);
                request.setAttribute("uri", request.getPathInfo());
                // request.setAttribute("paginaurl",remoteAddr);
                request.getRequestDispatcher("/resource.jsp").forward(request, response);

            }else{
                System.out.println("pasaA");
                request.setAttribute("contenido1",res);
                request.setAttribute("nombre1", u_name);
                request.getRequestDispatcher("/resourceV2.jsp").forward(request, response);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        /*
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        if (remoteAddr == null) {
            remoteAddr = "biciaccidentes.es";
        }

         */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] path = request.getServletPath().split("/");
        String nombre  = path[path.length - 1];
        request.setAttribute("nombre",nombre);
        request.getRequestDispatcher("/resource.jsp").forward(request,response);
    }
}
