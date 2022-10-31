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
        ArrayList<String> ddd = getDoc("effectivity_1");
        System.out.println("size 1 " + ddd.size());
        System.out.println(ddd.get(0));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
        ArrayList<String> dd = getDoc("effectivity_2");
        System.out.println("size 2 " + dd.size());
        System.out.println(dd.get(0));
        System.out.println(dd.get(1));
        System.out.println(dd.get(2));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
        ArrayList<String> d = getDoc("effectivity_3");
        System.out.println("size 3 " + d.size());
        System.out.println(d.get(0));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
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
            // select.append("SELECT").append(Const.PLANE_NAME).append("FROM ").append(Const.TABLE_PLANE);
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
            // System.out.println(sel);
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
        // System.out.println("name.size() " + name.size());
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
            // переделать селектер
            StringBuilder sel = new StringBuilder();
//            SELECT doc_name FROM doc
//            JOIN doc_effectivity ON doc.doc_id = doc_effectivity.doc_id
//            JOIN effectivity ON effectivity.effectivity_id = doc_effectivity.effectivity_id WHERE effectivity_name
            sel.append("SELECT ").append(Const.DOC_NAME).append(" FROM ").append(Const.TABLE_DOC).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.DOC_ID).append(" JOIN ")
                    .append(Const.TABLE_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ").append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID)
                    .append(" WHERE ").append(Const.EFF_NAME).append(" = '").append(name).append("'");
            System.out.println(sel.toString());
            resultSet = statement.executeQuery(sel.toString());
            System.out.println("!!!!!!!!!!");
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
            // System.out.println("name:  " + name);
        }
        return arrayList;
    }

//    public ArrayList<String> getSpecsForProfile(int id) {
//        ResultSet resSet = null;
//        StringBuilder select = new StringBuilder();
//        select.append("SELECT ").append(Const.EFF_NAME).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
//                .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
//                .append(" = ")
//                .append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID)
//                .append(" WHERE ").append(Const.DOC_ID).append(" = ").append(id);
////       /*SELECT effectivity_name
////FROM effectivity
////JOIN doc_effectivity ON effectivity.effectivity_id = doc_effectivity.effectivity_id
////WHERE  doc_id = 1*/
//        //SELECT effectivity_name FROM effectivity JOIN doc_effectivity ON effectivity.effectivity_id = doc_effectivity.effectivity_id WHERE doc_id = 1
//        System.out.println(select);
//        System.out.println("lzlzlzlz");
//
//        try {
//            PreparedStatement pStatement = getDbConnection().prepareStatement(String.valueOf(select));
//            resSet = pStatement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        ArrayList<String> name = new ArrayList<>();
//        while (true) {
//            try {
//                if (!resSet.next()) {
//                    System.out.println("!resSet.next()");
//                    break;
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                name.add(resSet.getString(2));
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return name;
//    }


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
