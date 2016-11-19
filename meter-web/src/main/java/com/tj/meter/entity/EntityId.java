package com.tj.meter.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class EntityId {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "snowflake", strategy = "com.tj.meter.util.SnowflakeIdGenerator")
    @GeneratedValue(generator = "snowflake")
    private Long id;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
