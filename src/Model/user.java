package Model;

public class user {
    private String login;
    private String password;
    private boolean adminAccess;

    public user(String login, String password, boolean adminAccess) {
        this.login = login;
        this.password = password;
        this.adminAccess = adminAccess;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }
}
