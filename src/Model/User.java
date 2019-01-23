package Model;

public class User {
    private String login;
    private String password;
    private boolean adminAccess;
    private int firma_id;

    public User(String login, String password, boolean adminAccess, int firma_id) {
        this.login = login;
        this.password = password;
        this.adminAccess = adminAccess;
        this.firma_id = firma_id;
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

    public int getFirma_id()
    {
        return firma_id;
    }

    public void setFirma_id(int firma_id)
    {
        this.firma_id = firma_id;
    }
}
