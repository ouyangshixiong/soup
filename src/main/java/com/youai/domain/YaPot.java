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

import com.youai.domain.enumeration.PotStatus;

/**
 * 壶
 */
@ApiModel(description = ""
    + "壶")
@Entity
@Table(name = "ya_pot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaPot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "is_private_pot", nullable = false)
    private Boolean isPrivatePot;
    
    @NotNull
    @Column(name = "pot_qrcode", nullable = false)
    private String potQrcode;
    
    @Size(min = 4, max = 20)
    @Column(name = "pot_label", length = 20)
    private String potLabel;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pot_status", nullable = false)
    private PotStatus potStatus;
    
    @Column(name = "pot_color")
    private String potColor;
    
    /**
     * 容量（单位：ml）
     */
    @ApiModelProperty(value = ""
        + "容量（单位：ml）")
    @Column(name = "pot_capacity")
    private Integer potCapacity;
    
    @Column(name = "pot_buy_date")
    private LocalDate potBuyDate;
    
    @ManyToOne
    @JoinColumn(name = "yauser_id")
    private YaUser yauser;

    @ManyToOne
    @JoinColumn(name = "yaorder_id")
    private YaOrder yaorder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPrivatePot() {
        return isPrivatePot;
    }
    
    public void setIsPrivatePot(Boolean isPrivatePot) {
        this.isPrivatePot = isPrivatePot;
    }

    public String getPotQrcode() {
        return potQrcode;
    }
    
    public void setPotQrcode(String potQrcode) {
        this.potQrcode = potQrcode;
    }

    public String getPotLabel() {
        return potLabel;
    }
    
    public void setPotLabel(String potLabel) {
        this.potLabel = potLabel;
    }

    public PotStatus getPotStatus() {
        return potStatus;
    }
    
    public void setPotStatus(PotStatus potStatus) {
        this.potStatus = potStatus;
    }

    public String getPotColor() {
        return potColor;
    }
    
    public void setPotColor(String potColor) {
        this.potColor = potColor;
    }

    public Integer getPotCapacity() {
        return potCapacity;
    }
    
    public void setPotCapacity(Integer potCapacity) {
        this.potCapacity = potCapacity;
    }

    public LocalDate getPotBuyDate() {
        return potBuyDate;
    }
    
    public void setPotBuyDate(LocalDate potBuyDate) {
        this.potBuyDate = potBuyDate;
    }

    public YaUser getYauser() {
        return yauser;
    }

    public void setYauser(YaUser yaUser) {
        this.yauser = yaUser;
    }

    public YaOrder getYaorder() {
        return yaorder;
    }

    public void setYaorder(YaOrder yaOrder) {
        this.yaorder = yaOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaPot yaPot = (YaPot) o;
        if(yaPot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaPot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaPot{" +
            "id=" + id +
            ", isPrivatePot='" + isPrivatePot + "'" +
            ", potQrcode='" + potQrcode + "'" +
            ", potLabel='" + potLabel + "'" +
            ", potStatus='" + potStatus + "'" +
            ", potColor='" + potColor + "'" +
            ", potCapacity='" + potCapacity + "'" +
            ", potBuyDate='" + potBuyDate + "'" +
            '}';
    }
}
