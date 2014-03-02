/**
 * 
 */
package example.channel.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import example.channel.ChatClient;
import example.channel.ChatServer;
import example.channel.Message;

/**
 * Implementacion del cliente
 * @author Alfonso Kim
 */
public class ChannelChatClient extends UnicastRemoteObject implements ChatClient {


	private static final long serialVersionUID = -522233638008528177L;
	
	private String name;
	private String channel;
	private ChatServer server;
	
	/**
	 * Constructor
	 * @param name		Nombre del cliente
	 * @param channel	Canal donde se conecta el cliente
	 * @param url		URL del servidor de chat
	 * @throws RemoteException
	 */
	public ChannelChatClient(String name, String channel, String url) 
			throws RemoteException {
		super();
		this.name = name;
		this.channel = channel;
		
		try {
			server = (ChatServer) Naming.lookup("rmi://" + url + "/ChannelChatServer");
			server.login(this); // callback object
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see example.channel.ChatClient#getName()
	 */
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	/**
	 * @see example.channel.ChatClient#getChannel()
	 */
	@Override
	public String getChannel() throws RemoteException {
		return channel;
	}

	/**
	 * @see example.channel.ChatClient#receiveEnter(java.lang.String)
	 */
	@Override
	public void receiveEnter(String name) throws RemoteException {
		System.out.println(name + " just logged in");
	}

	/**
	 * @see example.channel.ChatClient#receiveExit(java.lang.String)
	 */
	@Override
	public void receiveExit(String name) throws RemoteException {
		System.out.println(name + " just logged out");
	}

	/**
	 * @see example.channel.ChatClient#receiveMessage(example.channel.Message)
	 */
	@Override
	public void receiveMessage(Message message) throws RemoteException {
		System.out.println(message.getName() + ": " + message.getText());
	}
	
	/**
	 * @see example.channel.ChatClient#sendMessage(String)
	 */
	@Override
	public void disconnect() throws RemoteException{
		server.logout(this);
	}
	
	/**
	 * @see example.channel.ChatClient#sendMessage(String)
	 */
	@Override
	public void sendMessage(String text) throws RemoteException {
		Message message = new Message(name, channel, text);
		server.send(message);
	}
	
	/**
	 * Captura una linea de la consola
	 * @param out	Mensaje a desplegar
	 * @return		El mensaje capturado por el usuario
	 * @throws IOException	En caso de error
	 */
	private static String getMessageFromStdIn(String out) throws IOException {
		System.out.println(out);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 3){
			System.out.println("Uso: nombre canal servidor");
			System.exit(-1);
		}
		try {
			ChatClient client = new ChannelChatClient(args[0], args[1], args[2]);
			String message;
			while(! (message = getMessageFromStdIn("mensaje>")).equals("quit") ){
				client.sendMessage(message);
			}
			System.out.println("bye!");
			client.disconnect();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
