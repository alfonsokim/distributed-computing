import sun.net.*;
import java.net.*;
import java.io.*;


public class MulticastServer
{

  public static void sendMsg(String msg, String address)throws IOException
  {
    byte[] buf = new byte[256];
    DatagramPacket packet;

    MulticastSocket s = new MulticastSocket();
    //s.setNetworkInterface(NetworkInterface.getByName("vmnet8"));

    buf = msg.getBytes();

    //Definiendo el grupo de multicast destinatario
    InetAddress group = InetAddress.getByName(address);

    packet = new DatagramPacket(buf,buf.length, group, 4446);
    s.send(packet);
    s.close();

  }
  public static void main(String[] args) throws IOException
  {
    String msg = "Este es el mensaje";

    try
    {
      sendMsg(msg,args[0]);
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }

  }

}
