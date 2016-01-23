package com.youai.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 商品
 */
@ApiModel(description = ""
    + "商品")
@Entity
@Table(name = "ya_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 40)
    @Column(name = "product_name", length = 40, nullable = false)
    private String productName;
    
    @NotNull
    @Column(name = "product_pictures", nullable = false)
    private String productPictures;
    
    @NotNull
    @Column(name = "orginal_price", nullable = false)
    private Float orginalPrice;
    
    @Column(name = "discount_price")
    private Float discountPrice;
    
    @Column(name = "membership_price")
    private Float membershipPrice;
    
    @Column(name = "is_serial")
    private Boolean isSerial;
    
    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "product_discription")
    private String productDiscription;
    
    @NotNull
    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;
    
    /**
     * 库存
     */
    @ApiModelProperty(value = ""
        + "库存")
    @NotNull
    @Column(name = "product_inventory", nullable = false)
    private Integer productInventory;
    
    @NotNull
    @Column(name = "on_shelve_date", nullable = false)
    private LocalDate onShelveDate;
    
    @NotNull
    @Column(name = "off_shelve_date", nullable = false)
    private LocalDate offShelveDate;
    
    /**
     * 可用或者不可用
     */
    @ApiModelProperty(value = ""
        + "可用或者不可用")
    @Column(name = "product_status")
    private Boolean productStatus;
    
    @OneToMany(mappedBy = "yaproduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaOrder> yaOrders = new HashSet<>();

    @OneToMany(mappedBy = "yaProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaSubProduct> yaSubProducts = new HashSet<>();

    @OneToMany(mappedBy = "yaproduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaBanner> yaBanners = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPictures() {
        return productPictures;
    }
    
    public void setProductPictures(String productPictures) {
        this.productPictures = productPictures;
    }

    public Float getOrginalPrice() {
        return orginalPrice;
    }
    
    public void setOrginalPrice(Float orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }
    
    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getMembershipPrice() {
        return membershipPrice;
    }
    
    public void setMembershipPrice(Float membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public Boolean getIsSerial() {
        return isSerial;
    }
    
    public void setIsSerial(Boolean isSerial) {
        this.isSerial = isSerial;
    }

    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getProductDiscription() {
        return productDiscription;
    }
    
    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public Integer getProductAmount() {
        return productAmount;
    }
    
    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public Integer getProductInventory() {
        return productInventory;
    }
    
    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public LocalDate getOnShelveDate() {
        return onShelveDate;
    }
    
    public void setOnShelveDate(LocalDate onShelveDate) {
        this.onShelveDate = onShelveDate;
    }

    public LocalDate getOffShelveDate() {
        return offShelveDate;
    }
    
    public void setOffShelveDate(LocalDate offShelveDate) {
        this.offShelveDate = offShelveDate;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }
    
    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public Set<YaOrder> getYaOrders() {
        return yaOrders;
    }

    public void setYaOrders(Set<YaOrder> yaOrders) {
        this.yaOrders = yaOrders;
    }

    public Set<YaSubProduct> getYaSubProducts() {
        return yaSubProducts;
    }

    public void setYaSubProducts(Set<YaSubProduct> yaSubProducts) {
        this.yaSubProducts = yaSubProducts;
    }

    public Set<YaBanner> getYaBanners() {
        return yaBanners;
    }

    public void setYaBanners(Set<YaBanner> yaBanners) {
        this.yaBanners = yaBanners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaProduct yaProduct = (YaProduct) o;
        if(yaProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaProduct{" +
            "id=" + id +
            ", productName='" + productName + "'" +
            ", productPictures='" + productPictures + "'" +
            ", orginalPrice='" + orginalPrice + "'" +
            ", discountPrice='" + discountPrice + "'" +
            ", membershipPrice='" + membershipPrice + "'" +
            ", isSerial='" + isSerial + "'" +
            ", keywords='" + keywords + "'" +
            ", productDiscription='" + productDiscription + "'" +
            ", productAmount='" + productAmount + "'" +
            ", productInventory='" + productInventory + "'" +
            ", onShelveDate='" + onShelveDate + "'" +
            ", offShelveDate='" + offShelveDate + "'" +
            ", productStatus='" + productStatus + "'" +
            '}';
    }
}
