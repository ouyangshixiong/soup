package com.youai.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A YaQuestion.
 */
@Entity
@Table(name = "ya_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YaQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 300)
    @Column(name = "ya_q", length = 300, nullable = false)
    private String yaQ;
    
    @Column(name = "ya_a")
    private String yaA;
    
    @Column(name = "priority")
    private Integer priority;
    
    @Column(name = "status")
    private String status;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYaQ() {
        return yaQ;
    }
    
    public void setYaQ(String yaQ) {
        this.yaQ = yaQ;
    }

    public String getYaA() {
        return yaA;
    }
    
    public void setYaA(String yaA) {
        this.yaA = yaA;
    }

    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YaQuestion yaQuestion = (YaQuestion) o;
        if(yaQuestion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yaQuestion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YaQuestion{" +
            "id=" + id +
            ", yaQ='" + yaQ + "'" +
            ", yaA='" + yaA + "'" +
            ", priority='" + priority + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
