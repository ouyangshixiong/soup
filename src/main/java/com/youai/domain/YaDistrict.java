package com.youai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A YaDistrict.
 */
@Entity
@Table(name = "ya_district")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaDistrict implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "district_code")
    private Integer districtCode;
    
    @Column(name = "district_name")
    private String districtName;
    
    @OneToMany(mappedBy = "yaDistrict")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YaBuilding> yaBuildings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }
    
    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Set<YaBuilding> getYaBuildings() {
        return yaBuildings;
    }

    public void setYaBuildings(Set<YaBuilding> yaBuildings) {
        this.yaBuildings = yaBuildings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaDistrict yaDistrict = (YaDistrict) o;
        if(yaDistrict.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaDistrict.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaDistrict{" +
            "id=" + id +
            ", districtCode='" + districtCode + "'" +
            ", districtName='" + districtName + "'" +
            '}';
    }
}
