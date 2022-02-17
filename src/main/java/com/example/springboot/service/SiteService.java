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
import org.springframework.data.domain.Pageable;
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

    //#spring.data.web.pageable.page-parameter = PageNumber
//#spring.data.web.pageable.size-parameter = PageSize
    public List<Site> showAll(Long userId, Pageable page) {
        return siteRepository.showAllByUserId(userId, page)
                .stream()
                .map(SiteTable::toSite)
                .collect(Collectors.toList());
    }

    public Long add(Site site, Long userId) {
        List<Long> allFolders = folderRepository.findAllFolderIdsByUserId(userId);
        if (!allFolders.contains(site.getFolderId())) {
            log.warn("The given folder doesn't exist");
            throw new NotFoundException(ResultInfoConstants.FOLDER_NOT_FOUND);
        }
        SiteTable siteTable = site.toSiteTable();
        siteTable.setUserId(userId);
        siteTable.setUsername((bCryptPasswordEncoder.encode((site.getUsername()))));
        siteTable.setPassword((bCryptPasswordEncoder.encode((site.getPassword()))));
        return siteRepository.save(siteTable).getId();
    }

    public List<Site> search(String name, Long userId) {
        List<SiteTable> optionalSiteTable = siteRepository.showSitesLike(name, userId);
        if (optionalSiteTable.size() == 0) {
            log.warn("There is no Sites stored with name:{}", name);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        return optionalSiteTable.stream().map(SiteTable::toSite).collect(Collectors.toList());
    }

    public Site searchById(Long id, Long userId) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, userId);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        return optionalSiteTable.get().toSite();
    }

    public Long update(Site site, Long id, Long userId) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, userId);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        List<Long> allFolders = folderRepository.findAllFolderIdsByUserId(userId);
        if (!allFolders.contains(site.getFolderId())) {
            log.warn("The given folder doesn't exist");
            throw new NotFoundException(ResultInfoConstants.FOLDER_NOT_FOUND);
        }

        SiteTable oldSiteTable = optionalSiteTable.get();
        SiteTable newSiteTable = site.toSiteTable(oldSiteTable.getId(), userId);
        newSiteTable.setCreatedAt(oldSiteTable.getCreatedAt());
        newSiteTable.setUsername((bCryptPasswordEncoder.encode((site.getUsername()))));
        newSiteTable.setPassword((bCryptPasswordEncoder.encode((site.getPassword()))));
        siteRepository.save(newSiteTable);
        return newSiteTable.toSite().getId();
    }

    public Long remove(Long id, Long userId) {
        Optional<SiteTable> optionalSiteTable = siteRepository.getSite(id, userId);
        if (!optionalSiteTable.isPresent()) {
            log.warn("There is no Sites stored with id:{}", id);
            throw new NotFoundException(ResultInfoConstants.SITE_NOT_FOUND);
        }
        siteRepository.deleteById(id);
        return id;
    }

// Folder Methods

    public Long createFolder(Folder folder, Long userId) {
        List<String> allFolders = folderRepository.findAllFolderNamesByUserId(userId);
        if (allFolders.contains(folder.getName())) {
            log.warn("This folder already exists :{}", folder.getName());
            throw new DuplicateKeyException(ResultInfoConstants.FOLDER_EXISTS);
        }
        FolderTable folderTable = folder.toFolderTable();
        folderTable.setUserId(userId);
        return folderRepository.save(folderTable).getId();
    }

    public List<Folder> showAllFolders(Long userId) {
        return folderRepository.findAllByUserId(userId)
                .stream()
                .map(FolderTable::toFolder)
                .collect(Collectors.toList());
    }

    public List<Site> showAllByFolder(Long id, Long userId) {
        return siteRepository.getAllSitesByFolder(id, userId)
                .stream()
                .map(SiteTable::toSite)
                .collect(Collectors.toList());
    }
}
