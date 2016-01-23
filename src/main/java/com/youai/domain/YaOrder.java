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

import com.youai.domain.enumeration.OrderStatus;

import com.youai.domain.enumeration.PotReturnMethod;

/**
 * 订单
 */
@ApiModel(description = ""
    + "订单")
@Entity
@Table(name = "ya_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 订单逻辑id
     */
    @ApiModelProperty(value = ""
        + "订单逻辑id")
    @NotNull
    @Column(name = "order_id", nullable = false)
    private String orderId;
    
    @Column(name = "use_coupon")
    private Boolean useCoupon;
    
    @Column(name = "order_date")
    private LocalDate orderDate;
    
    /**
     * 冗余一份,订单used、lock产品数量
     */
    @ApiModelProperty(value = ""
        + "冗余一份,订单used、lock产品数量")
    @Column(name = "product_amount")
    private Integer productAmount;
    
    @NotNull
    @Column(name = "order_total_price", nullable = false)
    private Float orderTotalPrice;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    
    @Column(name = "order_status_change_time")
    private Long orderStatusChangeTime;
    
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    @Column(name = "delivery_period")
    private String deliveryPeriod;
    
    @Size(min = 2, max = 20)
    @Column(name = "delivery_man_name", length = 20)
    private String deliveryManName;
    
    @Column(name = "delivery_phone")
    private String deliveryPhone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pot_return_method")
    private PotReturnMethod potReturnMethod;
    
    /**
     * 是否有私人壶
     */
    @ApiModelProperty(value = ""
        + "是否有私人壶")
    @Column(name = "has_private_pot")
    private Boolean hasPrivatePot;
    
    /**
     * 是否使用私人壶
     */
    @ApiModelProperty(value = ""
        + "是否使用私人壶")
    @Column(name = "use_private_pot")
    private Boolean usePrivatePot;
    
    /**
     * 父订单逻辑id
     */
    @ApiModelProperty(value = ""
        + "父订单逻辑id")
    @Column(name = "parent_order_id")
    private String parentOrderId;
    
    @NotNull
    @Column(name = "is_parent_order", nullable = false)
    private Boolean isParentOrder;
    
    @NotNull
    @Column(name = "order_real_price", nullable = false)
    private Float orderRealPrice;
    
    @ManyToOne
    @JoinColumn(name = "yauser_id")
    private YaUser yauser;

    @OneToOne
    private YaAddress yaAddress;

    @OneToMany(mappedBy = "yaorder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaPot> yaPots = new HashSet<>();

    @OneToOne
    private YaCouponOrder yaCouponOrder;

    @ManyToOne
    @JoinColumn(name = "yaproduct_id")
    private YaProduct yaproduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getUseCoupon() {
        return useCoupon;
    }
    
    public void setUseCoupon(Boolean useCoupon) {
        this.useCoupon = useCoupon;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getProductAmount() {
        return productAmount;
    }
    
    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public Float getOrderTotalPrice() {
        return orderTotalPrice;
    }
    
    public void setOrderTotalPrice(Float orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderStatusChangeTime() {
        return orderStatusChangeTime;
    }
    
    public void setOrderStatusChangeTime(Long orderStatusChangeTime) {
        this.orderStatusChangeTime = orderStatusChangeTime;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }
    
    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public String getDeliveryManName() {
        return deliveryManName;
    }
    
    public void setDeliveryManName(String deliveryManName) {
        this.deliveryManName = deliveryManName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }
    
    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public PotReturnMethod getPotReturnMethod() {
        return potReturnMethod;
    }
    
    public void setPotReturnMethod(PotReturnMethod potReturnMethod) {
        this.potReturnMethod = potReturnMethod;
    }

    public Boolean getHasPrivatePot() {
        return hasPrivatePot;
    }
    
    public void setHasPrivatePot(Boolean hasPrivatePot) {
        this.hasPrivatePot = hasPrivatePot;
    }

    public Boolean getUsePrivatePot() {
        return usePrivatePot;
    }
    
    public void setUsePrivatePot(Boolean usePrivatePot) {
        this.usePrivatePot = usePrivatePot;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }
    
    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public Boolean getIsParentOrder() {
        return isParentOrder;
    }
    
    public void setIsParentOrder(Boolean isParentOrder) {
        this.isParentOrder = isParentOrder;
    }

    public Float getOrderRealPrice() {
        return orderRealPrice;
    }
    
    public void setOrderRealPrice(Float orderRealPrice) {
        this.orderRealPrice = orderRealPrice;
    }

    public YaUser getYauser() {
        return yauser;
    }

    public void setYauser(YaUser yaUser) {
        this.yauser = yaUser;
    }

    public YaAddress getYaAddress() {
        return yaAddress;
    }

    public void setYaAddress(YaAddress yaAddress) {
        this.yaAddress = yaAddress;
    }

    public Set<YaPot> getYaPots() {
        return yaPots;
    }

    public void setYaPots(Set<YaPot> yaPots) {
        this.yaPots = yaPots;
    }

    public YaCouponOrder getYaCouponOrder() {
        return yaCouponOrder;
    }

    public void setYaCouponOrder(YaCouponOrder yaCouponOrder) {
        this.yaCouponOrder = yaCouponOrder;
    }

    public YaProduct getYaproduct() {
        return yaproduct;
    }

    public void setYaproduct(YaProduct yaProduct) {
        this.yaproduct = yaProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaOrder yaOrder = (YaOrder) o;
        if(yaOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaOrder{" +
            "id=" + id +
            ", orderId='" + orderId + "'" +
            ", useCoupon='" + useCoupon + "'" +
            ", orderDate='" + orderDate + "'" +
            ", productAmount='" + productAmount + "'" +
            ", orderTotalPrice='" + orderTotalPrice + "'" +
            ", orderStatus='" + orderStatus + "'" +
            ", orderStatusChangeTime='" + orderStatusChangeTime + "'" +
            ", deliveryDate='" + deliveryDate + "'" +
            ", deliveryPeriod='" + deliveryPeriod + "'" +
            ", deliveryManName='" + deliveryManName + "'" +
            ", deliveryPhone='" + deliveryPhone + "'" +
            ", potReturnMethod='" + potReturnMethod + "'" +
            ", hasPrivatePot='" + hasPrivatePot + "'" +
            ", usePrivatePot='" + usePrivatePot + "'" +
            ", parentOrderId='" + parentOrderId + "'" +
            ", isParentOrder='" + isParentOrder + "'" +
            ", orderRealPrice='" + orderRealPrice + "'" +
            '}';
    }
}
