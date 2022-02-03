package com.example.springboot.repository;

import com.example.springboot.entity.FolderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<FolderTable, Long> {
    @Query(value = "select name from folder_tbl where user_id = :id", nativeQuery = true)
    public List<String> findAllFolderNamesByUserId(Long id);

    @Query(value = "select * from folder_tbl where user_id = :id", nativeQuery = true)
    public List<FolderTable> findAllByUserId(Long id);

    @Query(value = "select id from folder_tbl where user_id = :id", nativeQuery = true)
    public List<Long> findAllFolderIdsByUserId(Long id);
}
