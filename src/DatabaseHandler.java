import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        System.out.println("@@@@@@@@@@@@");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager
                    .getConnection(dbUrl, dbName, dbPass);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (dbConnection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        System.out.println(getInfo() + "фывфыв");

        return dbConnection;
    }
    public int getInfo(){
        Statement statement = null;
        int id = 0;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO записать сюда поиск по файлам))
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * FROM plane");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("___________");
        while(true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            try {
                id = resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String name = null;
            try {
                name = resultSet.getString(2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(id + "  " + name);
        }
        return id;
    }
}
