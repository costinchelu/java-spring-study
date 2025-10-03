package bootdemo.service;

import bootdemo.dto.UserRequest;
import bootdemo.entity.User;
import bootdemo.exception.UserNotFoundException;
import bootdemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public User saveUser(UserRequest userRequest) {
        User user = User
                .build(0,
                        userRequest.getName(),
                        userRequest.getEmail(),
                        userRequest.getMobile(),
                        userRequest.getGender(),
                        userRequest.getAge(),
                        userRequest.getNationality());
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException {
        return repository
                .findUserByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("User id " + id + " not found."));
    }
}
