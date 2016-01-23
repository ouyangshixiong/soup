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

import com.youai.domain.enumeration.Gender;

import com.youai.domain.enumeration.LoginStatus;

/**
 * 用户信息
 */
@ApiModel(description = ""
    + "用户信息")
@Entity
@Table(name = "ya_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    
    @Column(name = "mobile_number")
    private String mobileNumber;
    
    @Column(name = "facebook")
    private String facebook;
    
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "login_status")
    private LoginStatus loginStatus;
    
    @Column(name = "has_private_pot")
    private Boolean hasPrivatePot;
    
    @Column(name = "auth_token")
    private String authToken;
    
    @Column(name = "auth_token_update_time")
    private LocalDate authTokenUpdateTime;
    
    /**
     * short message limit 相关
     */
    @ApiModelProperty(value = ""
        + "short message limit 相关")
    @Column(name = "sm_used_count")
    private Integer smUsedCount;
    
    @Column(name = "sm_last_check_date")
    private LocalDate smLastCheckDate;
    
    @Column(name = "sm_last_content")
    private String smLastContent;
    
    @Column(name = "sm_expire_time")
    private LocalDate smExpireTime;
    
    @OneToMany(mappedBy = "yauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaPot> yaPots = new HashSet<>();

    @OneToMany(mappedBy = "yauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaOrder> yaOrders = new HashSet<>();

    @OneToMany(mappedBy = "yauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaCouponOrder> yaCouponOrders = new HashSet<>();

    @OneToMany(mappedBy = "yauser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaAddress> yaAddresss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFacebook() {
        return facebook;
    }
    
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }
    
    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Boolean getHasPrivatePot() {
        return hasPrivatePot;
    }
    
    public void setHasPrivatePot(Boolean hasPrivatePot) {
        this.hasPrivatePot = hasPrivatePot;
    }

    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LocalDate getAuthTokenUpdateTime() {
        return authTokenUpdateTime;
    }
    
    public void setAuthTokenUpdateTime(LocalDate authTokenUpdateTime) {
        this.authTokenUpdateTime = authTokenUpdateTime;
    }

    public Integer getSmUsedCount() {
        return smUsedCount;
    }
    
    public void setSmUsedCount(Integer smUsedCount) {
        this.smUsedCount = smUsedCount;
    }

    public LocalDate getSmLastCheckDate() {
        return smLastCheckDate;
    }
    
    public void setSmLastCheckDate(LocalDate smLastCheckDate) {
        this.smLastCheckDate = smLastCheckDate;
    }

    public String getSmLastContent() {
        return smLastContent;
    }
    
    public void setSmLastContent(String smLastContent) {
        this.smLastContent = smLastContent;
    }

    public LocalDate getSmExpireTime() {
        return smExpireTime;
    }
    
    public void setSmExpireTime(LocalDate smExpireTime) {
        this.smExpireTime = smExpireTime;
    }

    public Set<YaPot> getYaPots() {
        return yaPots;
    }

    public void setYaPots(Set<YaPot> yaPots) {
        this.yaPots = yaPots;
    }

    public Set<YaOrder> getYaOrders() {
        return yaOrders;
    }

    public void setYaOrders(Set<YaOrder> yaOrders) {
        this.yaOrders = yaOrders;
    }

    public Set<YaCouponOrder> getYaCouponOrders() {
        return yaCouponOrders;
    }

    public void setYaCouponOrders(Set<YaCouponOrder> yaCouponOrders) {
        this.yaCouponOrders = yaCouponOrders;
    }

    public Set<YaAddress> getYaAddresss() {
        return yaAddresss;
    }

    public void setYaAddresss(Set<YaAddress> yaAddresss) {
        this.yaAddresss = yaAddresss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaUser yaUser = (YaUser) o;
        if(yaUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaUser{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", facebook='" + facebook + "'" +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            ", gender='" + gender + "'" +
            ", loginStatus='" + loginStatus + "'" +
            ", hasPrivatePot='" + hasPrivatePot + "'" +
            ", authToken='" + authToken + "'" +
            ", authTokenUpdateTime='" + authTokenUpdateTime + "'" +
            ", smUsedCount='" + smUsedCount + "'" +
            ", smLastCheckDate='" + smLastCheckDate + "'" +
            ", smLastContent='" + smLastContent + "'" +
            ", smExpireTime='" + smExpireTime + "'" +
            '}';
    }
}
