import java.net.*;
import java.io.*;

public class SharedActionState {
		private SharedActionState mySharedObj;
		private String myThreadName;
		private double accountA;
		private double accountB;
		private double accountC;
		private boolean accessing=false; // true a thread has a lock, false otherwise
		private int threadsWaiting=0; // number of waiting writers
		
		SharedActionState(double startingBalance) {
			accountA = startingBalance; 
			accountB = startingBalance; 
			accountC = startingBalance;}
		
		
		//Attempt to aquire a lock
		  public synchronized void acquireLock() throws InterruptedException{
		        Thread me = Thread.currentThread(); // get a ref to the current thread
		        System.out.println(me.getName()+" is attempting to acquire a lock!");	
		        ++threadsWaiting;
		        
		        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
				      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
				      //wait for the lock to be released - see releaseLock() below
				      wait();
				    }
				    // nobody has got a lock so get one
				    --threadsWaiting;
				    accessing = true;
				    System.out.println(me.getName()+" got a lock!"); 
				  }

				  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
		  
		  /* The processInput method */

		    public synchronized String processInput(String myThreadName, String theInput) {
		        System.out.println(myThreadName + " received " + theInput);
		        String theOutput = null;
		        String[] parts = theInput.split(" +");

		        // Check what the client said bit
		        //Add or subtract money
		        
		        if (parts.length == 3 && (parts[0].equalsIgnoreCase("ADD") || parts[0].equalsIgnoreCase("SUBTRACT"))) {
		            double amount = Double.parseDouble(parts[2]);

		            if (parts[0].equalsIgnoreCase("SUBTRACT"))
		            	amount = -amount;

		            if (parts[1].equalsIgnoreCase("A"))
		                accountA = accountA + amount;
		            else if (parts[1].equalsIgnoreCase("B"))
		                accountB = accountB + amount;
		            else if (parts[1].equalsIgnoreCase("C"))
		                accountC = accountC + amount;
		            else
		                return "Invalid account";

		            theOutput = "Transaction completed. AccountA = " + accountA + " AccountB = " + accountB+ " AccountC = " + accountC;
		        }
		        
		        // Transfer money
		        else if (parts.length == 4 && parts[0].equalsIgnoreCase("TRANSFER")) {

		            boolean fromValid = parts[1].equalsIgnoreCase("A") || parts[1].equalsIgnoreCase("B") || parts[1].equalsIgnoreCase("C");
		            boolean toValid = parts[2].equalsIgnoreCase("A") ||parts[2].equalsIgnoreCase("B") || parts[2].equalsIgnoreCase("C");

		            if (!fromValid || !toValid || parts[1].equalsIgnoreCase(parts[2]))
		                return "Invalid accounts";

		            double amount = Double.parseDouble(parts[3]);

		            if (parts[1].equalsIgnoreCase("A"))
		                accountA = accountA - amount;
		            else if (parts[1].equalsIgnoreCase("B"))
		                accountB = accountB - amount;
		            else
		                accountC = accountC - amount;

		            if (parts[2].equalsIgnoreCase("A"))
		                accountA = accountA + amount;
		            else if (parts[2].equalsIgnoreCase("B"))
		                accountB = accountB + amount;
		            else
		                accountC = accountC + amount;

		            theOutput = "Transfer completed. AccountA = " + accountA+ " AccountB = " + accountB + " AccountC = " + accountC;
		        }
	        // Return the output message to ActionServer
	        System.out.println(theOutput);
    		return theOutput;
    	}	
}