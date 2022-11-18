import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    String nameStr = "_name";
    String idStr = "_id";

    public void getDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbName, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dbConnection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    //
    public ArrayList<String> getName(String str) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            //SELECT doc_name FROM doc
            String sel = "SELECT " + str + nameStr+ " FROM " + str;
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArrayStr(resultSet, 1);
    }

    public int getId(String str, String table) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            //SELECT plane_id FROM plane WHERE plane_name = 'plane1'
            String sel = "SELECT " + table + idStr + " FROM " + table + " WHERE " + table
                    + nameStr + " ='" + str + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToInt(resultSet);
    }

    // название самолета по эффективити
    public Storage getPlaneByEff(int i) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
//           SELECT plane_name, plane.plane_id
//     FROM plane
//      JOIN plane_effectivity ON plane.plane_id = plane_effectivity.plane_id
//--      JOIN effectivity e on e.effectivity_id = plane_effectivity.effectivity_id
//     WHERE effectivity_id = '1'
            String sel = "SELECT " + Const.PLANE_NAME + ", " + Const.TABLE_PLANE + "." +
                    Const.PLANE_ID + " FROM " + Const.TABLE_PLANE + " JOIN " +
                    Const.TABLE_PLANE_EFF + " ON " + Const.TABLE_PLANE + "." +
                    Const.PLANE_ID + " = " +
                    Const.TABLE_PLANE_EFF + "." + Const.PLANE_ID +
                    " WHERE " + Const.EFF_ID + " = '" + i + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToStorage(resultSet);
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
            String sel = "SELECT " + Const.EFF_NAME + " FROM " + Const.TABLE_EFF + " JOIN " +
                    Const.TABLE_PLANE_EFF + " ON " + Const.TABLE_EFF + "." + Const.EFF_ID +
                    " = " +
                    Const.TABLE_PLANE_EFF + "." + Const.EFF_ID +
                    " WHERE " + Const.PLANE_ID + " = '" + id + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArrayStr(resultSet, 1);
    }

    // id эффективити по id самолета
    public ArrayList<Integer> getEffIdByPlaneId(int id) {
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
            String sel = "SELECT " + Const.TABLE_PLANE_EFF + "." + Const.EFF_ID + " FROM " + Const.TABLE_EFF + " JOIN " +
                    Const.TABLE_PLANE_EFF + " ON " + Const.TABLE_EFF + "." + Const.EFF_ID +
                    " = " +
                    Const.TABLE_PLANE_EFF + "." + Const.EFF_ID +
                    " WHERE " + Const.PLANE_ID + " = '" + id + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArrayInt(resultSet);
    }

    // название эффективити по id дока
    public Storage getEffNameIdByDocId(int id) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            String sel = "SELECT " + Const.EFF_NAME + ", " + Const.TABLE_EFF + "." + Const.EFF_ID
                    + " FROM " + Const.TABLE_EFF + " JOIN " + Const.TABLE_DOC_EFF + " ON "
                    + Const.TABLE_EFF + "." + Const.EFF_ID + " = " + Const.TABLE_DOC_EFF + "."
                    + Const.EFF_ID + " JOIN " + Const.TABLE_DOC + " ON " + Const.TABLE_DOC + "."
                    + Const.DOC_ID + " = " + Const.TABLE_DOC_EFF + "." + Const.DOC_ID +
                    " WHERE " + Const.TABLE_DOC + "." + Const.DOC_ID + " = '" + id + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToStorage(resultSet);
    }

    // файл по имени эффективити
    public ArrayList<String> getDocByEffName(String name) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            String sel = "SELECT " + Const.DOC_NAME + " FROM " + Const.TABLE_DOC + " JOIN " +
                    Const.TABLE_DOC_EFF + " ON " + Const.TABLE_DOC + "." + Const.DOC_ID + " = " +
                    Const.TABLE_DOC_EFF + "." + Const.DOC_ID + " JOIN " + Const.TABLE_EFF + " ON "
                    + Const.TABLE_EFF + "." + Const.EFF_ID + " = " + Const.TABLE_DOC_EFF + "."
                    + Const.EFF_ID + " WHERE " + Const.EFF_NAME + " = '" + name + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArrayStr(resultSet, 1);
    }

    // id документа по id эффективити
    public ArrayList<Integer> getDocIdByEffId(int num) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            /*   SELECT doc.doc_id
            FROM doc
            JOIN doc_effectivity ON doc.doc_id = doc_effectivity.doc_id
            WHERE effectivity_id = 'n' */
            String sel = "SELECT " + Const.TABLE_DOC + "." + Const.DOC_ID + " FROM " +
                    Const.TABLE_DOC + " JOIN " +
                    Const.TABLE_DOC_EFF + " ON " + Const.TABLE_DOC + "." + Const.DOC_ID +
                    " = " +
                    Const.TABLE_DOC_EFF + "." + Const.DOC_ID +
                    " WHERE " + Const.EFF_ID + " = '" + num + "'";
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToArrayInt(resultSet);
    }

    //нахождение файла по слову из него
    public Map<Integer, String> findDocByText(String scan) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        try {
            String sel = "SELECT " + Const.TABLE_DOC + "." + Const.DOC_ID + ", " + Const.DOC_NAME + " FROM " + Const.TABLE_TEXT + " JOIN " +
                    Const.TABLE_DOC + " ON " + Const.TABLE_DOC + "." + Const.DOC_ID +
                    " = " +
                    Const.TABLE_TEXT + "." + Const.DOC_ID +
                    " WHERE to_tsvector(" + Const.DOC_TEXT + ") @@ to_tsquery('" + scan + "');";
            resultSet = statement.executeQuery(sel);
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

    // добавление нового пользователя
    public void addNewUser(String name, String surname, String login, String pas, boolean bool) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert;
        insert = "INSERT INTO " + Const.TABLE_USERS + "(" + Const.USER_NAME + "," + Const.USER_SURNAME + ","
                + Const.USER_LOG + "," + Const.USER_PASS + "," + Const.USER_ADMIN + ")" + "VALUES('" + name + "','"
                + surname + "','" + login + "','" + pas + "'," + bool + ")";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bundlePlaneEff(int planeId, int effId) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert;
        //INSERT INTO plane_effectivity (plane_id, effectivity_id) VALUES (3,5)
        insert = "INSERT INTO " + Const.TABLE_PLANE_EFF + " (" + Const.PLANE_ID + "," + Const.EFF_ID + ")" +
                "VALUES(" + planeId + "," + effId + ")";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bundleEffDoc(int effId, int docId) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //
        String insert = "INSERT INTO " + Const.TABLE_DOC_EFF + " (" + Const.EFF_ID + "," + Const.DOC_ID + ")" +
                " VALUES(" + effId + "," + docId + ")";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bundleTextOnDoc(int docId, String text) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //
        String insert = "INSERT INTO " + Const.TABLE_TEXT + " (" + Const.DOC_ID + "," + Const.DOC_TEXT + ")" +
                " VALUES(" + docId + ",'" + text + "')";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNew(String name, String table) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert;
        insert = "INSERT INTO " + table + "(" + table + nameStr + ")" + " VALUES('" + name + "')";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addNewGetId(String name, String table) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert;
        ResultSet resultSet;
        //INSERT into doc (doc_name) VALUES('asd'); SELECT lastval();
        insert = "INSERT INTO " + table + "(" + table + nameStr + ")" + " VALUES('" + name + "')";
        String sel = "SELECT " + "lastval();";
        try {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultToInt(resultSet);
    }

    // проверка соответствия логина и пароля бд
    public boolean checkLogPass(String login, String password) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        //SELECT user_log FROM users WHERE user_log = 'firefly' AND users.pass = 'pass'
        String sel = "SELECT " + Const.USER_LOG + " FROM " + Const.TABLE_USERS +
                " WHERE " + Const.USER_LOG + " = '" + login + "' AND " + Const.USER_PASS + " = '" + password + "'";
        try {
            resultSet = statement.executeQuery(sel);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // проверка на то существует ли такой логин в базе или нет
    public boolean checkLogin(String login) {
        ResultSet resultSet = result(login);
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //проверка на то является ли пользователь администратором
    public boolean checkAdminBool(String login) {
        ResultSet resultSet = result(login);
        Boolean bool = null;

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                bool = resultSet.getBoolean(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return bool;
    }

    public ArrayList<String> resultToArrayStr(ResultSet resultSet, int rows) {
        ArrayList<String> list = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                list.add(resultSet.getString(rows));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public int resultToInt(ResultSet resultSet){
        int id = -1;
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
        }
        return id;
    }
    public ArrayList<Integer> resultToArrayInt(ResultSet resultSet) {
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

    public Storage resultToStorage(ResultSet resultSet) {
        Storage storage = null;
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

    public ResultSet result(String str) {
        Statement statement;
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet;
        //SELECT user_admin FROM users WHERE user_log = 'firefly'
        String sel = "SELECT " + Const.USER_ADMIN + " FROM " + Const.TABLE_USERS +
                " WHERE " + Const.USER_LOG + " = '" + str + "'";
        try {
            resultSet = statement.executeQuery(sel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}
