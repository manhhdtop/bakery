package com.bakery.server.service;

import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse save(UserCreateDto userCreateDto);

    UserResponse update(UserUpdateDto userUpdateDto);

    void delete(Long id);
}
