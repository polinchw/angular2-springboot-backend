package com.bpe.springboot.data.rest.entity;

import javax.persistence.*;

/**
 * Created by polinchakb on 2/26/17.
 */
@Entity
@Table(name="hero")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
