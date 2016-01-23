package com.youai.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.youai.domain.enumeration.CouponOrderStatus;

/**
 * 买券复用YaOrderSequence生成唯一couponOrderId
 */
@ApiModel(description = ""
    + "买券复用YaOrderSequence生成唯一couponOrderId")
@Entity
@Table(name = "ya_coupon_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaCouponOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 买券逻辑id
     */
    @ApiModelProperty(value = ""
        + "买券逻辑id")
    @NotNull
    @Column(name = "coupon_order_id", nullable = false)
    private String couponOrderId;
    
    @Column(name = "coupon_order_date")
    private LocalDate couponOrderDate;
    
    @Column(name = "coupon_expire_date")
    private LocalDate couponExpireDate;
    
    /**
     * 系统送的不要付钱
     */
    @ApiModelProperty(value = ""
        + "系统送的不要付钱")
    @NotNull
    @Column(name = "coupon_need_pay", nullable = false)
    private Boolean couponNeedPay;
    
    @Column(name = "coupon_order_price")
    private Float couponOrderPrice;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_order_status", nullable = false)
    private CouponOrderStatus couponOrderStatus;
    
    @Column(name = "order_status_change_time")
    private Long orderStatusChangeTime;
    
    @Column(name = "parentcoupon_order_id")
    private String parentcouponOrderId;
    
    @NotNull
    @Column(name = "is_parent_coupon_order", nullable = false)
    private Boolean isParentCouponOrder;
    
    @ManyToOne
    @JoinColumn(name = "yauser_id")
    private YaUser yauser;

    @ManyToOne
    @JoinColumn(name = "yacoupon_id")
    private YaCoupon yacoupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponOrderId() {
        return couponOrderId;
    }
    
    public void setCouponOrderId(String couponOrderId) {
        this.couponOrderId = couponOrderId;
    }

    public LocalDate getCouponOrderDate() {
        return couponOrderDate;
    }
    
    public void setCouponOrderDate(LocalDate couponOrderDate) {
        this.couponOrderDate = couponOrderDate;
    }

    public LocalDate getCouponExpireDate() {
        return couponExpireDate;
    }
    
    public void setCouponExpireDate(LocalDate couponExpireDate) {
        this.couponExpireDate = couponExpireDate;
    }

    public Boolean getCouponNeedPay() {
        return couponNeedPay;
    }
    
    public void setCouponNeedPay(Boolean couponNeedPay) {
        this.couponNeedPay = couponNeedPay;
    }

    public Float getCouponOrderPrice() {
        return couponOrderPrice;
    }
    
    public void setCouponOrderPrice(Float couponOrderPrice) {
        this.couponOrderPrice = couponOrderPrice;
    }

    public CouponOrderStatus getCouponOrderStatus() {
        return couponOrderStatus;
    }
    
    public void setCouponOrderStatus(CouponOrderStatus couponOrderStatus) {
        this.couponOrderStatus = couponOrderStatus;
    }

    public Long getOrderStatusChangeTime() {
        return orderStatusChangeTime;
    }
    
    public void setOrderStatusChangeTime(Long orderStatusChangeTime) {
        this.orderStatusChangeTime = orderStatusChangeTime;
    }

    public String getParentcouponOrderId() {
        return parentcouponOrderId;
    }
    
    public void setParentcouponOrderId(String parentcouponOrderId) {
        this.parentcouponOrderId = parentcouponOrderId;
    }

    public Boolean getIsParentCouponOrder() {
        return isParentCouponOrder;
    }
    
    public void setIsParentCouponOrder(Boolean isParentCouponOrder) {
        this.isParentCouponOrder = isParentCouponOrder;
    }

    public YaUser getYauser() {
        return yauser;
    }

    public void setYauser(YaUser yaUser) {
        this.yauser = yaUser;
    }

    public YaCoupon getYacoupon() {
        return yacoupon;
    }

    public void setYacoupon(YaCoupon yaCoupon) {
        this.yacoupon = yaCoupon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaCouponOrder yaCouponOrder = (YaCouponOrder) o;
        if(yaCouponOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaCouponOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaCouponOrder{" +
            "id=" + id +
            ", couponOrderId='" + couponOrderId + "'" +
            ", couponOrderDate='" + couponOrderDate + "'" +
            ", couponExpireDate='" + couponExpireDate + "'" +
            ", couponNeedPay='" + couponNeedPay + "'" +
            ", couponOrderPrice='" + couponOrderPrice + "'" +
            ", couponOrderStatus='" + couponOrderStatus + "'" +
            ", orderStatusChangeTime='" + orderStatusChangeTime + "'" +
            ", parentcouponOrderId='" + parentcouponOrderId + "'" +
            ", isParentCouponOrder='" + isParentCouponOrder + "'" +
            '}';
    }
}
