package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
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
    // Create a socket and connect it to the server.
    DatagramSocket socket = new DatagramSocket(propsReader.getClientPort());
    socket.connect(
        new InetSocketAddress(propsReader.getServerHostname(), propsReader.getServerPort()));

    logger.atInfo().log("Openning port: %d", socket.getLocalPort());
    logger.atInfo().log("Connecting to server: %s:%d", socket.getInetAddress().getHostName(),
        socket.getPort());
    logger.atInfo().log("%s", socket.getInetAddress().toString());

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
    new GameClient(PropertiesReader.getInstance()).start();
  }
}
