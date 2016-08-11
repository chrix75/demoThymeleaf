package com.example.dto;

import java.util.List;

/**
 * Created by Christian Sperandio on 31/07/2016.
 */
public class CompanyDTO implements NamedDTO {
    final private String siret;
    final private String name;
    final private String zipcode;
    final private List<String> subscriptions;
    final private int userCount;

    public CompanyDTO(String siret, String name, String zipcode, List<String> subscriptions, int userCount) {
        this.siret = siret;
        this.name = name;
        this.zipcode = zipcode;
        this.subscriptions = subscriptions;
        this.userCount = userCount;
    }

    public String getSiret() {
        return siret;
    }

    public String getName() {
        return name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public int getUserCount() {
        return userCount;
    }
}
