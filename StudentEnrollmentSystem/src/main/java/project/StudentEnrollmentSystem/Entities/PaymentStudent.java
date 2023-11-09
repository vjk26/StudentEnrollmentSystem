package project.StudentEnrollmentSystem.Entities;

import java.time.LocalDate;

public class PaymentStudent {
	private String name;
	private String email;
	private String mobile;
	private Integer batchId;
	private LocalDate dateOfJoining;
	private LocalDate payDate;
	private Double amount;
	private String payMode;

	public PaymentStudent() {
		super();
	}

	public PaymentStudent(String name, String email, String mobile, Integer batchId, LocalDate dateOfJoining,
			LocalDate payDate, Double amount, String payMode) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.batchId = batchId;
		this.dateOfJoining = dateOfJoining;
		this.payDate = payDate;
		this.amount = amount;
		this.payMode = payMode;
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

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

}
