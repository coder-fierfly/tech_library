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

    public String getPlaneByEff(String eff){
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.PLANE_NAME).append(" FROM ").append(Const.TABLE_PLANE).append(" JOIN ")
                    .append(Const.TABLE_PLANE_EFF).append(" ON ").append(Const.TABLE_PLANE).append(".").append(Const.PLANE_ID)
                    .append(" = ")
                    .append(Const.TABLE_PLANE_EFF).append(".").append(Const.PLANE_ID)
                    .append(" WHERE ").append(Const.EFF_NAME).append(" = '").append(eff).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String name = null;
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                name = resultSet.getString(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return name;
    }

    // по id документа вынимаю эффективити
    public ArrayList<String> getEff(int id) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            //SELECT effectivity_name
            //FROM effectivity JOIN plane_effectivity ON effectivity.effectivity_id = plane_effectivity.effectivity_id
            //WHERE plane_id = '2'
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.EFF_NAME).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
                    .append(Const.TABLE_PLANE_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ")
                    .append(Const.TABLE_PLANE_EFF).append(".").append(Const.EFF_ID)
                    .append(" WHERE ").append(Const.PLANE_ID).append(" = '").append(id).append("'");
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

    // по имени документа получаю эффеттивити
    //TODO проверирть работоспособность
    public String getEff(String str) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            //TODO
            // JOIN doc d on d.doc_id = doc_effectivity.doc_id


            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.EFF_NAME).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID).append(" JOIN ")
                    .append(Const.TABLE_DOC).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ").append(Const.TABLE_DOC_EFF).append(".").append(Const.DOC_ID)
                    .append(" WHERE ").append(Const.DOC_NAME).append(" = '").append(str).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String name = null;
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                name = resultSet.getString(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return name;
    }

    // файл по имени эффективити
    public ArrayList<String> getDoc(String name) {
        Statement statement = null;
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

    //нахождение файла по слову из него
    public ArrayList<String> findDocByText(String scan) {
        Statement statement = null;
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
}
