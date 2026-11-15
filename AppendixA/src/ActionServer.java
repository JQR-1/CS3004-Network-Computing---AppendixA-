import java.net.*;
import java.io.*;

public class ActionServer {
	public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "ActionServer";
    int ActionServerNumber = 4545;
    
    double SharedVariable = 1000;

    //Create the shared object in the global scope...
    
    SharedActionState ourSharedActionStateObject = new SharedActionState(SharedVariable);
        
    // Make the server socket

    try {
      ActionServerSocket = new ServerSocket(ActionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + ActionServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(ActionServerName + " started");

    //Allows clients to join in any order instead of a correct one
    int nextThreadNumber = 1;
    while (listening && nextThreadNumber <= 3) {

        new ActionServerThread(ActionServerSocket.accept(),"ActionServerThread" + nextThreadNumber,ourSharedActionStateObject).start();
        System.out.println("New " + ActionServerName + " thread started.");

        nextThreadNumber++;
    }
    ActionServerSocket.close();
  }
}
