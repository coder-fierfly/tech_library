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
            dbConnection = DriverManager.getConnection(dbUrl, dbName, dbPass);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (dbConnection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        System.out.println("DBDBDBDB");
        ArrayList<String> ddd = findDoc("hol pol");
        System.out.println("size 1 " + ddd.size());
        System.out.println(ddd.get(0));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
//        ArrayList<String> dd = getDoc("effectivity_2");
//        System.out.println("size 2 " + dd.size());
//        System.out.println(dd.get(0));
//        System.out.println(dd.get(1));
//        System.out.println(dd.get(2));
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
//        ArrayList<String> d = getDoc("effectivity_3");
//        System.out.println("size 3 " + d.size());
//        System.out.println(d.get(0));
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(asd.get(2));
//           System.out.println(getSpecsForProfile());
        return dbConnection;
    }

    //может потом переписать для общего досавания или ни....
    public ArrayList<String> getName(String table) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO записать сюда поиск по файлам))
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * FROM " + table);
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
        }
        return name;
    }

    public ArrayList<String> getEff(int id) {
        Statement statement = null;
        System.out.println();
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.EFF_NAME).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID)
                    .append(" WHERE ").append(Const.DOC_ID).append(" = '").append(id).append("'");
            resultSet = statement.executeQuery(sel.toString());
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
                name.add(resultSet.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return name;
    }

    public ArrayList<String> getDoc(String name) {
        Statement statement = null;
        System.out.println("getDoc");
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.DOC_NAME).append(" FROM ").append(Const.TABLE_DOC).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.DOC_ID).append(" JOIN ")
                    .append(Const.TABLE_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ").append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID)
                    .append(" WHERE ").append(Const.EFF_NAME).append(" = '").append(name).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                arrayList.add(resultSet.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return arrayList;
    }

    public ArrayList<String> findDoc(String scan) {
        Statement statement = null;
        System.out.println("findDoc");
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.DOC_NAME).append(" FROM ").append(Const.TABLE_TEXT).append(" JOIN ")
                    .append(Const.TABLE_DOC).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ")
                    .append(Const.TABLE_TEXT).append(".").append(Const.DOC_ID)
                    .append(" WHERE to_tsvector(").append(Const.DOC_TEXT).append(") @@ to_tsquery('").append(scan).append("');");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                arrayList.add(resultSet.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return arrayList;
    }


    // попробовать вызывать что-то из файлов)
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

    //todo сдеать поиск по словам из файлов и вывод данных файлов
}
