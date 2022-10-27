import java.sql.*;
import java.util.ArrayList;

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
        ArrayList<String> asd = getName();
        System.out.println(asd.size());
        System.out.println(asd.get(0));
        System.out.println(asd.get(1));
        System.out.println(asd.get(2));
     //   System.out.println(getSpecsForProfile());
        return dbConnection;
    }

    //может потом переписать для общего досавания или ни....
    public ArrayList<String> getName() {
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
        ArrayList<String> name = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                name.add(resultSet.getString(2));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // System.out.println("id:" + id + "name:  " + name);
        }
        return name;
    }


    //TODO попробовать вызывать что-то из файлов)
    public ResultSet getSpecsForProfile() {
        ResultSet resSet = null;
        StringBuilder select = new StringBuilder();
        select.append("SELECT").append(Const.PLANE_NAME).append("FROM ").append(Const.TABLE_PLANE);
        System.out.println(select);

        try {
            PreparedStatement pStatement = getDbConnection().prepareStatement(String.valueOf(select));
            resSet = pStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    //TODO сделать выбор по эффективити
    //todo сдеать поиск по словам из файлов и вывод данных файлов
}
