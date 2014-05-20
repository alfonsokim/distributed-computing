import java.io.*;
import java.net.*;
import java.util.*;


public class MulticastClient
{
  public static void main(String[] args)throws IOException
  {
    DatagramPacket packet;

    MulticastSocket s = new MulticastSocket(4446);
    byte[] buf = new byte[256];
    InetAddress address = InetAddress.getByName("230.10.10.10");
    s.joinGroup(address);

    packet = new DatagramPacket(buf,buf.length);
    String msg;
    while(!(msg = new String(packet.getData())).equals("quit"))
    //for(int i = 0; i<5; i++)
    {
      s.receive(packet);

      System.out.println(new StringBuilder("[")
        .append(packet.getAddress())
        .append("]:")
        .append(msg)
        .toString()
      );

      //System.out.println("Mensaje de: " + packet.getAddress() );
      //System.out.println("Mensaje: " + msg);

    }
    s.leaveGroup(address);
    s.close();
  }
}
