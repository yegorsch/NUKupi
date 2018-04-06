package Models;

public class Log {
    public long millis;
    public String loggerName;
    public String message;

    public Log(long millis, String loggerName, String message) {
        this.millis = millis;
        this.loggerName = loggerName;
        this.message = message;
    }
}
