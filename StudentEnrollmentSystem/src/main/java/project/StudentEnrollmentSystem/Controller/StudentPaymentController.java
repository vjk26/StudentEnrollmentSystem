package project.StudentEnrollmentSystem.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import project.StudentEnrollmentSystem.Entities.Payment;
import project.StudentEnrollmentSystem.Entities.PaymentRepo;
import project.StudentEnrollmentSystem.Entities.PaymentStudent;
import project.StudentEnrollmentSystem.Entities.Student;
import project.StudentEnrollmentSystem.Entities.StudentRepo;

@RestController
public class StudentPaymentController {
	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	// <------------Add a new student to the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	// @PostMapping("/Students")
	@Operation(summary = "Add a New Student", description = "Create and add a new student to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Student created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Student addNewStudent(@Valid @RequestBody Student student) {
		studentRepo.save(student);
		return student;
	}

	// <------------Update an existing student in the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/Students/ByStudentId{studentId}")
	@Operation(summary = "Update a Student", description = "Update an existing student in the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Student updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "404", description = "Student not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Student updateStudent(@PathVariable("studentId") int studentId, @Valid @RequestBody Student student) {
		var optStudent = studentRepo.findById(studentId);
		try {
			if (!optStudent.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StudentId Not Found...!");

			studentRepo.save(student);
			return student;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// 4API<------------Update an existing payment in the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/Payments/ByPaymentId/{paymentId}")
	@Operation(summary = "Update a Payment", description = "Update an existing payment in the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "404", description = "Payment not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Payment updatePayment(@PathVariable("paymentId") Integer paymentId, @Valid @RequestBody Payment payment) {
		var optPayment = paymentRepo.findById(paymentId);
		try {
			if (!optPayment.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Id Not Found...!");
			}
			paymentRepo.save(payment);
			return payment;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// <------------ Add a student along with payment to the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/AddStudentWithPayment")
	@Operation(summary = "Add a Student with Payment", description = "add a new student along with payment to the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student and Payment created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@Transactional
	public PaymentStudent addStudentPayment(@Valid @RequestBody PaymentStudent paymentstudent) {
		try {
			Student s = new Student();
			s.setName(paymentstudent.getName());
			s.setEmail(paymentstudent.getEmail());
			s.setMobile(paymentstudent.getMobile());
			s.setBatchId(paymentstudent.getBatchId());
			s.setDateOfJoining(paymentstudent.getDateOfJoining());

			List<Student> listStudent = studentRepo.findAll();
			boolean present = listStudent.contains(s);
			if (present) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already present");}
			studentRepo.save(s);
			Payment p = new Payment();
			p.setStudentId(s.getStudentId());
			p.setAmount(paymentstudent.getAmount());
			p.setPayDate(paymentstudent.getPayDate());
			p.setPayMode(paymentstudent.getPayMode());

			paymentRepo.save(p);

			return paymentstudent;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	// <-------- Delete a student along with their payment from the system--------->

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteStudentPayment/{studentId}")
	@Operation(summary = "Delete Student with Payment", description = "Delete a student along with their payment from the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student and Payment deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@Transactional
	public String deleteStudentWithPayment(@PathVariable("studentId") int studentId) {
		Optional<Student> s = studentRepo.findById(studentId);
		if (!s.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Id Not Exist...!");
		} else {
			Student student = s.get();
			Payment payment = paymentRepo.findByStudentId(studentId);
			paymentRepo.delete(payment);
			studentRepo.delete(student);
			return "Both Student ,StudentPayment are Deleted";
		}

	}

}
