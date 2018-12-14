package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.Scanner;
import com.google.common.flogger.FluentLogger;
import util.PropertiesReader;

public class GameClient {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  
  private final PropertiesReader propsReader;

  public GameClient(PropertiesReader propsReader) {
    this.propsReader = propsReader;
  }

  public void start() throws IOException {
    // Create a socket.
    DatagramSocket socket = new DatagramSocket(propsReader.getClientPort());
    logger.atInfo().log("Opened socket on local port: %d", socket.getLocalPort());

    // Connect to server.
    String serverHostName = propsReader.getServerHostName();
    int serverPort = propsReader.getServerPort();

    logger.atInfo().log("Connecting to server: %s:%d ...", serverHostName, serverPort);
    socket.connect(new InetSocketAddress(serverHostName, serverPort));
    logger.atInfo().log("Connected");

    Scanner sc = new Scanner(System.in);

    boolean flag = true;

    while (flag) {
      String line = sc.nextLine();
      DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length);
      socket.send(packet);
    }

    sc.close();
    socket.close();
  }

  public static void main(String[] args) throws IOException {
    Locale.setDefault(Locale.ENGLISH);
    
    new GameClient(PropertiesReader.getInstance()).start();
  }
}
