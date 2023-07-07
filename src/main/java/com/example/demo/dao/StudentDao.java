package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Repository
public class StudentDao {
	private static Logger log=LoggerFactory.getLogger(StudentDao.class);

	@Autowired
	StudentRepository studentRepository;

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public void saveStudent(Student student) {
		log.info("Register Number "+student.getRegNo()+" Details saved ");
		this.studentRepository.save(student);
	}

	public Student getStudentById(int id) {
		Optional<Student> optional = studentRepository.findById(id);
		Student student = null;
		if (optional.isPresent()) {
			student = optional.get();
		} else {
			throw new RuntimeException(" Student not found for id :: " + id);
		}

		return student;
	}

	public void deleteStudentById(int id) {
		this.studentRepository.deleteById(id);
	}

	public void save(Student st) {
		log.info("Register Number "+st.getRegNo()+" Details saved ");
		studentRepository.save(st);
	}

	public int getAdrCount() {
		return studentRepository.getAdrCount();
	}
	
	public Student findByRegNo(long regNo) {
		return studentRepository.findByRegNo(regNo);
	}
	
	public User findUserByname(String username) {
		return studentRepository.findUserByname(username);
	}

}
