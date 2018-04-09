package DB.QueryCreators;

public class UserQueryCreator {
    private static UserQueryCreator ourInstance = new UserQueryCreator();

    public static UserQueryCreator getInstance() {
        return ourInstance;
    }

    private UserQueryCreator() {
    }

    public String getUserByEmail(String email) {
        String sq
    }
}
