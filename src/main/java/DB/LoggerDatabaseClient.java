package DB;

import DB.QueryCreators.LoggerQueryCreator;
import Models.Log;
import Models.LogCollection;

import java.sql.*;

public class LoggerDatabaseClient extends DatabaseClient {

    public LoggerDatabaseClient() { LoggerQueryCreator.getInstance().setConnection(conn); }

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

    public LogCollection runQueryLogsByFilter(String message, String loggerName) {
        LogCollection loggers = new LogCollection();

        try {
            String query = "SELECT  millis, logger_name, message " +
                            "FROM logger\n" +
                            "WHERE \n" +
                            "    (? IS NULL OR message like ?)\n" +
                            "AND \n" +
                            "    (? IS NULL OR logger_name like ?)\n " +
                            "limit " + 0 + "," + 50 + "\n";

            PreparedStatement stmt = conn.prepareStatement(query);
            if (message == null || message.length() == 0) {
                stmt.setNull(1, Types.VARCHAR);
                stmt.setNull(2, Types.VARCHAR);
            } else {
                String t = "%" + message + "%";
                stmt.setString(1, t);
                stmt.setString(2, t);
            }

            if (loggerName == null || loggerName.length() == 0) {
                stmt.setNull(3, Types.VARCHAR);
                stmt.setNull(4, Types.VARCHAR);
            } else {
                String c = "%" + loggerName + "%";
                stmt.setString(3, c);
                stmt.setString(4, c);
            }

            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();

            fillLogger(loggers, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loggers;
    }

    private void fillLogger(LogCollection loggers, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Log lg = new Log(rs.getLong("millis"), rs.getString("logger_name"),
                    rs.getString("message"));

            loggers.add(lg);
        }
    }
}