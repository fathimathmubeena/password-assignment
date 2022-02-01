package com.example.springboot.repository;

import com.example.springboot.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {

    // User findByUsername(Long mobile);


    UserTable findByUsername(Long username);

    @Query(value = "select username from user_tbl", nativeQuery = true)
    public List<Long> getAllMobileNumbers();

    @Query(value = "select password from user_tbl where username =:username", nativeQuery = true)
    public Long getMpin(long username);

    @Query(value = "select * from user_tbl where username =:username", nativeQuery = true)
    public UserTable getUser(long username);

//    @Query(value = "delete from user_tbl where username =:username", nativeQuery = true)
//    public Long deleteAccount(long username);
}
