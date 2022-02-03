package com.example.springboot.repository;

import com.example.springboot.entity.OneTimePasswordTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OneTimePasswordTable, Long> {
    @Query(value = "select * from otp_tbl where user_id = :id", nativeQuery = true)
    public Optional<OneTimePasswordTable> findByUserId(Long id);

    @Query(value = "insert into otp_tbl set user_id =:userId ", nativeQuery = true)
    @Modifying
    public boolean insertUser(long userId);
}
