package project.StudentEnrollmentSystem.Entities;

import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Student_Id")
	private int studentId;

	@Column(name = "Name")
	@NotBlank(message = "Student Name is required")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Name contain only letters and spaces")
	private String name;

	@Column(name = "Email")
	@Email(message = "Invalid email address")
	private String email;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "Batch_Id")
	private Integer batchId;

	@Column(name = "Dateofjoining")
	private LocalDate dateOfJoining;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_Id", insertable = false, updatable = false)
	@JsonIgnore
	private Batch batch;

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_Id", referencedColumnName = "Student_Id")
	@JsonIgnore
	private Payment payment;

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(batchId, dateOfJoining, email, mobile, name, payment, studentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(batchId, other.batchId) && Objects.equals(dateOfJoining, other.dateOfJoining)
				&& Objects.equals(email, other.email) && Objects.equals(mobile, other.mobile)
				&& Objects.equals(name, other.name) && Objects.equals(payment, other.payment)
				&& studentId == other.studentId;
	}

	public Student() {
		super();
	}

	public Student(int studentId, String name, String email, String mobile, Integer batchId, LocalDate dateOfJoining,
			Batch batch, Payment payment) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.batchId = batchId;
		this.dateOfJoining = dateOfJoining;
		this.batch = batch;
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ",name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", batchId=" + batchId + ", dateOfJoining=" + dateOfJoining + ", payment=" + payment + "]";
	}


}
