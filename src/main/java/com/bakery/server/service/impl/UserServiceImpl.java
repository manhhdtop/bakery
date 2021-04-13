package com.bakery.server.service.impl;

import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.entity.UserEntity;
import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.UserResponse;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.repository.UserRepository;
import com.bakery.server.service.UserService;
import com.bakery.server.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public ApiBaseResponse findAll(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        return ApiBaseResponse.success(convertPage(page, pageable));
    }

    @Override
    public ApiBaseResponse findByName(String keyword, Pageable pageable) {
        Page<UserEntity> page = userRepository.findByUsernameContainingOrNameContaining(keyword.trim(), pageable);
        return ApiBaseResponse.success(convertPage(page, pageable));
    }

    public ApiBaseResponse save(UserCreateDto userCreateDto) {
        validateCreateUser(userCreateDto);

        UserEntity userOld = userRepository.findByUsername(userCreateDto.getUsername());
        AssertUtil.isNull(userOld, "user.create.username.exist");

        userCreateDto.encodePassword(passwordEncoder);
        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        if (!CollectionUtils.isEmpty(userCreateDto.getRoleIds())) {
            List<RoleEntity> roles = roleRepository.findAllById(userCreateDto.getRoleIds());
            AssertUtil.notEmpty(roles, "role.not_exist");
            userEntity.setRoles(roles);
        }

        UserEntity user = userRepository.save(userEntity);
        return ApiBaseResponse.success(modelMapper.map(user, UserResponse.class));
    }

    public ApiBaseResponse update(UserUpdateDto userUpdateDto) {
        validateUpdateUser(userUpdateDto);

        UserEntity userOld = userRepository.findById(userUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(userOld, "user.update.user_id.not_exist");
        AssertUtil.isFalse(userOld.getUsername().equals(usernameAdministrator), "user.update.user_id.not_exist");
        String password = null;
        if (StringUtils.isNotBlank(userUpdateDto.getPassword())) {
            userUpdateDto.setPassword(passwordEncoder.encode(userUpdateDto.getPassword().trim()));
        } else {
            password = userOld.getPassword();
        }
        modelMapper.map(userUpdateDto, userOld);
        if (password != null) {
            userOld.setPassword(password);
        }
        List<RoleEntity> roles = roleRepository.findAllById(userUpdateDto.getRoleIds());
        AssertUtil.notEmpty(roles, "role.not_exist");
        userOld.setRoles(roles);

        userOld = userRepository.save(userOld);
        return ApiBaseResponse.success(modelMapper.map(userOld, UserResponse.class));
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

    private Page<UserResponse> convertPage(Page<UserEntity> page, Pageable pageable) {
        List<UserEntity> userEntities = page.getContent();
        Type type = new TypeToken<List<UserResponse>>() {
        }.getType();
        List<UserResponse> userResponses = modelMapper.map(userEntities, type);
        return new PageImpl<>(userResponses, pageable, page.getTotalElements());
    }
}
