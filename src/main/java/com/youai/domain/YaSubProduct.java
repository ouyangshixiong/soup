package com.youai.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 商品系列中的某一款
 */
@ApiModel(description = ""
    + "商品系列中的某一款")
@Entity
@Table(name = "ya_sub_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaSubProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "serial_product_id", nullable = false)
    private Long serialProductId;
    
    @Column(name = "capacity")
    private String capacity;
    
    @Column(name = "price")
    private Float price;
    
    @ManyToOne
    @JoinColumn(name = "ya_product_id")
    private YaProduct yaProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSerialProductId() {
        return serialProductId;
    }
    
    public void setSerialProductId(Long serialProductId) {
        this.serialProductId = serialProductId;
    }

    public String getCapacity() {
        return capacity;
    }
    
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Float getPrice() {
        return price;
    }
    
    public void setPrice(Float price) {
        this.price = price;
    }

    public YaProduct getYaProduct() {
        return yaProduct;
    }

    public void setYaProduct(YaProduct yaProduct) {
        this.yaProduct = yaProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaSubProduct yaSubProduct = (YaSubProduct) o;
        if(yaSubProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaSubProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaSubProduct{" +
            "id=" + id +
            ", serialProductId='" + serialProductId + "'" +
            ", capacity='" + capacity + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
