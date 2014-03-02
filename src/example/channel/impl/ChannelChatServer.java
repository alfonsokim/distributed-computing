package example.channel.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.channel.ChatClient;
import example.channel.ChatServer;
import example.channel.Message;

/**
 * Implementacion del servidor
 * @author Alfonso Kim
 */
public class ChannelChatServer extends UnicastRemoteObject implements ChatServer {

	private static final long serialVersionUID = 1122334455;
	
	private Map<String, ArrayList<ChatClient>> channels;

	/**
	 * Constructor del servidor
	 * @throws RemoteException	En caso de errror
	 */
	protected ChannelChatServer() throws RemoteException {
		super();
		channels = new HashMap<String, ArrayList<ChatClient>>();
	}

	/**
	 * @see example.channel.ChatServer#login()
	 */
	@Override
	public void login(ChatClient newClient) throws RemoteException {
		if (channels.containsKey(newClient.getChannel())){
			List<ChatClient> clients = channels.get(newClient.getChannel());
			for(ChatClient client : clients){
				client.receiveEnter(newClient.getName());
			}
			clients.add(newClient);
		} else {
			ArrayList<ChatClient> clients = new ArrayList<ChatClient>();
			clients.add(newClient);
			channels.put(newClient.getChannel(), clients);
		}
		System.out.println("client " + newClient.getName() + 
				" logged to " + newClient.getChannel());
	}

	/**
	 * @see example.channel.ChatServer#logout()
	 */
	@Override
	public void logout(ChatClient client) throws RemoteException {
		if (channels.containsKey(client.getChannel())){
			List<ChatClient> clients = channels.get(client.getChannel());
			clients.remove(client);
			System.out.println("client " + client.getName() + 
					" has logged out from " + client.getChannel());	
		} else {
			System.out.println("unrecognized channel: " + client.getChannel());
		}
	}

	/**
	 * @see example.channel.ChatServer#send()
	 */
	@Override
	public void send(Message message) throws RemoteException {
		if (channels.containsKey(message.getChannel())){
			ArrayList<ChatClient> clients = channels.get(message.getChannel());
			for(ChatClient client: clients){
				if(!client.getName().equals(message.getName())){ // no enviar al originador
					client.receiveMessage(message);
				}
			}
		} else {
			System.out.println("unrecognized channel: " + message.getChannel());
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String helloPath = ChannelChatServer.class.getResource("ChannelChatServer.class").getFile();
		helloPath = helloPath.replace("/example/channel/ChannelChatServer.class", "");
		String downloadLocation = "file:" + helloPath;

		String serverURL = new String("///ChannelChatServer");
		System.setProperty("java.rmi.server.codebase", downloadLocation);
		
		try {
			ChannelChatServer server = new ChannelChatServer();
			Naming.rebind(serverURL, server);
			System.out.println("Channel Server Ready!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

}
