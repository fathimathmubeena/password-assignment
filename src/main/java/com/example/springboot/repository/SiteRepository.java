package com.example.springboot.repository;

import com.example.springboot.entity.SiteTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {
    @Query(value = "select * from site_tbl where name LIKE :name%", nativeQuery = true)
    public List<SiteTable> getSitesLike(String name);

    @Query(value = "select name from site_tbl", nativeQuery = true)
    public List<String> getAllNames();

    @Query(value = "select * from site_tbl where name = :name", nativeQuery = true)
    public Optional<SiteTable> getSite(String name);

}
