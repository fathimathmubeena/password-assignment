package com.example.springboot.service;

import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.entity.Folder;
import com.example.springboot.entity.FolderTable;
import com.example.springboot.entity.Site;
import com.example.springboot.entity.SiteTable;
import com.example.springboot.exception.DuplicateKeyException;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.repository.FolderRepository;
import com.example.springboot.repository.SiteRepository;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;

    private final FolderRepository folderRepository;

    private final UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<Site> showAll(Long mobile) {
        return siteRepository.findAllOfUser(mobile)
                .stream()
                .map(SiteTable::toSite)
                .collect(Collectors.toList());
    }

    public Long add(Site site, Long mobile) {
//        List<String> oldNames = siteRepository.getAllNames(mobile);
//        String newName = site.getName();
//        if (oldNames.stream().anyMatch(newName::equalsIgnoreCase)) {
//            log.warn("This name: {} is already used in other sites, Please give other names");
//            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_NAME);
//        }
        List<Long> allFolders = folderRepository.getAllFolderIds(mobile);
        if (!allFolders.contains(site.getFolderId())) {
            log.warn("The given folder doesn't exist");
            throw new NotFoundException(ResultInfoConstants.FOLDER_NOT_FOUND);
        }
        SiteTable siteTable = site.toSiteTable();
        siteTable.setMobile(mobile);
        siteTable.setPassword((bCryptPasswordEncoder.encode((site.getPassword()))));
        return siteRepository.save(siteTable).getId();
    }

    public List<Site> search(String name, Long mobile) {
        List<SiteTable> optionalSiteTable = siteRepository.getSitesLike(name, mobile);
        if (optionalSiteTable.size() == 0) {
            log.warn("There is no Sites stored with name:{}", name);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        return optionalSiteTable.stream().map(SiteTable::toSite).collect(Collectors.toList());
    }

    public Site searchById(Long id, Long mobile) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, mobile);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        return optionalSiteTable.get().toSite();
    }

    public Long update(Site site, Long id, Long mobile) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, mobile);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        List<Long> allFolders = folderRepository.getAllFolderIds(mobile);
        if (!allFolders.contains(site.getFolderId())) {
            log.warn("The given folder doesn't exist");
            throw new NotFoundException(ResultInfoConstants.FOLDER_NOT_FOUND);
        }

        SiteTable oldSiteTable = optionalSiteTable.get();
        SiteTable newSiteTable = site.toSiteTable();
        newSiteTable.setCreatedAt(oldSiteTable.getCreatedAt());
        newSiteTable.setId(oldSiteTable.getId());
        newSiteTable.setMobile(mobile);
        newSiteTable.setPassword((bCryptPasswordEncoder.encode((site.getPassword()))));
        siteRepository.save(newSiteTable);
        return newSiteTable.toSite().getId();
    }

    public Long remove(Long id, Long mobile) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, mobile);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        siteRepository.deleteById(id);
        return id;
    }

// Folder Methods

    public Long createFolder(Folder folder, Long mobile) {
        List<String> allFolders = folderRepository.getAllFolders(mobile);
        if (allFolders.contains(folder.getName())) {
            log.warn("This folder already exists :{}", folder.getName());
            throw new DuplicateKeyException(ResultInfoConstants.FOLDER_EXISTS);
        }
        FolderTable folderTable = folder.toFolderTable();
        folderTable.setMobile(mobile);
        return folderRepository.save(folderTable).getId();
    }

    public List<Folder> showAllFolders(Long mobile) {
        return folderRepository.showAllOfUser(mobile)
                .stream()
                .map(FolderTable::toFolder)
                .collect(Collectors.toList());
    }

    public List<Site> showAllByFolder(Long id, Long mobile) {
        return siteRepository.getAllSitesByFolder(id, mobile)
                .stream()
                .map(SiteTable::toSite)
                .collect(Collectors.toList());
    }
}
