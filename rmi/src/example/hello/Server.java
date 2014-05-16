package example.hello;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Server implements Hello{
	
	private int serviceCount;

	public Server() {
		this.serviceCount = 0;
	}


	public String sayHello() {
		
		return new StringBuilder("Hello, world!")
			.append(" serviceCount: ")
			.append(++serviceCount)
			.toString();
	}


	public static void main(String[] args)throws InterruptedException {

		String helloPath = Hello.class.getResource("Hello.class").getFile();
		helloPath = helloPath.replace("/example/hello/Hello.class", "");
		String downloadLocation = "file:" + helloPath;

		try {

			//RMI se conecta al puerto 1099

			System.setProperty("java.rmi.server.codebase", downloadLocation);

			Server obj = new Server();
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj,0);

			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", stub);

			System.err.println("Server ready");

		} 
		catch (Exception e) {

			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

