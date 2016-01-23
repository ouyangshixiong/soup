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
 * 地区列表 楼名列表 下发   字段改为recieverLocation
 */
@ApiModel(description = ""
    + "地区列表 楼名列表 下发   字段改为recieverLocation")
@Entity
@Table(name = "ya_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 20)
    @Column(name = "receiver_name", length = 20, nullable = false)
    private String receiverName;
    
    @Column(name = "receiver_mobile")
    private String receiverMobile;
    
    @Column(name = "receiver_district")
    private String receiverDistrict;
    
    @Column(name = "receiver_building")
    private String receiverBuilding;
    
    @Column(name = "receiver_floor")
    private String receiverFloor;
    
    @Column(name = "receiver_company")
    private String receiverCompany;
    
    @NotNull
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;
    
    @ManyToOne
    @JoinColumn(name = "yauser_id")
    private YaUser yauser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }
    
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }
    
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverBuilding() {
        return receiverBuilding;
    }
    
    public void setReceiverBuilding(String receiverBuilding) {
        this.receiverBuilding = receiverBuilding;
    }

    public String getReceiverFloor() {
        return receiverFloor;
    }
    
    public void setReceiverFloor(String receiverFloor) {
        this.receiverFloor = receiverFloor;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }
    
    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public YaUser getYauser() {
        return yauser;
    }

    public void setYauser(YaUser yaUser) {
        this.yauser = yaUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaAddress yaAddress = (YaAddress) o;
        if(yaAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaAddress{" +
            "id=" + id +
            ", receiverName='" + receiverName + "'" +
            ", receiverMobile='" + receiverMobile + "'" +
            ", receiverDistrict='" + receiverDistrict + "'" +
            ", receiverBuilding='" + receiverBuilding + "'" +
            ", receiverFloor='" + receiverFloor + "'" +
            ", receiverCompany='" + receiverCompany + "'" +
            ", isDefault='" + isDefault + "'" +
            '}';
    }
}
