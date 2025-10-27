package com.example.app_jewelry.entity;

import java.util.List;

public class District {
    private int code;
    private String name;
    private List<Ward> wards;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Ward> getWards() {
        return wards;
    }

    @Override
    public String toString() {
        return name;
    }
}