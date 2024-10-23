package com.example.testgreendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Car {
    @Id
    private Long id;
    private String name;
    private Double engine;

    public Car() {
    }
    @Generated(hash = 705397516)
    public Car(Long id, String name, Double engine) {
        this.id = id;
        this.name = name;
        this.engine = engine;
    }
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

    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }
}
