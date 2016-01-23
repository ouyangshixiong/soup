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

import com.youai.domain.enumeration.CouponType;

/**
 * 优惠券
 */
@ApiModel(description = ""
    + "优惠券")
@Entity
@Table(name = "ya_coupon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaCoupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 40)
    @Column(name = "coupon_name", length = 40, nullable = false)
    private String couponName;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false)
    private CouponType couponType;
    
    @NotNull
    @Column(name = "coupon_pictures", nullable = false)
    private String couponPictures;
    
    @Column(name = "price")
    private Float price;
    
    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "coupon_discription")
    private String couponDiscription;
    
    @NotNull
    @Column(name = "coupon_amount", nullable = false)
    private Integer couponAmount;
    
    /**
     * 库存
     */
    @ApiModelProperty(value = ""
        + "库存")
    @NotNull
    @Column(name = "coupon_inventory", nullable = false)
    private Integer couponInventory;
    
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
    @Column(name = "coupon_status")
    private Boolean couponStatus;
    
    @OneToMany(mappedBy = "yacoupon")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaCouponOrder> yaCouponOrders = new HashSet<>();

    @OneToMany(mappedBy = "yacoupon")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaBanner> yaBanners = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }
    
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public CouponType getCouponType() {
        return couponType;
    }
    
    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public String getCouponPictures() {
        return couponPictures;
    }
    
    public void setCouponPictures(String couponPictures) {
        this.couponPictures = couponPictures;
    }

    public Float getPrice() {
        return price;
    }
    
    public void setPrice(Float price) {
        this.price = price;
    }

    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCouponDiscription() {
        return couponDiscription;
    }
    
    public void setCouponDiscription(String couponDiscription) {
        this.couponDiscription = couponDiscription;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }
    
    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getCouponInventory() {
        return couponInventory;
    }
    
    public void setCouponInventory(Integer couponInventory) {
        this.couponInventory = couponInventory;
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

    public Boolean getCouponStatus() {
        return couponStatus;
    }
    
    public void setCouponStatus(Boolean couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Set<YaCouponOrder> getYaCouponOrders() {
        return yaCouponOrders;
    }

    public void setYaCouponOrders(Set<YaCouponOrder> yaCouponOrders) {
        this.yaCouponOrders = yaCouponOrders;
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
        YaCoupon yaCoupon = (YaCoupon) o;
        if(yaCoupon.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaCoupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaCoupon{" +
            "id=" + id +
            ", couponName='" + couponName + "'" +
            ", couponType='" + couponType + "'" +
            ", couponPictures='" + couponPictures + "'" +
            ", price='" + price + "'" +
            ", keywords='" + keywords + "'" +
            ", couponDiscription='" + couponDiscription + "'" +
            ", couponAmount='" + couponAmount + "'" +
            ", couponInventory='" + couponInventory + "'" +
            ", onShelveDate='" + onShelveDate + "'" +
            ", offShelveDate='" + offShelveDate + "'" +
            ", couponStatus='" + couponStatus + "'" +
            '}';
    }
}
