package servlet;// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;

@WebServlet(name = "AttributeServlet", urlPatterns = {"/AttributeServlet"})
public class AttributeServlet extends HttpServlet
{
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // Get session object
        HttpSession session = request.getSession();

        String name   = request.getParameter("attrib_name");
        String value  = request.getParameter("attrib_value");
        String name2   = request.getParameter("attrib_name2");
        String value2  = request.getParameter("attrib_value2");
        String remove = request.getParameter("attrib_remove");

        if (remove != null && remove.equals("on"))
        {
            session.removeAttribute(name);
        }
        else
        {
            if ((name != null && name.length() > 0) && (value != null && value.length() > 0))
            {
                session.setAttribute(name, value);
            }

        }

        if (remove != null && remove.equals("on"))
        {
            session.removeAttribute(name2);
        }
        else
        {
            if ((name2 != null && name2.length() > 0) && (value2 != null && value2.length() > 0))
            {
                session.setAttribute(name2, value2);
            }

        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        // no-cache lets the page reload by clicking on the reload link
        out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
        out.println("<head>");
        out.println(" <title>Session lifecycle</title>");
        out.println("</head>");
        out.println("");

        out.println("<body>");
        out.println("<h1><center>Session attributes</center></h1>");

        out.println("Enter name and value of an attribute");

        // String url = response.encodeURL ("offutt/servlet/AttributeServlet");
        String url = response.encodeURL("AttributeServlet");
        out.println("<form action=\"" + url + "\" method=\"GET\">");
        out.println(" Name: ");
        out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

        out.println(" Value: ");
        out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");

        out.println(" Name2: ");
        out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name2\">");

        out.println(" Value2: ");
        out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value2\">");

        out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
        out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
        out.println("</form>");
        out.println("<hr>");

        out.println("Attributes in this session:");
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements())
        {
            String att_name  = (String) e.nextElement();
            String att_value = (String) session.getAttribute(att_name);
            String att_name2  = (String) e.nextElement();
            String att_value2 = (String) session.getAttribute(att_name2);

            out.print  ("<br><b>Name:</b> ");
            out.println(att_name);
            out.print  ("<br><b>Value:</b> ");
            out.println(att_value);
            out.print  ("<br><b>Name2:</b> ");
            out.println(att_name2);
            out.print  ("<br><b>Value2:</b> ");
            out.println(att_value2);
        } //end while

        out.println("</body>");
        out.println("</html>");
        out.close();
    } // End doGet
} //End  SessionLifeCycle