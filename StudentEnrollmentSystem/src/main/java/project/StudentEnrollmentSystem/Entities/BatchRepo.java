package project.StudentEnrollmentSystem.Entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BatchRepo extends JpaRepository<Batch, Integer> {

// 6.List all Batches currently running
	@Query("From Batch where endDate > GETDATE()")
	List<Batch> getAllBatchesCurrentlyRunning();

// 7.List all Batches that were completed	
	@Query("From Batch where endDate < GETDATE()")
	List<Batch> getAllBatchesCurrentlyCompleted();

// 11.List all batches of a particular course
	@Query("From Batch  where courseCode = :courseCode")
	List<Batch> getAllBatchesByCourseCode(@Param("courseCode") String courseCode);

// 13.List all batches that started between two given dates
	@Query("From Batch where startDate between :date1 and :date2")
	List<Batch> getAllBatchesBy2GivenDates(LocalDate date1, LocalDate date2);

}
