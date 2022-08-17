package test1.com.test1.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.sql.Timestamp;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "avatar", nullable = true)
    private String avatar;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)    
    private String email;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "Status_last_update", nullable = false)
    private Timestamp statusLastUpdate;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public User setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Timestamp getStatusLastUpdate() {
        return statusLastUpdate;
    }

    public User setStatusLastUpdate(Timestamp statusLastUpdate) {
        this.statusLastUpdate = statusLastUpdate;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
            "User{id = %d, avatar = '%s', name = '%s', email = '%s', status = %b, statusLastUpdate = %t}",
                id, avatar, name, email, status, statusLastUpdate);
    }
}
