package test1.com.test1.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import test1.com.test1.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByStatusAndStatusLastUpdateAfter(boolean status, Timestamp statusTimestamp);
    List<User> findByStatus(boolean status);
    List<User> findByStatusLastUpdateAfter(Timestamp statusTimestamp);
}
