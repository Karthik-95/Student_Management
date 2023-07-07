package com.example.demo.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.StudException.AdharNotFound;
import com.example.demo.model.Student;

public interface StudentService {
	List<Student> getAllStudents();

	void saveStudent(Student student);

	Student getStudentById(int id);

	void deleteStudentById(int id);

	String dateFormat(Date date);
	
	Student findByRegNo(long regNo);

	Student save(long aadharNo) throws AdharNotFound, ParseException;

	Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
