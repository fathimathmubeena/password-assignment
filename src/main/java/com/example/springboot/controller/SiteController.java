package com.example.springboot.controller;


import com.example.springboot.constants.ResultInfoConstants;
import com.example.springboot.controller.response.ResponseWrapper;
import com.example.springboot.entity.Site;
import com.example.springboot.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Long> add(@RequestBody @Valid Site site) {
        log.info("Received request to add site :{}", site);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.add(site));
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Site>> search(@PathVariable @NotBlank String name) {
        log.info("Received request to search site starts with name: {}", name);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.search(name));
    }

    @GetMapping("/show")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<List<Site>> showAll() {
        log.info("Received request to show all sites");
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.showAll());
    }

    @PutMapping("/update/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<Site> update(@RequestBody @Valid Site site, @PathVariable @NotBlank String name) {
        log.info("Received request to update site details of site name: {}", site.getName());
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.update(site, name));
    }

    @DeleteMapping("/remove/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseWrapper<String> remove(@PathVariable @Valid String name) {
        log.info("Received request to remove the site details of site name: {}", name);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.remove(name));
    }

}
