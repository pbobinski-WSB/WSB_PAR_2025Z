package wsb.par.rmi.simple;
// Importing java.rmi package to enable object of one JVM
// to invoke methods on object in another JVM
import java.rmi.*;

// Main class
public class Client {

    // Main driver method
    public static void main(String args[])
    {
        // try block to check for exceptions
        try {

            // Looking for object in remote registry
            demo client = (demo)Naming.lookup(
                    "rmi://localhost:3000/simplermi");

            // Print and display the client message
            System.out.println(client.msg("PBo"));

            System.out.println(client.add(12, 23));

        }

        // Catch block to handle the exception
        catch (Exception e) {
        }
    }
}
