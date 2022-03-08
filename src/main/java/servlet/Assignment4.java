package servlet;

/** *****************************************************************
 twoButtons.java   servlet example

 @author Jeff Offutt
  ********************************************************************* */
// modified: David Gonzalez (uses @WebServlet, and js/ resource folder)

// Import Java Libraries
import java.io.*;
import java.util.*;


// twoButtons class
// CONSTRUCTOR: no constructor specified (default)
//
// ***************  PUBLIC OPERATIONS  **********************************
// public void doPost ()  --> prints a blank HTML page
// public void doGet ()  --> prints a blank HTML page
// private void PrintHead (PrintWriter out) --> Prints the HTML head section
// private void PrintBody (PrintWriter out) --> Prints the HTML body with
//              the form. Fields are blank.
// private void PrintBody (PrintWriter out, String lhs, String rhs, String rslt)
//              Prints the HTML body with the form.
//              Fields are filled from the parameters.
// private void PrintTail (PrintWriter out) --> Prints the HTML bottom
//***********************************************************************

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "assignment4",
        urlPatterns = {"/assignment4"}
)
public class Assignment4 extends HttpServlet
{

    // Location of servlet.
// static String Domain  = "cs.gmu.edu:8443";
// static String Path    = "/offutt/servlet/";
    static String Servlet = "assignment4";

    // Button labels
    static String OperationSubmit = "Submit";
    static String OperationShow = "Show";
    static int numInputs = 0;

    // Other strings.
    static String Style ="https://www.cs.gmu.edu/~offutt/classes/432/432-style.css";

    /** *****************************************************
     *  Overrides HttpServlet's doPost().
     *  Converts the values in the form, performs the operation
     *  indicated by the submit button, and sends the results
     *  back to the client.
     ********************************************************* */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        int rslt = 0;
        int charNumVal = 0;
        ArrayList<String> vals = new ArrayList<>();
        String operation = request.getParameter("Operation");
        String characteristicStr = request.getParameter("CHARNUM");

        if(characteristicStr != null){
            try{
                int val = Integer.parseInt(characteristicStr);
                if(val > 0){
                    charNumVal = val;
                }else{

                }
            }catch (NumberFormatException e){
                charNumVal = 0;
            }
        }

        if (operation.equals(OperationSubmit))
        {
            rslt = charNumVal;
            for(int i = 0; i < rslt; i++){
                vals.add("");
            }
            numInputs = rslt;
        }

        String showValues = "";
        if (operation.equals(OperationShow))
        {
            rslt = charNumVal;
            for(int i = 0; i < numInputs; i++){
                String c = request.getParameter("RSLT" + i);
                vals.add(c);
                showValues += " " + c;
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        PrintHead(out);
        PrintBody(out, characteristicStr, rslt, showValues, vals);
        PrintTail(out);
    }  // End doPost

    /** *****************************************************
     *  Overrides HttpServlet's doGet().
     *  Prints an HTML page with a blank form.
     ********************************************************* */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        PrintHead(out);
        PrintBody(out);
        PrintTail(out);
    } // End doGet

    /** *****************************************************
     *  Prints the <head> of the HTML page, no <body>.
     ********************************************************* */
    private void PrintHead (PrintWriter out)
    {
        out.println("<html>");
        out.println("");

        out.println("<head>");
        out.println("<title>Two buttons example</title>");
        out.println("<script src=\"js/index.js\"/></script>"); // here to show how to access resources placed at src/main/webapp/
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
        out.println("</head>");
        out.println("");
    } // End PrintHead

    /** *****************************************************
     *  Prints the <BODY> of the HTML page with the form data
     *  values from the parameters.
     ********************************************************* */
    private void PrintBody (PrintWriter out, String charNum, int rslt, String show, ArrayList<String> vals)
    {
        out.println("<body>");
        out.println("<p>");
        out.println("A simple example that demonstrates how to operate with");
        out.println("multiple submit buttons.");
        out.println("</p>");
        out.print  ("<form method=\"post\"");
        out.println(" action=\""+Servlet+"\">"); // relative path used so it works in local and deploy runs
        out.println("");
        out.println(" <table>");
        out.println("  <tr>");
        out.println("   <td>How many characteristics? (enter an integer greater than 0):");
        out.println("   <td><input type=\"text\" name=\"CHARNUM\" value=\""+ charNum +"\" size=5>");
        out.println("  </tr>");
        for(int i = 0; i < rslt; i++){
            String name = "RSLT" + i;
            out.println("  <br>");
            out.println("  <tr>");
            out.println("   <td>Characteristic " + (i + 1) + " :");
            out.println("   <td><input type=\"text\" name=\"" + name + "\" value=\""+vals.get(i)+" \" size=6>");
        }

        out.println("<p>");
        out.println("");
        out.println("</p>");
        out.println("  </tr>");
        out.println(" </table>");
        out.println(" <br>");
        out.println(" <br>");
        out.println(" <input type=\"submit\" value=\"" + OperationSubmit + "\" name=\"Operation\">");
        out.println(" <input type=\"submit\" value=\"" + OperationShow + "\" name=\"Operation\">");
        out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\">");
        out.println("</form>");
        out.println(show);
        out.println("</body>");
    } // End PrintBody

    /** *****************************************************
     *  Overloads PrintBody (out,charNum, rslt) to print a page
     *  with blanks in the form fields.
     ********************************************************* */
    private void PrintBody (PrintWriter out)
    {
        PrintBody(out, "", 0, "", new ArrayList<String>());
    }

    /** *****************************************************
     *  Prints the bottom of the HTML page.
     ********************************************************* */
    private void PrintTail (PrintWriter out)
    {
        out.println("");
        out.println("</html>");
    } // End PrintTail

}  // End twoButtons

