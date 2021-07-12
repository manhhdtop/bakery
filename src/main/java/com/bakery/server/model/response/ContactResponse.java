package com.bakery.server.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class ContactResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String content;
    private UserResponse updatedBy;
    private Date updatedDate;
    private Integer status;
    private String statusDescription;
}
