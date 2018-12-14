package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.google.common.flogger.FluentLogger;
import util.PropertiesReader;

public class GameServer {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  
  private final PropertiesReader propsReader;
  
  public GameServer(PropertiesReader propsReader) {
    this.propsReader = propsReader;
  }
  
  public void start() throws IOException {
    // Create a socket.
    DatagramSocket socket = new DatagramSocket(propsReader.getServerPort());
    logger.atInfo().log("Opened socket on local port: %d", socket.getLocalPort());
   
    
    boolean flag = true;
    
    while (flag) {
      byte[] buff = new byte[16];
      DatagramPacket packet = new DatagramPacket(buff, buff.length);
      socket.receive(packet);
      packet.getData();
      System.out.println(new String(buff));
      if (new String(buff).trim().equals("kill")) {
        flag = false;
      }
    }
    
    socket.close();
  }
  
  public static void main(String[] args) throws IOException {
    new GameServer(PropertiesReader.getInstance()).start();
  }
}
