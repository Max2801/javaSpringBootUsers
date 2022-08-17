package test1.com.test1.object;

public class UserRequest {
    private String avatar;
    private String name;  
    private String email;
    private boolean status;

    public String getAvatar() {
        return avatar;
    }

    public UserRequest setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public UserRequest setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
