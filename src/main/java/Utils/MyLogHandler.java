package Utils;

import com.google.gson.Gson;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyLogHandler extends Handler {

    @Override
    public void publish(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        if (record.getMessage().contains("request") || record.getMessage().contains("response")) {
            String s = new Gson().toJson(record);
            System.out.println(s);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}