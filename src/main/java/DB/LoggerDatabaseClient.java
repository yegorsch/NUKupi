package DB;

import Models.Log;
import Models.LogCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoggerDatabaseClient extends DatabaseClient {

    public LoggerDatabaseClient() {
        super();
    }

    public boolean runQueryAddLog(String logId, Long logMillis, String loggerName, String logMsg) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into logger values (?, ?, ?, ?)");
            stmt.setString(1, logId);
            stmt.setLong(2, logMillis);
            stmt.setString(3, loggerName);
            stmt.setString(4, logMsg);
            int count = stmt.executeUpdate();
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public LogCollection runQueryLogsAll() {
        LogCollection logs = new LogCollection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select millis, logger_name, message from logger"
            );
            while(rs.next()) {
                logs.add(new Log(rs.getLong("millis"), rs.getString("logger_name"), rs.getString("message")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    public LogCollection runQueryLogsByFilter(String searchWord, String loggerName) {
        LogCollection logs = new LogCollection();
        try {
            String sql = "select millis, logger_name, message from logger " +
                    "where message like '%(?)%' and logger_name like '%(?)%'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(
                    "select millis, logger_name, message from logger " +
                            "where message like '%" + searchWord + "%' and logger_name like '%" + loggerName + "%'"
            );
            while(rs.next()) {
                logs.add(new Log(rs.getLong("millis"), rs.getString("logger_name"), rs.getString("message")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

}
