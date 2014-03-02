/**
 * 
 */
package example.topic;

import java.io.Serializable;

/**
 * Mensaje basado en temas
 * 
 * @author Alfonso Kim
 */
public class TopicMessage implements Serializable {

	private static final long serialVersionUID = -171559654405882313L;

	private String name;
	private String topic;
	private String text;
	
	/**
	 * Mensaje basado en temas
	 * @param name	Nombre de quien envia el mensaje
	 * @param topic	Tema del mensaje
	 * @param text	Contenido del mensaje
	 */
	public TopicMessage(String name, String topic, String text) {
		super();
		this.name = name;
		this.topic = topic;
		this.text = text;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}
