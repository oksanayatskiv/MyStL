package com.aplaygroundreviewer.aplaygroundreviewer.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Playground {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=400)
    private String name;

    @NotNull
    @Size(min=3, max=400)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id", unique = true)
    private Equipment equipment;

    public Playground () {
        this.equipment = new Equipment(false, false);
    }

    public Playground(String name, String description) {
        this.name = name;
        this.description = description;
        this.equipment = new Equipment(false, false);
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
