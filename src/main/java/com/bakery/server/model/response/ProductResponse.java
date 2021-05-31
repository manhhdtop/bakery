package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long price;
    private List<UploadFileResponse> images;
    private List<ProductOptionResponse> options;
    private List<OptionTypeResponse> optionTypes;
    private CategoryResponse category;
    private Integer status;

    public ProductResponse(Long id, String name, String slug, String description, Long price, String images, Long categoryId, String categoryName, String options) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.price = price;
        this.images = Arrays.stream(images.split(",")).map(UploadFileResponse::of).collect(Collectors.toList());
        this.category = new CategoryResponse(categoryId, categoryName);
        convertOpitons(options);
    }

    private void convertOpitons(String optionsString) {
        Map<Long, OptionTypeResponse> map = new HashMap<>();
        if (StringUtils.isNotBlank(optionsString)) {
            String[] arrOption = optionsString.trim().split(",");
            for (String s : arrOption) {
                String[] field = s.split("\\|");
                OptionTypeResponse optionType;
                ProductOptionResponse productOption = new ProductOptionResponse();
                productOption.setId(Long.parseLong(field[0]));
                productOption.setProductId(this.getId());
                productOption.setValue(field[1]);
                long optionTypeId = Long.parseLong(field[2]);
                optionType = map.getOrDefault(optionTypeId, null);
                if (optionType == null) {
                    optionType = new OptionTypeResponse();
                    optionType.setId(optionTypeId);
                    optionType.setName(field[3]);
                    optionType.setOptions(new ArrayList<>());
                    map.put(optionType.getId(), optionType);
                }
                optionType.getOptions().add(productOption);
            }
        }
        this.optionTypes = new ArrayList<>(map.values());
    }
}
