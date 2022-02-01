package com.example.springboot.repository;

import com.example.springboot.entity.SiteTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {
    @Query(value = "select * from site_tbl where name LIKE :name% and mobile = :mobile", nativeQuery = true)
    public List<SiteTable> getSitesLike(String name, Long mobile);

    @Query(value = "select * from site_tbl where mobile = :mobile", nativeQuery = true)
    public List<SiteTable> findAllOfUser(Long mobile);

    @Query(value = "select name from site_tbl where mobile = :mobile", nativeQuery = true)
    public List<String> getAllNames(Long mobile);

    @Query(value = "select * from site_tbl where id = :id and mobile = :mobile", nativeQuery = true)
    public Optional<SiteTable> getSite(Long id, Long mobile);

    @Query(value = "select * from site_tbl where folder_id = :id and mobile = :mobile", nativeQuery = true)
    public List<SiteTable> getAllSitesByFolder(Long id, Long mobile);

}
