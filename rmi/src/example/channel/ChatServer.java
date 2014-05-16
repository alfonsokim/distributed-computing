/**
 * 
 */
package example.channel;

import java.rmi.Remote;
import java.rmi.RemoteException;

import example.channel.ChatClient;
import example.channel.Message;

/**
 * Interfaz del Servidor de Chat basado en canales
 * 
 * @author Alfonso Kim
 */
public interface ChatServer extends Remote {
	
	/**
	 * Nuevo cliente
	 * @param newClient			Cliente recien loggead
	 * @throws RemoteException	En caso de error
	 */
	void login(ChatClient newClient) throws RemoteException;
	
	/**
	 * Cliente se sale
	 * @param client			Cliente que se va
	 * @throws RemoteException	En caso de error
	 */
	void logout(ChatClient client) throws RemoteException;
	
	/**
	 * Envio del mensaje
	 * @param message			Mensaje a enviar en la red
	 * @throws RemoteException	EN caso de error
	 */
	void send(Message message) throws RemoteException;

}
