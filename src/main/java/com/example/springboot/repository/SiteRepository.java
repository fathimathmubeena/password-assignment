package com.example.springboot.repository;

import com.example.springboot.entity.SiteTable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {
    @Query(value = "select * from site_tbl where name LIKE :name% and user_id = :userId", nativeQuery = true)
    public List<SiteTable> showSitesLike(String name, Long userId);

    // public List<SiteTable> findByUserId(Long userId, Pageable page);

    @Query(value = "select * from site_tbl where user_id = :userId", nativeQuery = true)
    public List<SiteTable> showAllByUserId(Long userId, Pageable page);

    @Query(value = "select * from site_tbl where id = :id and user_id = :userId", nativeQuery = true)
    public Optional<SiteTable> getSite(Long id, Long userId);

    @Query(value = "select * from site_tbl where folder_id = :id and user_id = :userId", nativeQuery = true)
    public List<SiteTable> getAllSitesByFolder(Long id, Long userId);

}
