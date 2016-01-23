package com.youai.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A YaBuilding.
 */
@Entity
@Table(name = "ya_building")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaBuilding implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "building_code")
    private Integer buildingCode;
    
    @Column(name = "building_name")
    private String buildingName;
    
    @ManyToOne
    @JoinColumn(name = "ya_district_id")
    private YaDistrict yaDistrict;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBuildingCode() {
        return buildingCode;
    }
    
    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public YaDistrict getYaDistrict() {
        return yaDistrict;
    }

    public void setYaDistrict(YaDistrict yaDistrict) {
        this.yaDistrict = yaDistrict;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaBuilding yaBuilding = (YaBuilding) o;
        if(yaBuilding.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaBuilding.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaBuilding{" +
            "id=" + id +
            ", buildingCode='" + buildingCode + "'" +
            ", buildingName='" + buildingName + "'" +
            '}';
    }
}
