import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Glossary program that takes user's input file and generate's HTML output
 * file.
 *
 * @author KaylinKerrick
 */
public final class Glossary {

    /**
     * Private no-argument constructor to prevent instantiation of this utility
     * class.
     */
    private Glossary() {
    }

    /**
     * Reads the glossary file and sorts terms and definitions into a map.
     *
     * @param file
     *            user inputed glossary file
     * @return a map of glossary terms and definitions from input file
     * @requires file exists and is properly formatted
     * @ensures parseFile = [map of terms to their definitions]
     */
    public static Map<String, String> parseFile(String file) {
        //Make glossary into a new TreeMap, this will store the terms
        //in alphabetical order
        Map<String, String> glossary = new TreeMap<>();

        //Read the inputed file
        SimpleReader in = new SimpleReader1L(file);

        //While it's not the end of the stream
        while (!in.atEOS()) {
            //Take in each term and remove any whitespace
            String term = in.nextLine().trim();

            //Initialize a string builder for the term definitions
            StringBuilder sb = new StringBuilder();
            boolean emptyLine = false;

            //Loop until stream ends or there's an empty line
            while (!in.atEOS() && !emptyLine) {
                String line = in.nextLine();
                String lineTrimmed = line.trim();
                //Exit loop if line is empty
                if (lineTrimmed.isEmpty()) {
                    emptyLine = true;
                } else {
                    sb.append(line.trim()).append(" ");
                }
            }
            //Add the term and definition to the glossary map
            String definition = sb.toString().trim();
            glossary.put(term, definition);
        }

        in.close();

        //Return glossary map
        return glossary;

    }

    /**
     * Generates an HTML page for the index of the glossary. Lists each of the
     * terms in alphabetical order with links to the definitions.
     *
     * @param terms
     *            Set of the term names
     * @param outputFolder
     *            Name of the folder for HTML pages
     * @param out
     *            SimpleWriter output stream to print tags
     * @requires out stream is open and the outputFolder location exists
     * @ensures index.html is created and in the output folder
     */
    public static void indexHtml(Set<String> terms, String outputFolder,
            SimpleWriter out) {
        //Print out the index page HTML tags
        out.println("<html>");
        //Tab Name and Title is Glossary
        out.println("<head><title>Glossary</title></head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        //Start List
        out.println("<ul>");

        //Loop through each individual term
        for (String term : terms) {
            //Link to each term's own HTML page
            out.println("<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        //Close tags
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    /**
     * Generates an HTML page for each individual term in the glossary. Each
     * term contains a definition and a link back to the index.html
     *
     * @param term
     *            String of the term name
     * @param definition
     *            String of the term's definition
     * @param outputFolder
     *            Name of the folder for HTML pages
     * @param out
     *            SimpleWriter output stream to print tags
     * @requires out stream is open and the outputFolder location exists
     * @ensures each term's HTML page is created and in the correct folder
     */
    public static void termHtml(String term, String definition, String outputFolder,
            SimpleWriter out) {
        //Print out the term page HTML tags
        out.println("<html>");
        //The term is the title of the tab
        out.println("<head><title>" + term + "</title></head>");
        out.println("<body>");

        //The title of the page is the term in red
        out.println("<h2><i><b><font color =\"red\">" + term + "</font></b></i></h2>");

        //Print the definition in the paragraph
        out.println("<p>" + definition + "</p>");

        //Link to return back to index page with all terms
        out.println("<p><a href=\"index.html\">Return to Glossary</a></p>");

        //Close tags
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command-line arguments (not used)
     * @ensures [all glossary HTML pages are created]
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // C:\Users\kayli\Downloads\terms.txt when testing
        //Ask user to enter a file
        out.println("Enter a file: ");
        String file = in.nextLine();

        //Ask user where they want HTML files to generate
        out.println("Enter output folder path: ");
        String folder = in.nextLine();

        //Create glossary map, call the parseFile method
        Map<String, String> glossary = parseFile(file);

        //Create a set with all the terms
        Set<String> terms = glossary.keySet();

        //Create index writer then call method to make index.html
        SimpleWriter indexWriter = new SimpleWriter1L(folder + "/index.html");
        indexHtml(terms, folder, indexWriter);

        //Loop through each term
        for (String term : terms) {
            // Get each term's definition
            String definition = glossary.get(term);

            //Create termWriter and call method to make each term's HTML page
            SimpleWriter termWriter = new SimpleWriter1L(folder + "/" + term + ".html");
            termHtml(term, definition, folder, termWriter);
        }

        //Print message so user knows where to find the files
        out.println("Glossary generated. Check " + folder + " folder.");

        //Close input and output
        in.close();
        out.close();
    }

}
