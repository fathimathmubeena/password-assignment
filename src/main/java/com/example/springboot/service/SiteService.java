package com.example.springboot.service;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.entity.Site;
import com.example.springboot.entity.SiteTable;
import com.example.springboot.exception.DuplicateKeyException;
import com.example.springboot.exception.SiteNotFoundException;
import com.example.springboot.repository.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;


    public List<Site> showAll() {
        return siteRepository.findAll()
                .stream()
                .map(SiteTable::toSite)
                .collect(Collectors.toList());
    }

    public Long add(Site site) {
        URLValidator urlValidator = new URLValidator();
        List<String> oldNames = siteRepository.getAllNames();
        String newName = site.getName();
        if (oldNames.stream().anyMatch(newName::equalsIgnoreCase)) {
            log.warn("This name: {} is already used in other sites, Please give other names");
            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_NAME);
        }
        String url = site.getUrl();

        return siteRepository.save(site.toSiteTable()).getId();
    }

    public List<Site> search(String name) {
        List<SiteTable> optionalSiteTable = siteRepository.getSitesLike(name);
        if (optionalSiteTable.size() == 0) {
            log.warn("There is no Sites stored with name:{}", name);
            throw new SiteNotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        return optionalSiteTable.stream().map(SiteTable::toSite).collect(Collectors.toList());
    }

    public Site update(Site site, String name) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(name);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with name:{}", site.getName());
            throw new SiteNotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }

        SiteTable newSiteTable = site.toSiteTable();
        newSiteTable.setCreatedAt(optionalSiteTable.get().getCreatedAt());
        newSiteTable.setId(optionalSiteTable.get().getId());
        siteRepository.save(newSiteTable);
        return newSiteTable.toSite();
    }

    public String remove(String name) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(name);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with name:{}", name);
            throw new SiteNotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        Long id = optionalSiteTable.get().getId();
        siteRepository.deleteById(id);
        return name;

    }
}
