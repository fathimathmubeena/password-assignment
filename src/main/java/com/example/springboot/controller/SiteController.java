package com.example.springboot.controller;


import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.controller.response.ResponseWrapper;
import com.example.springboot.entity.Folder;
import com.example.springboot.entity.Site;
import com.example.springboot.service.SiteService;
import com.example.springboot.utility.JWTUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;


    private final JWTUtility jwtUtility;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> add(@RequestBody @Valid Site site, HttpServletRequest request) {
        log.info("Received request to add site :{}", site);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.add(site, jwtUtility.getId(request)));
    }

    @PostMapping("/folder/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> createFolder(@RequestBody @Valid Folder folder, HttpServletRequest request) {
        log.info("Received request to create folder :{}", folder);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.createFolder(folder, jwtUtility.getId(request)));
    }

    @GetMapping("/folder/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Folder>> showAllFolders(HttpServletRequest request) {
        log.info("Received request to show all folders");
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.showAllFolders(jwtUtility.getId(request)));
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Site>> search(@PathVariable @NotBlank String name, HttpServletRequest request) {
        log.info("Received request to search site starts with name: {}", name);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.search(name, jwtUtility.getId(request)));
    }

    @GetMapping("/search/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Site> searchById(@PathVariable @NotNull Long id, HttpServletRequest request) {
        log.info("Received request to search site of id: {}", id);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.searchById(id, jwtUtility.getId(request)));
    }

    @GetMapping("/show")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Site>> showAll(HttpServletRequest request, Pageable page) {
        log.info("Received request to show all sites");
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.showAll(jwtUtility.getId(request), page));
    }

    @GetMapping("/show/folder/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Site>> showAllByFolder(@PathVariable Long id, HttpServletRequest request) {
        log.info("Received request to show all sites in the given folder : {}", id);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.showAllByFolder(id, jwtUtility.getId(request)));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> update(@RequestBody @Valid Site site, @PathVariable @NotBlank Long id, HttpServletRequest request) {
        log.info("Received request to update site details of site id: {}", site.getId());
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.update(site, id, jwtUtility.getId(request)));
    }

    @DeleteMapping("/remove/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> remove(@PathVariable @Valid Long id, HttpServletRequest request) {
        log.info("Received request to remove the site details of site id: {}", id);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.remove(id, jwtUtility.getId(request)));
    }

}
