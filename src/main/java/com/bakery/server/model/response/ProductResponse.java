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
        if (StringUtils.isNotBlank(images)) {
            this.images = Arrays.stream(images.split(",")).map(UploadFileResponse::of).collect(Collectors.toList());
        }
        this.category = new CategoryResponse(categoryId, categoryName);
        convertOpitons(options);
    }

    private void convertOpitons(String optionsString) {
        Map<Long, OptionTypeResponse> map = new HashMap<>();
        if (StringUtils.isNotBlank(optionsString)) {
            String[] arrOption = optionsString.trim().split(",");
            for (String s : arrOption) {
                int i = 0;
                String[] field = s.split("\\|");
                OptionTypeResponse optionType;
                ProductOptionResponse productOption = new ProductOptionResponse();
                productOption.setId(Long.parseLong(field[i++]));
                productOption.setProductId(this.getId());
                productOption.setValue(field[i++]);
                String price = field[i++];
                if (StringUtils.isNotBlank(price)) {
                    productOption.setPrice(Long.parseLong(price));
                }
                long optionTypeId = Long.parseLong(field[i++]);
                optionType = map.getOrDefault(optionTypeId, null);
                if (optionType == null) {
                    optionType = new OptionTypeResponse(optionTypeId, field[i]);
                    map.put(optionType.getId(), optionType);
                }
                optionType.getOptions().add(productOption);
            }
        }
        this.optionTypes = new ArrayList<>(map.values());
    }
}
