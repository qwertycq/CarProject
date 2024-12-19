package task1_8.databaseH2;

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
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Подключение к H2 выполнено!");

            loadSqlScript(conn, "/db/schema.sql");
            loadSqlScript(conn, "/db/data.sql");

            System.out.println("Скрипты успешно обработаны!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void drop() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Соединение с H2 установлено!");

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP ALL OBJECTS DELETE FILES");
                System.out.println("База данных успешно очищена.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private void loadSqlScript(Connection conn, String filePath) {
        try (InputStream input = getClass().getResourceAsStream(filePath);
             BufferedReader bufReader = new BufferedReader(new InputStreamReader(input))) {

            StringBuilder scriptContent = new StringBuilder();
            String currentLine;
            while ((currentLine = bufReader.readLine()) != null) {
                scriptContent.append(currentLine).append("\n");
            }

            try (Statement stmt = conn.createStatement()) {
                System.out.println("Запуск SQL: " + scriptContent);
                stmt.execute(scriptContent.toString());
                System.out.println("Скрипт " + filePath + " выполнен успешно.");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка выполнения скрипта: " + filePath, ex);
        }
    }
}
