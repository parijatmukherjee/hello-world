package com.twodarray.helloworld.repository;

import com.twodarray.helloworld.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	List<Employee> findAllByBirthday(Date birthday);
}
