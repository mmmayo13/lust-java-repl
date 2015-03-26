/**
 * @filename:       Repl.java
 * @description:    A very simple Java REPL.
 * @version:        0.1
 * @author:         Matthew Mayo
 * @modified:       2015-03-22
 * @usage:          java Repl
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class Repl {

    // Code snippet contents, line by line
    ArrayList<String> snippet = new ArrayList<String>();

    /**
     * Main method creates new Repl
     */
    public static void main(String[] args) throws IOException,
            InterruptedException {
        Repl repl = new Repl();
    }

    /**
     * Default constructor sets up console, loops on console input
     */
    public Repl() throws IOException, InterruptedException {

        // Lingering file cleanup
        cleanup();

        // Clear screen
        for (int i = 0; i < 50; ++i)
			System.out.println();

        // Get Java version
        String ver = getJavaVersion();

        // Output welcome message
        System.out.println(ver);
        System.out.println("The Limited Use Somewhat Trivial (LUST) Java REPL"
                + " version 0.1\n");

        // Loop on console input
        while (true)
            this.console();

    }

    /**
     * Accepts input from console
     */
    private void console() throws IOException, InterruptedException,
            FileNotFoundException {

        // Get input, build snippet until EOF (Ctrl+D)
        String line = "";
        System.out.print("java> ");
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            System.out.print("      ");
            line = scan.nextLine();
            snippet.add(line);
            switch (line.toLowerCase()) {
                case ":exit":
                    cleanup();
                    System.out.println();
                    System.exit(1);
                default:
                    break;
            }
        }

        // Create Java file
        createJavaFile(snippet);

        // Compile to class file
        compileAndExecute();

        // Reset snippet
        snippet.clear();

        // Always cleanup
        cleanup();

    }

    /**
     * Gets the JVM version on machine
     * @returns JVM version as String
     */
    private String getJavaVersion() throws IOException {

        String jvm = "";
        String s = null;
        ArrayList<String> version = new ArrayList<String>();
        Runtime rt = Runtime.getRuntime();
        String[] v = {"java", "-version"};
        Process p = rt.exec(v);

        BufferedReader err = new BufferedReader(new
             InputStreamReader(p.getErrorStream()));

        while ((s = err.readLine()) != null)
            version.add(s);
        for (int i = 0; i < version.size(); i++)
            if (i == version.size() - 1)
                jvm = version.get(i);
        return jvm;

    }

    /**
     * Creates Java file of code snippet
     * @param ArrayList<String> of all command in snippet
     */
    private void createJavaFile(ArrayList<String> snippet)
            throws FileNotFoundException{

        PrintWriter out = new PrintWriter("Temp.java");
        out.println("public class Temp {");
        out.println("\tpublic static void main(String[] args) {");

        // The statements
        for (int i = 0; i < snippet.size(); i++)
            out.println("\t\t" + snippet.get(i));

        out.println("\t}");
        out.println("}");
        out.close();

    }

    /**
     * Compiles Java file to bytecode and executes
     */
    private void compileAndExecute() throws IOException, InterruptedException {

        Boolean compileError = false;

        // Compile
        Runtime rt = Runtime.getRuntime();
        String[] cmd1 = {"javac", "Temp.java"};
        Process p1 = rt.exec(cmd1);
        String errCompile = null;

        BufferedReader stdErrorCompile = new BufferedReader(new
            InputStreamReader(p1.getErrorStream()));

        // Check for compile time errors and output
        if ((errCompile = stdErrorCompile.readLine()) != null) {
            compileError = true;
            System.out.println();
            System.out.println(errCompile);
            while ((errCompile = stdErrorCompile.readLine()) != null)
                System.out.println(errCompile);
        }

        // Wait for compile to finish
        p1.waitFor();

        // Execute
        String[] cmd2 = {"java", "Temp"};
        Process p2 = rt.exec(cmd2);
        String result = null;
        String errRun = null;

        BufferedReader stdInput = new BufferedReader(new
            InputStreamReader(p2.getInputStream()));

        BufferedReader stdErrorRun = new BufferedReader(new
            InputStreamReader(p2.getErrorStream()));

        // Runtime errors
        if ((errRun = stdErrorRun.readLine()) != null && compileError == false) {
            System.out.println();
            System.out.println(errRun);
            while ((errRun = stdErrorRun.readLine()) != null)
                System.out.println(errRun);
        }

        // Results
        else if (compileError == false) {
            System.out.println();
            while ((result = stdInput.readLine()) != null)
                System.out.println(result);
        }

        System.out.println();
        compileError = false;

    }

    /**
     * Delete temp Java and class files used in session
     */
     private void cleanup() {
         File f1 = new File("Temp.java");
         File f2 = new File("Temp.class");
         f1.delete();
         f2.delete();
     }

}
