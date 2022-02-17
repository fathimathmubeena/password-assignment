package com.example.springboot.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
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
    private final Long folderId;
    private final String notes;
    private final Long userId;

    public SiteTable toSiteTable() {
        return new SiteTable(this.id, this.url, this.name, this.username, this.password, this.folderId, this.notes, this.userId);
    }

    public SiteTable toSiteTable(Long id, Long userId) {
        return new SiteTable(id, this.url, this.name, this.username, this.password, this.folderId, this.notes, userId);
    }
}
