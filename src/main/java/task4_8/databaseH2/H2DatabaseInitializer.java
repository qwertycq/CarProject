package task4_8.databaseH2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class H2DatabaseInitializer {
    String jdbcUrl;
    String username;
    String password;

    public H2DatabaseInitializer(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void init() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Подключение к базе данных H2 успешно!");

            executeSqlScript(connection, "/db/schema.sql");
            executeSqlScript(connection, "/db/data.sql");

            System.out.println("Скрипты выполнены успешно!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Подключение к базе данных H2 успешно!");

            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP ALL OBJECTS DELETE FILES");
                System.out.println("Все объекты в базе данных были успешно удалены.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private void executeSqlScript(Connection connection, String scriptPath) {
        try (InputStream is = getClass().getResourceAsStream(scriptPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            try (Statement statement = connection.createStatement()) {
                System.out.println("Выполняется SQL: " + sql.toString());
                statement.execute(sql.toString());
                System.out.println("Скрипт " + scriptPath + " выполнен успешно.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка выполнения скрипта: " + scriptPath, e);
        }
    }

}
