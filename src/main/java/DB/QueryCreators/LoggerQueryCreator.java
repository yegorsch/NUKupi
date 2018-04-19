package DB.QueryCreators;

import java.sql.Connection;

public class LoggerQueryCreator  {

    private static LoggerQueryCreator ourInstance = new LoggerQueryCreator();
    private Connection conn;

    public static LoggerQueryCreator getInstance() {
        return ourInstance;
    }

    private LoggerQueryCreator() {

    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

}