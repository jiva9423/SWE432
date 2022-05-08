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
        name = "servlet2",
        urlPatterns = {"/servlet2"}
)
public class Servlet2 extends HttpServlet
{

    // Location of servlet.
// static String Domain  = "cs.gmu.edu:8443";
// static String Path    = "/offutt/servlet/";
    static String Servlet = "servlet2";

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
        ArrayList<String> valNames = new ArrayList<>();
        String operation = request.getParameter("Operation");
        String characteristicStr = request.getParameter("CHARNUM");
        Vector<characteristic> Characs = new Vector<>();

        boolean errors = false;

        if(characteristicStr != null){
            try{
                int val = Integer.parseInt(characteristicStr);
                if(val > 0){
                    charNumVal = val;
                }else{
                    errors = true;
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
                valNames.add("");
            }
            numInputs = rslt;
        }

        String showValues = "";
        if (operation.equals(OperationShow))
        {
            rslt = charNumVal;
            for(int i = 0; i < numInputs; i++){
                String c = request.getParameter("RSLT" + i);
                String n = request.getParameter("NAME" + i);
                c = c.replaceAll(" ", "");
                vals.add(c);
                valNames.add(n);
                Characs.add(new characteristic(n, Integer.parseInt(c)));
                int val = -1;
                try{
                    val = Integer.parseInt(c);
                }catch (NumberFormatException e){
                    val = 0;
                }
                showValues += "\n" + n + " [";
                for(int j = 0; j < val; j++){
                    showValues += n + j + " ";
                }

                showValues += " ]";
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        PrintHead(out);
        PrintBody(out, characteristicStr, rslt, showValues, vals,  valNames, Characs);
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
    private void PrintBody (PrintWriter out, String charNum, int rslt, String show, ArrayList<String> vals, ArrayList<String> valNames, Vector<characteristic> Characs)
    {
        out.println("<body>");
        out.println("<p>");
        out.println("Assignment 6. Javier Talavera, David Blatman.");
        out.println("<br>");
        out.println("<br>");
        out.println("<a href=\"https://github.com/jiva9423/SWE432/blob/main/src/main/java/servlet/Assignment6.java\">GithubRepo</a>");
        out.println("multiple submit buttons.");
        out.println("</p>");
        out.print  ("<form method=\"post\"");
        out.println(" action=\""+Servlet+"\">"); // relative path used so it works in local and deploy runs
        out.println("");
        out.println(" <table>");
        out.println("  <tr>");
        out.println("   <td>How many characteristics? (enter an integer greater than 0):");
        out.println("   <td><input type=\"text\" name=\"CHARNUM\" value=\""+charNum+"\" size=5>");
        out.println(" <em> </tr>");
        for(int i = 0; i < rslt; i++){
            String name = "RSLT" + i;
            String name2 = "NAME" + i;
            out.println("  <br>");
            out.println("  <tr>");
            out.println("   <td>Input Characteristic Name :");
            out.println("   <input type=\"text\" name=\"" + name2 + "\" value=\""+ valNames.get(i)+" \" size=6>");
            out.println("   Number of this characteristic:");
            out.println("   <td><input type=\"number\" name=\"" + name + "\" value=\""+ vals.get(i)+" \" size=6>");

        }
        out.println("</em>");
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
        out.print("<br>");
        if(Characs.size() > 0){
            EC(out, Characs.size(), Characs);
            out.print("<br>");
            BC(out, Characs.size(), Characs);
        }
        out.println("</body>");
    } // End PrintBody

    /** *****************************************************
     *  Overloads PrintBody (out,charNum, rslt) to print a page
     *  with blanks in the form fields.
     ********************************************************* */
    private void PrintBody (PrintWriter out)
    {
        PrintBody(out, "", 0, "", new ArrayList<String>(), new ArrayList<String>(), new Vector<characteristic>());
    }

    /** *****************************************************
     *  Prints the bottom of the HTML page.
     ********************************************************* */
    private void PrintTail (PrintWriter out)
    {
        out.println("");
        out.println("</html>");
    }

    // Each-choice--print the blocks in each-choice order
    private static void EC(PrintWriter out, int numCharacteristics, Vector<characteristic> Characs)
    {
        int maxCharacteristic=0;
        String block;
        characteristic C;
        for (int charNum=0; charNum<numCharacteristics; charNum++)
        {  // Find the maximum # blocks among the characteristics
            C = Characs.get(charNum);
            if (C.getNumBlocks()>maxCharacteristic)
                maxCharacteristic= C.getNumBlocks();
        }
        out.print("<br>");
        out.println("\n" + maxCharacteristic + " each-choice abstract tests");
        for (int testNum=1; testNum<=maxCharacteristic; testNum++)
        {
            out.print("<br>");
            out.println("Abstract test " + testNum + ": [");
            for (int charNum=0; charNum<numCharacteristics; charNum++)
            {
                C=Characs.get(charNum);
                block=C.getName();
                out.print(block);
                if (testNum<=C.getNumBlocks())
                    out.print(testNum);
                else // no more blocks, use wild card
                    out.print("*");
                if (charNum<numCharacteristics-1)
                    out.print(", ");
            }
            out.println("]");
        }
    } // end EC

    // Base-choice--print the blocks in base-choice order
// Assume base blocks are all '1'
    private static void BC(PrintWriter out, int numCharacteristics, Vector<characteristic> Characs)
    {
        String block;
        characteristic C;
        int numTests = 1; // start at 1 for the base test
        for (int CNum=0; CNum<numCharacteristics; CNum++)
        {  // Find the maximum # blocks among the characteristics
            C = Characs.get(CNum);
            numTests = numTests + (C.getNumBlocks()-1);
        }
        out.print("<br>");
        out.println("\n" + numTests + " base-choice abstract tests");
        out.print("<br>");

        // Create base test
        Vector<String> baseTest = new Vector<>();
        for (int CNum=0; CNum<numCharacteristics; CNum++)
        {
            C = Characs.get(CNum);
            baseTest.add(C.getName()+"1");
        }
        out.println("Abstract test 1 (base): ");
        out.println(baseTest);

        // non-base tests
        Vector<String> nextTest = new Vector<>(baseTest);
        int testNum = 2;
        for (int CNum=0; CNum<numCharacteristics; CNum++)
        { // for (int BNum=2; BNum<=3; BNum++)
            C = Characs.get(CNum);
            for (int BNum=2; BNum<=C.getNumBlocks(); BNum++)
            {
                nextTest.set(CNum, C.getName()+String.valueOf(BNum));
                out.print("<br>");
                out.println("Abstract test " + testNum + " = " + nextTest);
                testNum++;
                nextTest.set(CNum, baseTest.get(CNum));
            }
        }
    }



}  // End twoButtons

