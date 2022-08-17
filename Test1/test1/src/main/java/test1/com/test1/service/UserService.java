package test1.com.test1.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test1.com.test1.model.User;
import test1.com.test1.object.UserRequest;
import test1.com.test1.repository.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<User>();
        userRepository.findAll().forEach(result::add);
        return result;
    }    

    @Override
    public List<User> findByStatusAndStatusLastUpdateAfter(boolean status, Timestamp statusTimestamp) {
        List<User> userList = userRepository.findByStatusAndStatusLastUpdateAfter(status, statusTimestamp);
        return userList;
    }

    @Override
    public List<User> findByStatus(boolean status) {
        List<User> userList = userRepository.findByStatus(status);
        return userList;
    }

    @Override
    public List<User> findByStatusLastUpdateAfter(Timestamp statusTimestamp) {
        List<User> userList = userRepository.findByStatusLastUpdateAfter(statusTimestamp);
        return userList;
    }    

    @Override
    public int create(UserRequest userRequest) {
        User user = new User()
            .setName(userRequest.getName())
            .setEmail(userRequest.getEmail())
            .setStatus(userRequest.getStatus())
            .setAvatar(userRequest.getAvatar())
            .setStatusLastUpdate(Timestamp.from(Instant.now()));
        User newUser = userRepository.save(user);
        return newUser.getId();
    }

    @Override
    public User changeStatus(User user, boolean newStatus) {
        user.setStatus(newStatus)
            .setStatusLastUpdate(Timestamp.from(Instant.now()));
        User newUser = userRepository.save(user);
        return newUser;
    }
}
