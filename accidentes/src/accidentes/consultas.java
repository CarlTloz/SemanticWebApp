package accidentes;

import metodos.App;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "consultas")
public class consultas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    App a = new App();
    String res = "defecto";
    ServletContext context = this.getServletContext();
    String fullPath = context.getRealPath("/WEB-INF/resources/biciaccidents-with-links.ttl");
    try

    {
       res = a.toString(fullPath,request.getParameter("param1"));

    }
        catch(Exception e)
    {
        e.printStackTrace();
    }

        request.setAttribute("param1", res);
        request.getRequestDispatcher("/consultas.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("escribe:" + request.getParameter("param1"));
    }
}
