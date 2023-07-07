package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.StudException.AdharNotFound;
import com.example.demo.dao.StudentDao;
import com.example.demo.model.Aadhar;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service

public class StudentSeviceImpl implements StudentService {
	
	private static Logger log=LoggerFactory.getLogger(StudentSeviceImpl.class);
	
	public final static int clgCode=8224;
	Date date1=new Date();
	
	@Autowired
	private StudentDao studentdao;
	@Autowired
	private StudentRepository studentRepository;


	@Override
	public List<Student> getAllStudents() {
		return studentdao.getAllStudents();
	}

	@Override
	public void saveStudent(Student student) {
		student.setRegNo(creatRegNo());
		this.studentdao.saveStudent(student);
	}

	@Override
	public Student getStudentById(int id) {
		
		return studentdao.getStudentById(id);
	}

	@Override
	public void deleteStudentById(int id) {
		this.studentdao.deleteStudentById(id);
	}

	@Override
	public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.studentRepository.findAll(pageable);
	}
	
	@Autowired
	RestTemplate restTemp;

	
	public Student save(long aadharNo) throws AdharNotFound, ParseException {
		String url="http://localhost:8080/getByAadharNum/";
		ResponseEntity<Aadhar> response=restTemp.exchange(url+aadharNo, HttpMethod.GET, null, Aadhar.class);
		Aadhar a=response.getBody();
		Student st=new Student();
		
		Optional<Aadhar>check=Optional.ofNullable(a);
		if(check.isPresent()) {			
		st.setAadharNo(aadharNo);
		st.setAddress(a.getAddress());
		st.setEmail(a.getEmail());
		st.setFirstName(a.getFirstName());
		st.setGender(a.getGender());
		//st.setId(a.getId());
		st.setLastName(a.getLastName());
		st.setMobNo(a.getMobileNo());
		st.setDob(dateFormat(a.getDob()));
		st.setRegNo(creatRegNo());
		
		log.info("Details set Based on Aadhar Infermation");
		return st;
		
		}
		else {
			log.info("Invalid Aadhar number Entered");
			throw new AdharNotFound("This Adhar number not exist");
		}
		
	}
	
	private int creatRegNo() {
		int regNo=Integer.valueOf(String.valueOf(clgCode)+dateFormat(date1).substring(8, 10)
				+String.valueOf(String.format("%03d", studentdao.getAdrCount()+1)));
		log.info("Register Number Created");
		return regNo;
	}
	public Student findByRegNo(long regNo) {
		return studentdao.findByRegNo(regNo);
	}
		
	public String dateFormat (Date date) {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	        String formattedDate = formatter.format(date);
	        return formattedDate;
	}
	
	

}
