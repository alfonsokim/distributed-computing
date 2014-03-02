/**
 * 
 */
package example.topic.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.topic.TopicClient;
import example.topic.TopicMessage;
import example.topic.TopicServer;

/**
 * Implementacion del servidor basado en temas
 * 
 * @author Alfonso Kim
 */
public class TopicChatServer extends UnicastRemoteObject implements TopicServer {

	private static final long serialVersionUID = -4544991454577390540L;
	
	protected Map<String, ArrayList<TopicClient>> topics;

	/**
	 * Constructor
	 * 
	 * @throws RemoteException
	 */
	public TopicChatServer() throws RemoteException {
		super();
		topics = new HashMap<String, ArrayList<TopicClient>>();
	}

	
	/**
	 * @see example.topic.TopicServer#login(example.topic.TopicClient)
	 */
	@Override
	public void login(TopicClient newClient) throws RemoteException {
		for( String topic: newClient.getTopics() ){
			if( topics.containsKey(topic) ){
				ArrayList<TopicClient> clients = topics.get(topic);
				for(TopicClient client: clients){
					client.receiveEnter(newClient);
				}
				clients.add(newClient);
			} else {
				ArrayList<TopicClient> clients = new ArrayList<TopicClient>();
				clients.add(newClient);
				topics.put(topic, clients);
			}
		}
		System.out.println("Client " + newClient.getName() + " suscribed to " + 
				newClient.getTopics());
	}

	
	/**
	 * @see example.topic.TopicServer#logout(example.topic.TopicClient)
	 */
	@Override
	public void logout(TopicClient clientGone) throws RemoteException {
		for( String topic: clientGone.getTopics() ){
			if( topics.containsKey(topic) ){
				ArrayList<TopicClient> clients = topics.get(topic);
				for( TopicClient client: clients ){
					client.receiveExit(clientGone);
				}
				System.out.println("client " + clientGone.getName() + 
						" has logged out from " + topic);	
			}
		}
	}

	/**
	 * @see example.topic.TopicServer#send(example.topic.TopicMessage)
	 */
	@Override
	public void send(TopicMessage message) throws RemoteException {
		if( topics.containsKey(message.getTopic()) ){
			ArrayList<TopicClient> clients = topics.get(message.getTopic());
			for(TopicClient client: clients){
				if( !client.getName().equals(message.getName()) ){
					client.receiveMessage(message);
				}
			}
		} else {
			System.out.println("unrecognized topic: " + message.getTopic());
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		String helloPath = TopicChatServer.class.getResource("TopicChatServer.class").getFile();
		helloPath = helloPath.replace("/example/channel/TopicChatServer.class", "");
		String downloadLocation = "file:" + helloPath;

		String serverURL = new String("///TopicChatServer");
		System.setProperty("java.rmi.server.codebase", downloadLocation);
		
		try {
			TopicChatServer server = new TopicChatServer();
			Naming.rebind(serverURL, server);
			System.out.println("Topic Server Ready!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
