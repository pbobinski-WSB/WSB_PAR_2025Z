package wsb.par.rmi.simple;// Server's Side

// Importing java.rmi package to enable object of one JVM
// to invoke methods on object in another JVM


import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

// Interface
// creating remote interface
interface demo extends Remote {
    public String msg(String msg) throws RemoteException;

    public int add(int a, int b) throws RemoteException;
}

// Class 1
// Helper Class
class demoRemote extends UnicastRemoteObject implements demo {
    demoRemote() throws RemoteException { super(); }

    // @Override
    public String msg(String msg) throws RemoteException
    {
        // Display message only
        System.out.println("simple message "+msg);
        return "simple message sent "+msg;
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }


}

// Class 2
// Main class
public class Server {

    // Main driver method
    public static void main(String args[])
            throws RemoteException, NotBoundException,
            AlreadyBoundException
    {

        // Try block to check for exceptions
        try {

            // Creating a new registry by creating
            // an object of Registry class
            Registry registry
                    = LocateRegistry.createRegistry(3000);

            demo obj = new demoRemote();

            // binding obj to remote registry
            Naming.bind("rmi://localhost:3000"
                            + "/simplermi",
                    (Remote)obj);

            // Display message when program
            // is executed succussfully
            System.out.println(
                    "registry created successfully");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Getting the name of the exception using
            // the toString() method over exception object
            System.err.println(e.toString());
        }
    }
}
