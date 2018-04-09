package DB.QueryCreators;

import java.sql.Connection;

public class ImageQueryCreator {
    private static ImageQueryCreator ourInstance = new ImageQueryCreator();
    private Connection conn;

    public static ImageQueryCreator getInstance() {
        return ourInstance;
    }

    private ImageQueryCreator() {

    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }
}