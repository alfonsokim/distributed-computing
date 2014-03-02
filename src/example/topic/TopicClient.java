/**
 * 
 */

package example.topic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import example.topic.TopicMessage;

/**
 * Interfaz del cliente basado en temas
 * 
 * @author Alfonso Kim
 */
public interface TopicClient extends Remote {


	/**
	 * @return El nombre del cliente
	 * @throws RemoteException En caso de errror
	 */
	String getName() throws RemoteException;
	
	/**
	 * @return Los temas a los que esta suscrito el cliente
	 * @throws RemoteException En caso de errror
	 */
	List<String> getTopics() throws RemoteException;
	
	
	/**
	 * Entra un nuevo cliente
	 * @param client Nombre del cliente
	 * @throws RemoteException En caso de errror
	 */
	void receiveEnter(TopicClient client) throws RemoteException;
	
	/**
	 * Se desconecta un cliente
	 * @param client El nombre del cliente desconectado
	 * @throws RemoteException En caso de errror
	 */
	void receiveExit(TopicClient client)throws RemoteException;
	
	/**
	 * @param client Mensaje Recibido
	 * @throws RemoteException	En caso de errror
	 */
	void receiveMessage(TopicMessage client) throws RemoteException;
	
	/**
	 * @param topic		Tema del mensaje
	 * @param message	Envio del mensaje
	 * @throws RemoteException	En caso de error
	 */
	void sendMessage(String topic, String message) throws RemoteException;
	
	/**
	 * Desconexion del cliente
	 * @throws RemoteException En caso de errror
	 */
	void disconnect() throws RemoteException;

	
	

}
