/**
 * 
 */
package example.channel;

import java.rmi.RemoteException;

import example.channel.Message;

/**
 * Interfaz del cliente remoto
 * @author Alfonso Kim
 */
public interface ChatClient extends java.rmi.Remote {
	
	/**
	 * @return El nombre del cliente
	 * @throws RemoteException En caso de errror
	 */
	String getName() throws RemoteException;
	
	/**
	 * @return El nombre del canal al que esta conectado
	 * @throws RemoteException En caso de errror
	 */
	String getChannel() throws RemoteException;
	
	
	/**
	 * Entra un nuevo cliente
	 * @param name Nombre del cliente
	 * @throws RemoteException En caso de errror
	 */
	void receiveEnter(String name) throws RemoteException;
	
	/**
	 * Se desconecta un cliente
	 * @param name El nombre del cliente desconectado
	 * @throws RemoteException En caso de errror
	 */
	void receiveExit(String name)throws RemoteException;
	
	/**
	 * @param message Mensaje Recibido
	 * @throws RemoteException	En caso de errror
	 */
	void receiveMessage(Message message) throws RemoteException;
	
	/**
	 * @param message	Envio del mensaje
	 * @throws RemoteException	En caso de error
	 */
	void sendMessage(String message) throws RemoteException;
	
	/**
	 * Desconexion del cliente
	 * @throws RemoteException En caso de errror
	 */
	void disconnect() throws RemoteException;

}
