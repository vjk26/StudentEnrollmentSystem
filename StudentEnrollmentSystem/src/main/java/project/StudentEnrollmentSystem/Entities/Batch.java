package project.StudentEnrollmentSystem.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Batches")
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Batch_Id")
	private int batchId;

	@Column(name = "Course_Code")
	@NotBlank(message = "CourseCode is required")
	private String courseCode;

	@Column(name = "Startdate")
	private LocalDate startDate;

	@Column(name = "Enddate")
	private LocalDate endDate;

	@Column(name = "Timings")
	private String timing;

	@Column(name = "Durationindays")
	@Positive(message = "Duration cannnot be negative")
	private Integer durationInDays;

	@Column(name = "Fee")
	@PositiveOrZero(message = "Fee must be >=0")
	private Double fee;

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public Integer getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Course_Code", insertable = false, updatable = false)
	@JsonIgnore
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "batch")
	@JsonIgnore
	List<Student> students = new ArrayList<>();

//	public List<Student> getStudents() {
//	return students;
//}
//
//public void setStudents(List<Student> students) {
//	this.students = students;
//}

	@Override
	public int hashCode() {
		return Objects.hash(batchId, course, courseCode, durationInDays, endDate, fee, startDate, students, timing);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		return batchId == other.batchId && Objects.equals(course, other.course)
				&& Objects.equals(courseCode, other.courseCode) && Objects.equals(durationInDays, other.durationInDays)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(fee, other.fee)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(students, other.students)
				&& Objects.equals(timing, other.timing);
	}

	@Override
	public String toString() {
		return "Batch [batchId=" + batchId + ", courseCode=" + courseCode + ", startDate=" + startDate + ", endDate="
				+ endDate + ", timing=" + timing + ", durationInDays=" + durationInDays + ", fee=" + fee + ", course="
				+ course + ", students=" + students + "]";
	}

}