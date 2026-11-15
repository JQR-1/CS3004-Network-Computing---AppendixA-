ActionServer:
ActionServer creates a socket on port 4545 and initializes one SharedActionState object. It accepts three client connections, 
and starts a separate ActionServerThread for each connection.

ActionServerThread:
ActionServerThread handles the socket connection for each client. It initially receives requests and calls SharedActionState to 
interpret and process transactions, responsible for requesting the lock from the SharedActionState per request , and sends responses 
back to the client.

SharedActionState:
Stores the three account balances, initially set to 1000 units. The processInput() method interprets ADD, SUBTRACT and TRANSFER requests, 
while acquireLock() and releaseLock() coordinate what thread has access to the balances by managing the lock.

ClientA:
Connects to the ActionServer using the port 4545 socket interface. Allows users to send banking requests in the console and displays 
responses from the server.

ClientB:
Connects to the ActionServer using the port 4545 socket interface. Allows users to send banking requests in the console and displays 
responses from the server.

ClientC:
Connects to the ActionServer using the port 4545 socket interface. Allows users to send banking requests in the console and displays 
responses from the server.
