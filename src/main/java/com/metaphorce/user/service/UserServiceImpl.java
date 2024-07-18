package com.metaphorce.user.service;

import com.metaphorce.databaseLib.dto.UserDto;
import com.metaphorce.databaseLib.models.User;
import com.metaphorce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAge(userDTO.getAge());
            user.setRole(userDTO.getRole());
            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        } else {
            return new UserDto();
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDTO(User user) {
        return new UserDto(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPhone(), user.getAge(), user.getRole());
    }

    private User convertToEntity(UserDto userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getLastname(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getAge(), userDTO.getRole());
    }
}
