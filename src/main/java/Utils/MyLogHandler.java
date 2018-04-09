package Utils;

import DB.LoggerDatabaseClient;
import com.google.gson.Gson;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyLogHandler extends Handler {

    private LoggerDatabaseClient dlc = new LoggerDatabaseClient();

    @Override
    public void publish(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        if (record.getMessage().contains("request") || record.getMessage().contains("response")) {
            Long millis = record.getMillis();
            String loggerName = record.getLoggerName();
            String message = record.getMessage();
            message = message.substring(0, message.length()-1).replace("\n", "; ");
            System.out.println(message);
            String sId = UniqueStringGenerator.generateIDWithDefaultLength();
            if(dlc.runQueryAddLog(sId,millis,loggerName,message)) {
                System.out.println("Success");
            }
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}