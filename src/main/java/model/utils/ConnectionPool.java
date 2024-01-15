package model.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Connection pool.
 */
public class ConnectionPool {
    private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<Connection>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found:"+ e.getMessage());
        }
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = null;
        final String DB_URL_with_SSL = System.getenv("DB_LINK");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        newConnection = DriverManager.getConnection(DB_URL_with_SSL, username, password);

        newConnection.setAutoCommit(true);
        return newConnection;
    }


    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            connection = (Connection) freeDbConnections.get(0);
            freeDbConnections.remove(0);

            try {
                if (connection.isClosed())
                    connection = getConnection();
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
            }
        } else {
            connection = createDBConnection();
        }

        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     * @throws SQLException the sql exception
     */
    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if(connection != null) freeDbConnections.add(connection);
    }
}