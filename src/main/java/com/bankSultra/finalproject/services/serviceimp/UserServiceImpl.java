package com.bankSultra.finalproject.services.serviceimp;


//import com.bankSultra.finalproject.dto.CreateUserDto;
import com.bankSultra.finalproject.dto.UpdateUserDto;
import com.bankSultra.finalproject.dto.UserDto;
import com.bankSultra.finalproject.dto.UserResponseDto;
import com.bankSultra.finalproject.model.Role;
import com.bankSultra.finalproject.model.UserList;
import com.bankSultra.finalproject.repository.RoleRepository;
import com.bankSultra.finalproject.repository.UserListRepository;
import com.bankSultra.finalproject.security.BCrypt;
import com.bankSultra.finalproject.services.UserService;
import org.dom4j.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private ValidationService validationService;




    @Autowired
    public List<UserList> getAllUser(){
        return userListRepository.findAll();
    }

    @Override
    public UserList createUser(UserList userList) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Role role = roleRepository.findById(1L).orElse(null);

        userList.setPassword(passwordEncoder.encode(userList.getPassword()));

        userListRepository.save(userList);
        roleRepository.insertUserRole(userList.getId(), role.getId());

        return userList;
    }

@Transactional
public UserResponseDto update(UserList userList, UpdateUserDto request){
//    log.info("REQUEST : {}", request);

    if (Objects.nonNull(request.getFirstName())) {
        userList.setFirstName(request.getFirstName());
    }

    if (Objects.nonNull(request.getLastName())) {
        userList.setLastName(request.getLastName());
    }

    if (Objects.nonNull(request.getEmail())) {
        userList.setEmail(request.getEmail());
    }

    if (Objects.nonNull(request.getMobileNumber())) {
        userList.setMobileNumber(request.getMobileNumber());
    }

    userListRepository.save(userList);

    return UserResponseDto.builder()
            .id(userList.getId())
            .email(userList.getEmail())
            .build();


}

//    @Transactional
//    public void register(CreateUserDto request){
//        validationService.validate(request);
//
//        if (userListRepository.existsById(request.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id already registered");
//        }
//        Role role = roleRepository.findById(1L).orElse(null);
//
//        UserList user = new UserList();
//        user.setEmail(request.getEmail());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
//        user.setMobileNumber(request.getMobileNumber());
//        user.setMobileNumber(request.getMobileNumber());
//
//        userListRepository.save(user);
//        roleRepository.insertUserRole(user.getId(), role.getId());
//    }



    @Override
    public Optional<UserList> findByemail(String email) {
        return userListRepository.findByEmail(email);
    }

}




