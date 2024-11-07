package com.today_solution_server.demo.repository;

import com.today_solution_server.demo.entity.Records;
import com.today_solution_server.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Integer> {

    Optional<Records> findOneByUserAndRecordId(Users users, Integer recordId);

    List<Records> findAllByUserOrderByRecordedDateDesc(Users users);
}
