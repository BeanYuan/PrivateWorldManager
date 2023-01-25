package beanyuan.privateworldmanager.sql;

import beanyuan.privateworldmanager.PrivateWorldManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {

    PrivateWorldManager plugin;
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public MySql(PrivateWorldManager plugin) {
        this.plugin = plugin;
        host = plugin.getConfig().getString("MySQL.host");
        port = plugin.getConfig().getString("MySQL.port");
        database = plugin.getConfig().getString("MySQL.database");
        username = plugin.getConfig().getString("MySQL.username");
        password = plugin.getConfig().getString("MySQL.password");
    }



    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
