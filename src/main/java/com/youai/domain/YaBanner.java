package com.youai.domain;

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
 * A YaBanner.
 */
@Entity
@Table(name = "ya_banner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaBanner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "banner_pictures", nullable = false)
    private String bannerPictures;
    
    @Column(name = "banner_name")
    private String bannerName;
    
    @Column(name = "update_date")
    private LocalDate updateDate;
    
    @ManyToOne
    @JoinColumn(name = "yaproduct_id")
    private YaProduct yaproduct;

    @ManyToOne
    @JoinColumn(name = "yacoupon_id")
    private YaCoupon yacoupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerPictures() {
        return bannerPictures;
    }
    
    public void setBannerPictures(String bannerPictures) {
        this.bannerPictures = bannerPictures;
    }

    public String getBannerName() {
        return bannerName;
    }
    
    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public YaProduct getYaproduct() {
        return yaproduct;
    }

    public void setYaproduct(YaProduct yaProduct) {
        this.yaproduct = yaProduct;
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
        YaBanner yaBanner = (YaBanner) o;
        if(yaBanner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaBanner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaBanner{" +
            "id=" + id +
            ", bannerPictures='" + bannerPictures + "'" +
            ", bannerName='" + bannerName + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
