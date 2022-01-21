package com.example.springboot.repository;

import com.example.springboot.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {
    @Query(value = "select mobile_no from user_tbl", nativeQuery = true)
    public List<Long> getAllMobileNumbers();

    @Query(value = "select mpin from user_tbl where mobile_no =:mobile", nativeQuery = true)
    public Long getMpin(long mobile);

    @Query(value = "select * from user_tbl where mobile_no =:mobile", nativeQuery = true)
    public UserTable getUser(long mobile);
}
