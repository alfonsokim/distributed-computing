/**
 * 
 */
package example.topic.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

import example.topic.TopicClient;
import example.topic.TopicMessage;
import example.topic.TopicServer;

/**
 * Implementacion del cliente basado en temas
 * 
 * @author Alfonso Kim
 */
public class TopicChatClient extends UnicastRemoteObject implements TopicClient {


	private static final long serialVersionUID = 3611959328261936140L;
	
	private String name;
	private List<String> topics;
	private TopicServer server;

	/**
	 * Constructor del cliente
	 * 
	 * @param name		Nombre del cliente
	 * @param topics	Nombre de los temas suscritos del cliente
	 * @param url		Direccion del servidor
	 * @throws RemoteException	En caso de error
	 */
	public TopicChatClient(String name, List<String> topics, String url) 
    throws RemoteException {
		super();
		this.name = name;
		this.topics = topics;
		try {
			server = (TopicServer) Naming.lookup("rmi://" + url + "/TopicChatServer");
			server.login(this); // callback object
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see example.topic.TopicClient#getName()
	 */
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	/**
	 * @see example.topic.TopicClient#getTopics()
	 */
	@Override
	public List<String> getTopics() throws RemoteException {
		return topics;
	}

	/**
	 * @see example.topic.TopicClient#receiveEnter(example.topic.TopicClient)
	 */
	@Override
	public void receiveEnter(TopicClient client) throws RemoteException {
		System.out.println(client.getName() + " just logged in");

	}

	/**
	 * @see example.topic.TopicClient#receiveExit(example.topic.TopicClient)
	 */
	@Override
	public void receiveExit(TopicClient client) throws RemoteException {
		System.out.println(client.getName() + " just logged out");
	}

	/**
	 * @see example.topic.TopicClient#receiveMessage(example.topic.TopicMessage)
	 */
	@Override
	public void receiveMessage(TopicMessage client) throws RemoteException {
		System.out.println(client.getName() + " [" + client.getTopic() + 
				"]:" + client.getText());
	}

	/**
	 * @see example.topic.TopicClient#sendMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMessage(String topic, String text) throws RemoteException {
		TopicMessage message = new TopicMessage(name, topic, text);
		server.send(message);
	}

	/**
	 * @see example.topic.TopicClient#disconnect()
	 */
	@Override
	public void disconnect() throws RemoteException {
		server.logout(this);
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
			System.out.println("Uso: nombre temas(separado por comas) servidor");
			System.exit(-1);
		}
		
		String name = args[0];
		List<String> topics = Arrays.asList(args[1].split(","));
		String server = args[2];
		
		try {
			TopicChatClient client = new TopicChatClient(name, topics, server);
			String text;
			while(!(text = getMessageFromStdIn(name + ":message>")).equals("quit")){
				String[] message = text.split(":");
				if (client.getTopics().contains(message[0])){
					client.sendMessage(message[0], message[1]);
				} else {
					System.out.println("client not suscribed to topic " + message[0]);
				}
			}
			client.disconnect();
			client = null;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
