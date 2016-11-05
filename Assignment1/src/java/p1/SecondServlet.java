package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        
        res.setContentType("text");
        try {
            PrintWriter out = res.getWriter();
            out.print("Hello world "+name+":"+email);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SecondServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
