package com.youai.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 订单号全局唯一生成表应对将来数据分库分表的问题
 */
@ApiModel(description = ""
    + "订单号全局唯一生成表应对将来数据分库分表的问题")
@Entity
@Table(name = "ya_order_sequence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaOrderSequence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaOrderSequence yaOrderSequence = (YaOrderSequence) o;
        if(yaOrderSequence.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaOrderSequence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaOrderSequence{" +
            "id=" + id +
            '}';
    }
}
