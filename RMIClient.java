// RMIClient.java
//
// This sample Java RMI client can perform the
// following operations:
//   (1)  Send a message to a remote object.  This
//        is done by using the -m command line switch.
//        Example:  java RMIClient -m "My message in quotes"
//   (2)  Calculate the factorial of a given number via
//        a method of the remote object.
//        Example:  java RMIClient -f 5

import java.rmi.*;
import java.rmi.server.*;

public class RMIClient
{
    public static void main(String argv[])
    {
        // Validate command line parameters
        if (argv.length < 2)
        {
        System.out.println("Usage: java RMIClient [-m \"MESSAGE\"] [-f INTEGER]");
            System.exit(1);
        }

        // Command line option flags
        boolean bMessage = false;
        boolean bFactorial = false;

        String strMsg = "No message.";
        long   lVal = 1;

        // Determine data to be processed
        for (int i=0; i<argv.length; i++)
        {
            if (argv[i].equals("-m"))
            {
                bMessage = true;
                strMsg = argv[++i];
            }
            if (argv[i].equals("-f"))
            {
                bFactorial = true;
                lVal = Long.parseLong(argv[++i]);
            }
         }

        // Install security manager.  This is only necessary
        // if the remote object's client stub does not reside
        // on the client machine (it resides on the server).
        System.setSecurityManager(new RMISecurityManager());

        // Get a remote reference to the RMIExampleImpl class
        String strName = "rmi://wpi.wpi.edu/TheRMIExample";
        System.out.println("Client: Looking up " + strName + "...");
        RMIClass RemRMIExample = null;

        try
        {
            RemRMIExample = (RMIClass)Naming.lookup(strName);
        }
        catch (Exception e)
        {
        System.out.println("Client: Exception thrown looking up " + strName);
        System.exit(1);
    }

        // Send a messge to the remote object
        if (bMessage)
        {
            try
            {
                if (!RemRMIExample.PostMsg(strMsg))
                    System.out.println("Client: Remote PostMsg() call failed.");
            }
            catch (Exception e)
            {
            System.out.println("Client: Exception thrown calling PostMsg().");
            System.exit(1);
            }
        }

        // Calculate the factorial
        if (bFactorial)
        {
            try
            {
                long lRes = RemRMIExample.Factorial(lVal);
                System.out.println("Client: Factorial(" + lVal + ") = " + lRes);
            }
            catch (Exception e)
            {
                 System.out.println("Client: Excpetion thrown calling Factorial().");
                 System.exit(1);
            }
         }
    }
}