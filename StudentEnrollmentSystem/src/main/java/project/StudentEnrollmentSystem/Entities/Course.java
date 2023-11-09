package project.StudentEnrollmentSystem.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Courses")
public class Course {
	@Id
	@Column(name = "Code")
	@NotBlank(message = "Course Code is required")
	@Pattern(regexp = "^[a-zA-Z0-9#]*$", message = "CourseCode should only contain letters and numbers")
	private String code;

	@Column(name = "Name")
	@NotBlank(message = "Course Name is required")
	@Pattern(regexp = "^[A-Za-z #]+$", message = "Name contain only letters and spaces")
	private String name;

	@Column(name = "Durationindays")
	@Positive(message = "Duration cannot be negative")
	private Integer durationInDays;

	@Column(name = "Fee")
	@PositiveOrZero(message = "Fee must be >=0")
	private Double fee;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToMany(mappedBy = "course")
	@JsonIgnore
	List<Batch> batches = new ArrayList<>();

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, durationInDays, fee, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(code, other.code) && Objects.equals(durationInDays, other.durationInDays)
				&& Objects.equals(fee, other.fee) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", durationInDays=" + durationInDays + ", fee=" + fee + "]";
	}

}