package com.techienotes.demoredis.service;

import com.techienotes.demoredis.model.User;
import com.techienotes.demoredis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "#id", unless = "#result == null")
    public User getUser(long id) {
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            log.info("User Exists with id {}", id);
            return userOp.get();
        } else {
            log.info("User doesn't Exists with id {}", id);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        log.info("Getting all users");
        return userRepository.findAll();

    }

    @Override
    @CacheEvict(value = "users", allEntries = false, key = "#id")
    public void delete(long id) {
        log.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @CachePut(value = "users", key = "#user.id")
    public User update(User user) {
        long id = user.getId();
        User userInDb = getUser(id);
        if (userInDb != null) {
            log.info(">> UserService : User updated : Exiting update");
            return create(user);
        } else {
            log.info(">> UserService : User with this id does not exist : Exiting update");
            return null;
        }
    }

    @Override
    public User create(User user) {
        log.info(" >> UserService : Entering create");

        User userToRet = userRepository.save(user);
        log.info(" << UserService : Exiting create");
        return userToRet;
    }
}
