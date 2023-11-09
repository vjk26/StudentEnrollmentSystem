package project.StudentEnrollmentSystem.Entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
// 14.List all payments for a particular batch
	@Query("From Payment as p inner join Student as s on p.studentId=s.studentId  where batchId=:batchId")
	List<Payment> getAllPaymentsByBatch(@Param("batchId") int batchId);

// 15.List all payments made between two dates
	@Query("From Payment where payDate between :date1 and :date2")
	List<Payment> getAllPaymentsBTDays(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2);

	@Query(value = "select * from payments where paydate between :date1 and :date2", nativeQuery = true)
	List<Payment> getAllPaymentsBy2Dates(@Param("date1") Date date1, @Param("date2") Date date2);

// 16.List all payments of a particular payment mode
	@Query("select p from Payment as p where payMode=:payMode")
	List<Payment> getAllPaymentsByPayMode(@Param("payMode") String payMode);

	Payment findByStudentId(int studentId);

}
