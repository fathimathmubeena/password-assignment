package com.example.springboot.repository;

import com.example.springboot.entity.FolderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<FolderTable, Long> {
    @Query(value = "select name from folder_tbl where mobile = :mobile", nativeQuery = true)
    public List<String> getAllFolders(Long mobile);

    @Query(value = "select * from folder_tbl where mobile = :mobile", nativeQuery = true)
    public List<FolderTable> showAllOfUser(Long mobile);

    @Query(value = "select id from folder_tbl where mobile = :mobile", nativeQuery = true)
    public List<Long> getAllFolderIds(Long mobile);
}
