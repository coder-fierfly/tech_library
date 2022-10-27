import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
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

        return dbConnection;
    }

    public int getInfo() {
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
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            System.out.println("id:" + id + "name:  " + name);
        }
        return id;
    }


    //TODO попробовать вызывать что-то из файлов)
//    public ResultSet getSpecsForProfile(int id) {
//        ResultSet resSet = null;
//        StringBuilder select = new StringBuilder();
//        select.append("SELECT * FROM ").append(Const.TAG_TABLE).append(" WHERE ").append(Const.TAG_USER).append("='").append(id).append("'");
//        System.out.println(select);
//
//        try {
//            PreparedStatement pStatement = getDbConnection().prepareStatement(String.valueOf(select));
//            resSet = pStatement.executeQuery();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return resSet;
//    }
}
