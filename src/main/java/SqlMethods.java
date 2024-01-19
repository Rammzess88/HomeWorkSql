import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlMethods {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public static Map getSqlResponseSelect(Connection connection, String sqlRequest) throws SQLException {
        Map<Integer, List<String>> resultMap = new HashMap<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlRequest);
        Integer count = 0;
        while (result.next()) {
            count++;
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                if (!resultMap.containsKey(count)) {
                    resultMap.put(count, new ArrayList<>());
                }
                resultMap.get(count).add(result.getString(i));
            }
        }
        return resultMap;
    }

    public static void getSqlResponse(Connection connection, String sqlRequest) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlRequest);
    }
}
