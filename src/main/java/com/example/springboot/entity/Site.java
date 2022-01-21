package com.example.springboot.entity;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;

@Data
public class Site {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NotBlank
    @URL
    private final String url;
    @NotBlank
    private final String name;
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    private final String notes;

    public SiteTable toSiteTable() {
        return new SiteTable(this.id, this.url, this.name, this.username, this.password, this.notes);
    }
}
