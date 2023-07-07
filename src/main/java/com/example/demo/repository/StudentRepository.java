package com.example.demo.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	@Query(value="select count(aadhar_number) from k2_new.student_details",nativeQuery = true)
	public int getAdrCount();
	
	@Query(value = "select * from k2_new.stud_details where aadhar_no=:no",nativeQuery = true)
	public Student findByAdhar(@Param("no") long aadharNo);
	
	@Query(value = "select * from k2_new.student_details where register_number=:regNo",nativeQuery = true)
	public Student findByRegNo(@Param("regNo") long regNo);

	@Query(value="SELECT * FROM k2_new.student_details where first_name=:name;", nativeQuery= true)
	public User findUserByname(String name);
}
