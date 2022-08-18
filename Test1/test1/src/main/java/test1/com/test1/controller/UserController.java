package test1.com.test1.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test1.com.test1.service.UserService;
import test1.com.test1.model.User;
import test1.com.test1.object.UserRequest;
import test1.com.test1.object.UserStatusRequest;
import test1.com.test1.object.UserStatusResponse;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public User getUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Iterable<User> getAll(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "online", required = false) Boolean online) {
        List<User> result = new ArrayList<>();
        
        if(id != null) {
            Timestamp timestamp = new Timestamp(id);
            if(online != null) {
                result = userService.findByStatusAndStatusLastUpdateAfter(online, timestamp);
            }
            else {
                result = userService.findByStatusLastUpdateAfter(timestamp);
            }
        }
        else if(online != null) {
            result = userService.findByStatus(online);
        }
        else {
            result = userService.findAll();
        }
        return result;   
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Order
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
        try {
            int newUserId = userService.create(userRequest);
            return new ResponseEntity<>(newUserId, HttpStatus.CREATED);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    @RequestMapping(value="/changeStatus", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Order
    public ResponseEntity<?> changeStatus(@RequestBody UserStatusRequest userStatusRequest) {        
        User oldUser = userService.getById(userStatusRequest.getId());
        if(oldUser != null) {
            Boolean oldStatus = oldUser.getStatus();
            try {
                userService.changeStatus(oldUser, userStatusRequest.isOnline());
                UserStatusResponse response = new UserStatusResponse()
                    .setId(userStatusRequest.getId())
                    .setOldStatus(oldStatus)
                    .setNewStatus(userStatusRequest.isOnline());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            catch (Exception ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(String.format("User id='%d' not found", userStatusRequest.getId()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

