import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public void getDbConnection() throws SQLException {
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
    }

    //может потом переписать для общего досавания или ни....
    public ArrayList<String> getName(String table) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
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

    public Storage getPlaneByEff(int i) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            StringBuilder sel = new StringBuilder();

//           SELECT plane_name, plane.plane_id
//     FROM plane
//      JOIN plane_effectivity ON plane.plane_id = plane_effectivity.plane_id
//--      JOIN effectivity e on e.effectivity_id = plane_effectivity.effectivity_id
//     WHERE effectivity_id = '1'
            sel.append("SELECT ").append(Const.PLANE_NAME).append(", ").append(Const.TABLE_PLANE).append(".")
                    .append(Const.PLANE_ID).append(" FROM ").append(Const.TABLE_PLANE).append(" JOIN ")
                    .append(Const.TABLE_PLANE_EFF).append(" ON ").append(Const.TABLE_PLANE).append(".")
                    .append(Const.PLANE_ID).append(" = ")
                    .append(Const.TABLE_PLANE_EFF).append(".").append(Const.PLANE_ID)
                    .append(" WHERE ").append(Const.EFF_ID).append(" = '").append(i).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Storage stor = null;
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                stor = (new Storage(resultSet.getInt(2), resultSet.getString(1)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return stor;
    }


    // по id документа вынимаю эффективити
    public ArrayList<String> getEffNameByPlaneId(int id) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
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

    public ArrayList<Integer> getEffIdByPlaneId(int id) {
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
            sel.append("SELECT ").append(Const.TABLE_PLANE_EFF).append(".").append(Const.EFF_ID).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
                    .append(Const.TABLE_PLANE_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ")
                    .append(Const.TABLE_PLANE_EFF).append(".").append(Const.EFF_ID)
                    .append(" WHERE ").append(Const.PLANE_ID).append(" = '").append(id).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArray(resultSet);
    }

    public Storage getEffNameIdByDocId(int id) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.EFF_NAME).append(", ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID).append(" FROM ").append(Const.TABLE_EFF).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_EFF).append(".").append(Const.EFF_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.EFF_ID).append(" JOIN ")
                    .append(Const.TABLE_DOC).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ").append(Const.TABLE_DOC_EFF).append(".").append(Const.DOC_ID)
                    .append(" WHERE ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = '").append(id).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Storage storage = null;
        Map<Integer, String> name = new HashMap<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                storage = (new Storage(resultSet.getInt(2), resultSet.getString(1)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return storage;
    }

    // файл по имени эффективити
    public ArrayList<String> getDocByEffName(String name) {
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

    public ArrayList<Integer> getDocIdByEffId(int num) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
       /*   SELECT doc.doc_id
            FROM doc
            JOIN doc_effectivity ON doc.doc_id = doc_effectivity.doc_id
            WHERE effectivity_id = 'n' */
            sel.append("SELECT ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID).append(" FROM ")
                    .append(Const.TABLE_DOC).append(" JOIN ")
                    .append(Const.TABLE_DOC_EFF).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ")
                    .append(Const.TABLE_DOC_EFF).append(".").append(Const.DOC_ID)
                    .append(" WHERE ").append(Const.EFF_ID).append(" = '").append(num).append("'");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArray(resultSet);
    }

    //нахождение файла по слову из него
    public Map<Integer, String> findDocByText(String scan) {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            StringBuilder sel = new StringBuilder();
            sel.append("SELECT ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID).append(", ").append(Const.DOC_NAME).append(" FROM ").append(Const.TABLE_TEXT).append(" JOIN ")
                    .append(Const.TABLE_DOC).append(" ON ").append(Const.TABLE_DOC).append(".").append(Const.DOC_ID)
                    .append(" = ")
                    .append(Const.TABLE_TEXT).append(".").append(Const.DOC_ID)
                    .append(" WHERE to_tsvector(").append(Const.DOC_TEXT).append(") @@ to_tsquery('").append(scan).append("');");
            resultSet = statement.executeQuery(sel.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, String> map = new HashMap<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                map.put(resultSet.getInt(1), resultSet.getString(2));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    public ArrayList<Integer> resultToArray(ResultSet resultSet){
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                arrayList.add(resultSet.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return arrayList;
    }
}
