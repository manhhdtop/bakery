package com.bakery.server.service.impl;

import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.entity.UserEntity;
import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.model.response.UserResponse;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.repository.UserRepository;
import com.bakery.server.service.UserService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${admin.user.username}")
    private String usernameAdministrator;

    @Override
    public List<UserResponse> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        Type type = new TypeToken<List<UserResponse>>() {
        }.getType();
        return modelMapper.map(userEntities, type);
    }

    public UserResponse save(UserCreateDto userCreateDto) {
        validateCreateUser(userCreateDto);

        UserEntity userOld = userRepository.findByUsername(userCreateDto.getUsername());
        AssertUtil.isNull(userOld, "user.create.username.exist");

        userCreateDto.encodePassword(passwordEncoder);
        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        if (!CollectionUtils.isEmpty(userCreateDto.getRoles())) {
            List<RoleEntity> roles = roleRepository.findAllById(userCreateDto.getRoles());
            AssertUtil.notEmpty(roles, "role.notExist");
            userEntity.setRoles(roles);
        }

        UserEntity user = userRepository.save(userEntity);
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse update(UserUpdateDto userUpdateDto) {
        validateUpdateUser(userUpdateDto);

        UserEntity userOld = userRepository.findById(userUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(userOld, "user.update.userId.notExist");
        AssertUtil.isFalse(userOld.getUsername().equals(usernameAdministrator), "user.update.userId.notExist");
        UserEntity userEntity = modelMapper.map(userUpdateDto, UserEntity.class);
        List<RoleEntity> roles = roleRepository.findAllById(userUpdateDto.getRoles());
        AssertUtil.notEmpty(roles, "role.notExist");
        userEntity.setRoles(roles);

        UserEntity user = userRepository.save(userEntity);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    private void validateCreateUser(UserCreateDto userCreateDto) {
        String username = userCreateDto.getUsername().toLowerCase().trim();
        userCreateDto.setUsername(username);
        userCreateDto.setName(userCreateDto.getName().trim());
        userCreateDto.setEmail(userCreateDto.getEmail().trim());
        userCreateDto.setPassword(userCreateDto.getPassword().trim());
        if (userCreateDto.getStatus() == null) {
            userCreateDto.setStatus(UserStatus.DEACTIVE.getStatus());
        }
    }

    private void validateUpdateUser(UserUpdateDto userUpdateDto) {
        userUpdateDto.setName(userUpdateDto.getName().trim());
        userUpdateDto.setEmail(userUpdateDto.getEmail().trim());
        if (userUpdateDto.getStatus() == null) {
            userUpdateDto.setStatus(UserStatus.DEACTIVE.getStatus());
        }
    }
}
