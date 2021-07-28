package com.bakery.server.entity;

import com.bakery.server.entity.base.AuditModel;
import com.bakery.server.model.response.ProductPriceRangeResponse;
import com.bakery.server.model.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static com.bakery.server.utils.Constant.RESULT_SET_MAPPING.PRODUCT_PRICE_RANGE_RESPONSE;
import static com.bakery.server.utils.Constant.RESULT_SET_MAPPING.PRODUCT_RESPONSE;

@SqlResultSetMapping(
        name = PRODUCT_RESPONSE,
        classes = @ConstructorResult(
                targetClass = ProductResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "slug", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "price", type = Long.class),
                        @ColumnResult(name = "images", type = String.class),
                        @ColumnResult(name = "categoryId", type = Long.class),
                        @ColumnResult(name = "categoryName", type = String.class),
                        @ColumnResult(name = "options", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = PRODUCT_PRICE_RANGE_RESPONSE,
        classes = @ConstructorResult(
                targetClass = ProductPriceRangeResponse.class,
                columns = {
                        @ColumnResult(name = "min", type = Long.class),
                        @ColumnResult(name = "max", type = Long.class)
                }
        )
)

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "product")
@Where(clause = "deleted is null or deleted = 0")
public class ProductEntity extends AuditModel {
    @Column(name = "name")
    private String name;
    @Column(name = "slug")
    private String slug;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Long price;
    @OneToMany
    @JoinColumn(name = "reference_id", referencedColumnName = "id")
    private List<FileUploadEntity> images;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;
    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<ProductOptionEntity> options;
    @Column(name = "rate_1")
    private Integer rate1;
    @Column(name = "rate_2")
    private Integer rate2;
    @Column(name = "rate_3")
    private Integer rate3;
    @Column(name = "rate_4")
    private Integer rate4;
    @Column(name = "rate_5")
    private Integer rate5;
    @Column(name = "status")
    private Integer status;
}
