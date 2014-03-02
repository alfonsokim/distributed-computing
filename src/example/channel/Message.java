/**
 * 
 */
package example.channel;

import java.io.Serializable;

/**
 * Mensaje a enviar en el chat de canales
 * 
 * @author Alfonso Kim
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -7274647807920506124L;

	private String name;
	private String channel;
	private String text;
	
	/**
	 * Constructor del mensaje
	 * 
	 * @param name		Nombre del cliente
	 * @param channel	Nombre del canal
	 * @param text		Texto del mensaje
	 */
	public Message(String name, String channel, String text) {
		super();
		this.name = name;
		this.channel = channel;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public String getChannel() {
		return channel;
	}

	public String getText() {
		return text;
	}

}
