package test1.com.test1.object;

public class UserStatusResponse {
    private boolean oldStatus;
    private boolean newStatus;
    private int id;

    public boolean getOldStatus() {
        return oldStatus;
    }

    public UserStatusResponse setOldStatus(boolean status) {
        this.oldStatus = status;
        return this;
    }

    public boolean getNewStatus() {
        return newStatus;
    }

    public UserStatusResponse setNewStatus(boolean status) {
        this.newStatus = status;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserStatusResponse setId(int id) {
        this.id = id;
        return this;
    }
}
