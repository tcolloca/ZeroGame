package util;

import org.filemanager.core.Properties;

public class PropertiesReader {

  private static final String CONFIG_FILENAME = "config.properties";
  private static final String CONNECTION = "CONNECTION";
  private static final String SERVER_HOSTNAME = "server_hostname";
  private static final String SERVER_PORT = "server_port";
  private static final String CLIENT_PORT = "client_port";
  
  private static PropertiesReader instance;
  private final Properties props;
  
  private PropertiesReader() {
    this.props = new Properties(CONFIG_FILENAME);
  }
  
  public static PropertiesReader getInstance() {
    if (instance == null) {
      instance = new PropertiesReader();
    } 
    return instance;
  }
  
  public int getServerPort() {
    return props.getInt(CONNECTION, SERVER_PORT);
  }
  
  public int getClientPort() {
   return props.getInt(CONNECTION, CLIENT_PORT);
  }
  
  public String getServerHostname() {
    return props.getString(CONNECTION, SERVER_HOSTNAME);
  }
}
