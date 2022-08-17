package test1.com.test1.object;

public class UserStatusRequest {
    private int id;
    private boolean online;

    public int getId() {
        return id;
    }

    public UserStatusRequest setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isOnline() {
        return online;
    }

    public UserStatusRequest setOnline(boolean status) {
        this.online = status;
        return this;
    }
}
