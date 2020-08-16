package ru.HomeWork.Logic;

public class User {
    private String name;
    private String surName;
    private Double salary;

    public User(String name, String surName, Double salary) {
        this.name = name;
        this.surName = surName;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
