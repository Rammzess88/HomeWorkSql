import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestSql {

    @Test
    public void checkAddAndDeleteProductFromFoodTable() throws SQLException {

        List<String> food = Arrays.asList("5", "Ананас", "FRUIT", "1");

        Connection connection = SqlMethods.getConnection();
        Map responseSql = SqlMethods.getSqlResponseSelect(connection, "SELECT * FROM food");

        Assert.assertEquals(
                "Количество строк в таблице не равно 4",
                4,
                responseSql.size());

        SqlMethods.getSqlResponse(connection, "INSERT INTO food VALUES ('" + food.get(0) + "', '" + food.get(1) + "','" + food.get(2) + "','" + food.get(3) + "')");
        responseSql = SqlMethods.getSqlResponseSelect(connection, "SELECT * FROM food");
        Assert.assertEquals(
                "Количество строк в таблице не равно 5",
                5,
                responseSql.size());
        Assert.assertTrue(
                "В таблице отсутствует добавленная строка",
                responseSql.containsValue(food));

        SqlMethods.getSqlResponse(connection, "DELETE FROM food WHERE FOOD_ID= " + food.get(0) + "");
        responseSql = SqlMethods.getSqlResponseSelect(connection, "SELECT * FROM food");
        Assert.assertEquals("Количество строк в таблице не равно 4",
                responseSql.size(),
                4);
        Assert.assertFalse(
                "В таблице присутствует добавленная ранее строка",
                responseSql.containsValue(food));

        SqlMethods.closeConnection(connection);
    }
}
