package com.bakery.server.model.response;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private CategoryResponse parent;
    private Date createdDate;
    private UserResponse createdBy;
    private Date updatedDate;
    private UserResponse updatedBy;

    public static CategoryResponse of(CategoryEntity entity) {

        return null;
    }
}
