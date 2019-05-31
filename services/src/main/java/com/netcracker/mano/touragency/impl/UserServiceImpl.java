package com.netcracker.mano.touragency.impl;

import com.netcracker.mano.touragency.converter.RoleConverter;
import com.netcracker.mano.touragency.converter.UserConverter;
import com.netcracker.mano.touragency.dto.UserDTO;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.exceptions.RegistrationException;
import com.netcracker.mano.touragency.interfaces.RoleService;
import com.netcracker.mano.touragency.interfaces.UserService;
import com.netcracker.mano.touragency.repository.CredentialsRepository;
import com.netcracker.mano.touragency.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    private CredentialsRepository credentialsRepository;

    private BCryptPasswordEncoder encoder;

    private UserConverter userConverter;

    private RoleService roleService;

    private RoleConverter roleConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CredentialsRepository credentialsRepository, BCryptPasswordEncoder encoder, UserConverter userConverter, RoleService roleService, RoleConverter roleConverter) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.encoder = encoder;
        this.userConverter = userConverter;
        this.roleService = roleService;
        this.roleConverter = roleConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findByCredentials_Login(login);
        if (user == null) throw new AuthorizationException("Cannot find user with this login");
        else if (user.getIsBlocked()) throw new AuthorizationException(("This user is blocked"));
        return new org.springframework.security.core.userdetails.User(
                user.getCredentials().getLogin(), user.getCredentials().getPassword(), getAuthority(user));

    }

    private Set<GrantedAuthority> getAuthority(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        log.info("Trying to register new user :{}", userDTO);
        User user = userConverter.convertToEntity(userDTO);
        if (checkUserIfExist(user.getCredentials().getLogin()))
            throw new RegistrationException("Cannot register user with already existing login");
        user.setIsBlocked(false);
        user.setRole(roleConverter.convertToEntity(roleService.findByName("ROLE_CLIENT")));
        String encodedPassword = encoder.encode(user.getCredentials().getPassword());
        user.getCredentials().setPassword(encodedPassword);
        return userConverter.convertToDTO(userRepository.save(user));
    }

    private boolean checkUserIfExist(String login) {
        log.info("Check user if exist :{}", login);
        return credentialsRepository.existsCredentialsByLogin(login);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.info("Trying to update user :{}", userDTO);
        User user = userConverter.convertToEntity(userDTO);
        String encodedPassword = encoder.encode(user.getCredentials().getPassword());
        user.getCredentials().setPassword(encodedPassword);
        return userConverter.convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findById(Long id) {
        log.info("Trying to get user by id :{}", id);
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException("Cannot find user with this id");
        return userConverter.convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        log.info("Trying to get all users");
        return userRepository.findAll()
                .stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void block(Long id) {
        log.info("Trying to block user with id :{}", id);
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException("Cannot block user with this id");
        if (user.getIsBlocked()) throw new CannotUpdateEntityException();
        user.setIsBlocked(true);
        userRepository.save(user);

    }

    @Override
    public void unblock(Long id) throws CannotUpdateEntityException, EntityNotFoundException {
        log.info("trying to unblock user with id:{}", id);
        User user = userRepository.findOne(id);
        if (user == null) throw new EntityNotFoundException("Cannot unblock user with this id");
        if (!user.getIsBlocked()) throw new CannotUpdateEntityException();
        user.setIsBlocked(false);
        userRepository.save(user);

    }

    @Override
    public List<UserDTO> getAllUsersByRole(String role) {
        return userRepository.findAllByRole_Name(role)
                .stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByLogin(String login) {
        User user = userRepository.findByCredentials_Login(login);
        if (user == null) throw new EntityNotFoundException("Cannot find user with this login");
        return userConverter.convertToDTO(user);
    }
}
