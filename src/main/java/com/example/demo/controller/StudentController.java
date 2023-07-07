package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.StudException.AdharNotFound;
import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import com.example.demo.service.PDF_Exporter;
import com.example.demo.service.PDF_ExporterIndivitual;
import com.example.demo.service.StudentService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {
	private static Logger log=LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;
	@Autowired StudentDao dao;

	// display list of students
	@GetMapping("/studentList")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);
	}

	@GetMapping("/showNewStudentForm")
	public String showNewStudentForm(Model model) {
		// create model attribute to bind form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "new_student";
	}

	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute("student") Student student) {
		// save student to database
		studentService.saveStudent(student);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {

		// get student from the service
		Student student = studentService.getStudentById(id);

		// set student as a model attribute to pre-populate the form
		model.addAttribute("student", student);
		return "update_student";
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable(value = "id") int id) {

		// call delete student method
		this.studentService.deleteStudentById(id);
		return "redirect:/";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 5;

		Page<Student> page = studentService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Student> listStudents = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listStudents", listStudents);
		return "index2";
	}

	@GetMapping("/aadhar")
	public String aadharGet() {
		return "new";
	}

	@GetMapping("/addStudentByAadhar")
	public String forwarder(@RequestParam(value = "aadharNo") String aadharNo) {
		return "redirect:/addStudentByAadhar/" + aadharNo;
	}

	@GetMapping(value = "/addStudentByAadhar/{aadharNo}")
	public String addStudByAdr(@PathVariable(value = "aadharNo") long aadharNo, Model model)
			throws AdharNotFound, ParseException {
		try {
			Student student = studentService.save(aadharNo);
			model.addAttribute("student", student);
		} catch (AdharNotFound e) {
			return "ExceptionPage";
		}
		return "student_aadhar";
	}

	@GetMapping("/showNewStudentFormAadhar")
	public String showNewStudent(Model model) {
		// create model attribute to bind form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "student_aadhar";
	}
	
	@GetMapping("/regNo")
	public String regNo() {
		return "RegNo";
	}
	
	@GetMapping("/findStudentByRegNo")
	public String forwarder1(@RequestParam(value = "regNo") String regNo) {
		return "redirect:/findByRegNo/" + regNo;
	}
	
	@GetMapping(value="/findByRegNo/{regNo}")
	public String findByRegNo(@PathVariable long regNo,Model model) {
		Student st=studentService.findByRegNo(regNo);
		model.addAttribute("student", st);
		return "student_regNo";
	}

	@GetMapping("/students/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		log.info("Enterd Students List PDF Exporter");
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=students_list_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Student> listStudents = studentService.getAllStudents();

		PDF_Exporter exporter = new PDF_Exporter(listStudents);
		exporter.export(response);
		log.info(headerValue +" Exported");
	}
	
	@GetMapping("/student_indivitual/export/pdf/{regNo}")
	public void indivitual_exportToPDF(@PathVariable long regNo, HttpServletResponse response) throws DocumentException, IOException {
		log.info("Entered Student detail PDF Exporter");
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		Student student=studentService.findByRegNo(regNo);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename= "+student.getFirstName()+" Details_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		PDF_ExporterIndivitual exporter = new PDF_ExporterIndivitual(student);
		exporter.export(response);
		log.info(headerValue+" Exported");
	}


}
