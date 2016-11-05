package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet
{
    String cemail;
    ResultSet res;
    Connection con;
    Statement stmt;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("uname");
        String email = req.getParameter("email");
        cemail= req.getParameter("cemail");
        
        resp.setContentType("text/html");
        
            
        PrintWriter out = resp.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Welcome "+name+",</h1>");
        out.println("<h3> With email '"+email+"'.</h3><br>");
        
        out.println("<p>Search with name:</p>");
        out.println("<form action=\"search\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"uname\" value=\""+name+"\">");
        out.println("<input type=\"hidden\" name=\"email\" value=\""+email+"\">");
        out.println("Name : <input type=\"text\" name=\"cemail\">");
        out.println("<input type=\"submit\" value=\"Search\">");
        out.println("</form>");
        
        if(!cemail.equals(""))
        {
            out.println("<table border=\"solid\">");
            out.println("<tr><td><b>ID</b></td><td><b>NAME</b></td><td><b>EMAIL</b></td><td><b>STATUS</b></td></tr>");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2","root","abc123");
                //stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
             //   stmt.execute("INSERT INTO MYDATA2(`NAME`,`EMAIL`) VALUES ('"+name+"','"+email+"')");
              //  res = stmt.executeQuery("SELECT * FROM MYDATA2 WHERE `NAME` LIKE '"+cemail+"%'");
               
                PreparedStatement ps = con.prepareStatement("SELECT * FROM MYDATA2 WHERE `NAME` LIKE ?");
                ps.setString(1,"'"+email+"%'");
                res = ps.executeQuery();
                while(res.next())
                {
                    String dbname = res.getString("NAME");
                    String dbemail = res.getString("EMAIL");
                    String dbid = res.getString("ID");
                    String dbstatus = res.getString("STATUS");
                    out.println("<tr><td>"+dbid+"</td><td>"+dbname+"</td><td>"+dbemail+"</td><td>"+dbstatus+"</td><tr>");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                try {
                res.close();
                stmt.close();
                con.close();
                } 
                catch (SQLException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("uname");
        String email = req.getParameter("email");
        cemail= req.getParameter("cemail");
        
        
        resp.setContentType("text/html");
        
        PrintWriter out = resp.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3> Welcome "+name+",</h3><br>");
        out.println("<p>Search with Email:</p><br>");
        out.println("<form action=\"search\" method=\"GET\">");
        out.println("Email : <input type=\"text\" name=\"cemail\">");
        out.println("</form>");
        out.println("<p>Email is +"+cemail+"</p>");
        out.println("<table>");
        out.println("<tr><td>NAME  </td><td>EMAIL</td></tr>");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2","root","abc123");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            res = stmt.executeQuery("SELECT * FROM MYDATA WHERE `NAME` LIKE '"+cemail+"%'");
            while(res.next())
            {
                String dbname = res.getString("NAME");
                String dbemail = res.getString("EMAIL");
                out.println("<tr><td>"+dbname+"</td><td>"+dbemail+"</td></tr>");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
            res.close();
            stmt.close();
            con.close();
            } 
            catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
