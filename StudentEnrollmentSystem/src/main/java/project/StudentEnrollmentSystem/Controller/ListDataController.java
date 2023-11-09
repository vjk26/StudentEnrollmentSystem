package project.StudentEnrollmentSystem.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import project.StudentEnrollmentSystem.Entities.Batch;
import project.StudentEnrollmentSystem.Entities.BatchRepo;
import project.StudentEnrollmentSystem.Entities.Course;
import project.StudentEnrollmentSystem.Entities.CourseRepo;
import project.StudentEnrollmentSystem.Entities.Payment;
import project.StudentEnrollmentSystem.Entities.PaymentRepo;
import project.StudentEnrollmentSystem.Entities.Student;
import project.StudentEnrollmentSystem.Entities.StudentRepo;

@RestController
public class ListDataController {
	@Autowired
	CourseRepo courseRepo;

	@Autowired
	BatchRepo batchRepo;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	PaymentRepo paymentRepo;

//<--------- List all Batches--------->
	@GetMapping("/ListBatches")
	@Operation(summary = "List of Batches", description = "Listing of batches")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing all batches"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Batch> getAllBatches() {
		return batchRepo.findAll();

	}

//<--------- List all Students--------->
	@CrossOrigin
	@GetMapping("/ListStudents")
	@Operation(summary = "List of Students", description = "Listing of students")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing all students"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

//<--------- List all Payments--------->
	@CrossOrigin
	@GetMapping("/ListPayments")
	@Operation(summary = "List of Payments", description = "Listing of payments")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing all payments"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Payment> getAllPayments() {
		return paymentRepo.findAll();
	}

//<---------5.List all Courses--------->
	@GetMapping("/ListCourses")
	@Operation(summary = "List of Courses", description = "Listing of courses")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing all courses"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Course> getAllCourses() {
		return courseRepo.findAll();
	}

//<---------6.List all Batches currently running--------->
	@GetMapping("/BatchesRunning")
	@Operation(summary = "List of Running Batches", description = "Listing of batches currently running")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing running batches"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Batch> getAllBatchesPresentRunning() {
		return batchRepo.getAllBatchesCurrentlyRunning();
	}

//<---------7.List all Batches that were completed
	@GetMapping("/BatchesCompleted")
	@Operation(summary = "List of Completed Batches", description = "Listing of batches that were completed")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing completed batches"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Batch> getAllBatchesCompletedTilNow() {
		return batchRepo.getAllBatchesCurrentlyCompleted();
	}

//<---------8.List all Students of currently running batches--------->
	@GetMapping("/Students/CurrentlyRunning")
	@Operation(summary = "List of Students Currently Running", description = "Listing students in currently running batches")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing students in running batches"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Student> getAllStudentsCurrentlyRunning() {
		return studentRepo.getAllStudentsCurrentlyRunning();
	}

//<---------9.List all Students for a particular course--------->
	@GetMapping("/Students/ByCourse/{code}")
	@Operation(summary = "List of Students by Course", description = "Listing students for a specific course")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing students for the course"),
			@ApiResponse(responseCode = "404", description = "No students found for the given course code"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Student> getAllStudentsByCourse(@PathVariable("code") String code) {
		List<Student> students = studentRepo.getAllStudentsByCourse(code);

		if (students.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found for the given course code.");
		}

		return students;
	}

//<---------10.List all Students of a particular batch--------->
	@GetMapping("/Students/ByBatch/{batchId}")
	@Operation(summary = "List of Students by Batch", description = "Listing students for a specific batch")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing students for the batch"),
			@ApiResponse(responseCode = "404", description = "No students found for the given batch ID"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Student> getAllStudentsByBatchId(@PathVariable("batchId") Integer batchId) {
		List<Student> students = studentRepo.getAllStudentByBatchId(batchId);

		if (students.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found for the given batch ID.");
		}

		return students;
	}

//<---------11.List all batches of a particular course--------->
	@GetMapping("/Batches/ByCourse/{courseCode}")
	@Operation(summary = "List of Batches by Course", description = "Listing batches for a specific course")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing batches for the course"),
			@ApiResponse(responseCode = "404", description = "No batches found for the given course code"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Batch> getAllBatchesByCourseCode(@PathVariable("courseCode") String courseCode) {
		List<Batch> batches = batchRepo.getAllBatchesByCourseCode(courseCode);

		if (batches.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No batches found for the given course code.");
		}

		return batches;
	}

//<---------12.List all students based on the given partial name--------->
	@GetMapping("/Students/PartialName/{name}")
	@Operation(summary = "List of Students by Partial Name", description = "Listing students by a partial name match")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing students by partial name"),
			@ApiResponse(responseCode = "404", description = "Given Partial Name doesn't exist"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Student> getStudentsByPartialName(@PathVariable("name") String name) {
		List<Student> s = studentRepo.findStudentByNameContaining(name);
		if (!s.isEmpty())
			return s;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Partial Name doesn't exist...!");
	}

//<---------13.List all batches that started between two given dates--------->
	@GetMapping("/Batches/ByDates/{date1}/{date2}")
	@Operation(summary = "List of Batches by Dates", description = "Listing batches that started between two given dates")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing batches by dates"),
			@ApiResponse(responseCode = "404", description = "No batches found for the given dates"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Batch> getAllBatchesBTGivenDates(@PathVariable("date1") LocalDate date1,
			@PathVariable("date2") LocalDate date2) {
		List<Batch> batches = batchRepo.getAllBatchesBy2GivenDates(date1, date2);

		if (batches.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No batches found for the given dates.");
		}

		return batches;
	}

//<---------14.List all payments for a particular batch--------->
	@GetMapping("/Payments/ByBatch/{batchId}")
	@Operation(summary = "List of Payments by Batch", description = "Listing payments for a specific batch")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing payments for the batch"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Payment> getAllPaymentsByBatch(@PathVariable("batchId") int batchId) {
		List<Payment> payments = paymentRepo.getAllPaymentsByBatch(batchId);
		if (payments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No  payments on given bybatchid.");
		}
		return payments;
	}

//<---------15.List all payments made between two dates--------->
	@GetMapping("/Payments/ByDates/{date1}/{date2}")
	@Operation(summary = "List of Payments by Dates", description = "Listing payments made between two given dates")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing payments by dates"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Payment> getAllPaymentsBy2Dates(@PathVariable("date1") LocalDate date1,
			@PathVariable("date2") LocalDate date2) {
		List<Payment> payments = paymentRepo.getAllPaymentsBTDays(date1, date2);
		if (payments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No  payments on given between dates.");
		}
		return payments;
	}

	@GetMapping("/Payments/ByDates2ndWay/{date1}/{date2}")
	@Operation(summary = "List of Payments by Dates (Alternative)", description = "Listing payments made between two given dates (alternative)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing payments by dates"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Payment> getAllPaymentsBy2Dates(@PathVariable("date1") Date date1, @PathVariable("date2") Date date2) {
		List<Payment> payments = paymentRepo.getAllPaymentsBy2Dates(date1, date2);

		if (payments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No  payments on given between dates.");
		}
		return payments;
	}

//<---------16.List all payments of a particular payment mode--------->
	@GetMapping("/Payments/ByPaymode/{payMode}")
	@Operation(summary = "List of Payments by Payment Mode", description = "Listing payments of a specific payment mode")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listing payments by payment mode"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Payment> getAllPaymentsByPaymode(@PathVariable("payMode") String payMode) {
		List<Payment> payments = paymentRepo.getAllPaymentsByPayMode(payMode);

		if (payments.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such payment mode found.");
		}
		return payments;
	}
}
