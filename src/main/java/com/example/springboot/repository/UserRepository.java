package com.example.springboot.repository;

import com.example.springboot.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {

    // User findByUsername(Long mobile);


    UserTable findByUsername(Long username);

    @Query(value = "select username from user_tbl", nativeQuery = true)
    public List<Long> getAllMobileNumbers();

    @Query(value = "select count(id) from user_tbl where id = :id", nativeQuery = true)
    public int checkUserExistsById(Long id);

    @Query(value = "select count(id) from user_tbl where username = :mobile", nativeQuery = true)
    public int checkUserExistsByUsername(Long mobile);

    @Query(value = "select id from user_tbl where username =:username", nativeQuery = true)
    public Long getIdByUsername(long username);

    @Query(value = "select password from user_tbl where username =:username", nativeQuery = true)
    public Long getMpin(long username);

    @Query(value = "select * from user_tbl where id =:userId", nativeQuery = true)
    public UserTable getUserById(long userId);


    @Query(value = "update user_tbl set password =:password where id =:userId", nativeQuery = true)
    @Modifying
    public void changePasswordById(long password, long userId);

}
