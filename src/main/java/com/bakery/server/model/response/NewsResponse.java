package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NewsResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String content;
    private Integer read;
    private Integer like;
    private Date createdDate;
    private Date updatedDate;
    private Integer status;
}
