/**
 * 
 */
package example.topic;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz del servidor basado en temas
 * 
 * @author Alfonso Kim
 */
public interface TopicServer extends Remote {
	
	/**
	 * Login de un nuevo cliente
	 * 
	 * @param client
	 * @throws RemoteException
	 */
	void login(TopicClient client) throws RemoteException;
	
	/**
	 * Logout de un nuevo cliente
	 * 
	 * @param client
	 * @throws RemoteException
	 */
	void logout(TopicClient client) throws RemoteException;
	
	/**
	 * Envio del mensaje de un cliente
	 * 
	 * @param message
	 * @throws RemoteException
	 */
	void send(TopicMessage message) throws RemoteException;

}
