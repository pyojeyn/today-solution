package com.today_solution_server.demo.repository;

import com.today_solution_server.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findOneByNickName(String nickName);

    Optional<Users> findOneByUserId(Integer userId);
}
