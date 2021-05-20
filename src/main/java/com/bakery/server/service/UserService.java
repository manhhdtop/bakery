package com.bakery.server.service;

import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByName(String keyword, Pageable pageable);

    ApiBaseResponse save(UserCreateDto userCreateDto);

    ApiBaseResponse update(UserUpdateDto userUpdateDto);

    void delete(Long id);
}
