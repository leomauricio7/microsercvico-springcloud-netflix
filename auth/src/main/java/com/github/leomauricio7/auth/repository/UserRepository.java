package com.github.leomauricio7.auth.repository;

import com.github.leomauricio7.auth.entity.Permission;
import com.github.leomauricio7.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName =:userName")
    User findByUserName(@Param("userName") String userName);

}
