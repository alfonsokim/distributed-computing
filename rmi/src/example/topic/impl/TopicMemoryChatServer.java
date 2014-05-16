/**
 * 
 */
package example.topic.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.topic.TopicClient;
import example.topic.TopicMessage;

/**
 * @author Alfonso Kim
 *
 */
public class TopicMemoryChatServer extends TopicChatServer {

	private static final long serialVersionUID = 7544061415734559823L;
	
	private Map<String, ArrayList<TopicMessage>> memory;

	/**
	 * @throws RemoteException
	 */
	public TopicMemoryChatServer() throws RemoteException {
		super();
		memory = new HashMap<String, ArrayList<TopicMessage>>();
	}

	
	/**
	 * @see example.channel.ChatServer#login(example.channel.ChatClient)
	 */
	public void login(TopicClient newClient) throws RemoteException {
		super.login(newClient);
		
		for(String topic: newClient.getTopics()){
			if (memory.containsKey(topic)){
				for(TopicMessage message: memory.get(topic)){
					newClient.receiveMessage(message);
				}
			} else {
				memory.put(topic, new ArrayList<TopicMessage>());
			}
		}
	}


	/**
	 * Guarda el mensaje en la memoria, para que se envie a los nuevos clientes
	 * 
	 * @see example.channel.ChatServer#send(example.channel.Message)
	 */
	public void send(TopicMessage message) throws RemoteException {
		super.send(message);
		if (memory.containsKey(message.getTopic())){
			memory.get(message.getTopic()).add(message);
		} else {
			// super maneja el caso
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String helloPath = TopicMemoryChatServer.class.getResource("TopicMemoryChatServer.class").getFile();
		helloPath = helloPath.replace("/example/channel/TopicMemoryChatServer.class", "");
		String downloadLocation = "file:" + helloPath;

		String serverURL = new String("///TopicChatServer");
		System.setProperty("java.rmi.server.codebase", downloadLocation);
		
		try {
			TopicMemoryChatServer server = new TopicMemoryChatServer();
			Naming.rebind(serverURL, server);
			System.out.println("Topic Memory Server Ready!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
