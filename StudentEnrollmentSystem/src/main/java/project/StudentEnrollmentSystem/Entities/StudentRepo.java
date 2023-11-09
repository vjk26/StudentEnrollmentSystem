package project.StudentEnrollmentSystem.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepo extends JpaRepository<Student, Integer> {
// 8.List all Students of currently running batches
	@Query("From Student as s inner join Batch as b on s.batchId=b.batchId where endDate > GETDATE()")
	List<Student> getAllStudentsCurrentlyRunning();

// 9.List all Students for a particular course
	@Query("From Student as s inner join Batch as b on s.batchId=b.batchId join Course c on c.code=b.courseCode where code=:code")
	List<Student> getAllStudentsByCourse(@Param("code") String code);

// 10.List all Students of a particular batch
	@Query("From Student  where batchId in :batchId")
	List<Student> getAllStudentByBatchId(@Param("batchId") Integer batchId);

// 12.List all students based on the given partial name
	List<Student> findStudentByNameContaining(String name);

}
