package com.example.dto;

import java.util.List;

/**
 * Created by Christian Sperandio on 01/08/2016.
 */
public class UserDTO implements NamedDTO {
    final private String company;
    final private String name;
    final private List<String> subscriptions;

    public UserDTO(String company, String name, List<String> subscriptions) {
        this.company = company;
        this.name = name;
        this.subscriptions = subscriptions;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public String getCompany() {
        return company;
    }
}
