package project.StudentEnrollmentSystem.Entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Payment_Id")
	private int paymentId;

	@Column(name = "Student_Id")
	private int studentId;

	@Column(name = "Amount")
	@PositiveOrZero(message = "Amount must be >=0")
	private Double amount;

	@Column(name = "Paydate")
	private LocalDate payDate;

	@Column(name = "Paymode")
	@Pattern(regexp = "^[UNC]$", message = "Invalid PayMode. It must be 'U', 'N', or 'C'.")
	private String payMode;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@OneToOne(mappedBy = "payment")
	private Student student;



	public Payment() {
		super();
	}

	public Payment(int paymentId, int studentId, Double amount, LocalDate payDate, String payMode, Student student) {
		super();
		this.paymentId = paymentId;
		this.studentId = studentId;
		this.amount = amount;
		this.payDate = payDate;
		this.payMode = payMode;
		this.student = student;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, payDate, payMode, paymentId, studentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(payDate, other.payDate)
				&& Objects.equals(payMode, other.payMode) && paymentId == other.paymentId
				&& studentId == other.studentId;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", studentId=" + studentId + ", amount=" + amount + ", payDate="
				+ payDate + ", payMode=" + payMode + "]";
	}

}
