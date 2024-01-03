package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {
    private static ConnessioneDB instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "07092021";
    private String url = "jdbc:postgresql://localhost:5432/wikipedia";
    private String driver = "org.postgresql.Driver";

    private ConnessioneDB() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Connessione al Database fallita : " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static ConnessioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDB();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDB();
        }
        return instance;
    }//todo creare metodo chiusura connessione, e che rimette l instanza uguale a null, cosi che nella prossima get instance viene di nuovo creata
}
