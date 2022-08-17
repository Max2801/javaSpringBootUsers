package test1.com.test1.service;

import java.sql.Timestamp;
import java.util.List;

import test1.com.test1.model.User;
import test1.com.test1.object.UserRequest;

public interface IUserService {
    User getById(int id);
    List<User> findAll();
    List<User> findByStatusAndStatusLastUpdateAfter(boolean status, Timestamp statusTimestamp);
    List<User> findByStatus(boolean status);
    List<User> findByStatusLastUpdateAfter(Timestamp statusTimestamp);
    int create(UserRequest userRequest);
    User changeStatus(User user, boolean newStatus);

}