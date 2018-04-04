package Models;

public class UserInfo {

    public String user_id;
    public String old_password;
    public String new_password;

    public UserInfo(String user_id, String old_password, String new_password) {
        this.user_id = user_id;
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String getUserId() {
        return user_id;
    }

    public String getOldPassword() {
        return old_password;
    }

    public String getNewPassword() {
        return new_password;
    }

}
