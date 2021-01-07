package com.example.model;

public class Reimbursement {

	//STATE
	private int reimbursementId;
	private double reimbursementAmount;
	private String reimbursementAuthor;
	private String reimbursementResolver;
	private String reimbursementStatusId;
	private String reimbursementTypeId;
	private String reimbursementSubmitted;
	private String reimbursementResolved;
	private String reimbursementDescription;
	private String reimbursementReciept;
	private String authorUsername;
	
	//CONSTRUCTORS
	public Reimbursement() {
		// TODO Auto-generated constructor stub
	}
	
	public Reimbursement(String reimbursementId, String reimbursementAmount, String reimbursementAuthor,
			String reimbursementResolver, String reimbursementStatusId, String reimbursementTypeId,
			String reimbursementSubmitted, String reimbursementResolved, String reimbursementDescription,
			String reimbursementReciept, String authorUsername) {
		super();
		this.reimbursementId = Integer.parseInt(reimbursementId);
		this.reimbursementAmount = Double.parseDouble(reimbursementAmount);
		this.reimbursementAuthor = reimbursementAuthor;
		this.reimbursementResolver = reimbursementResolver;
		this.reimbursementStatusId = reimbursementStatusId;
		this.reimbursementTypeId = reimbursementTypeId;
		this.reimbursementSubmitted = reimbursementSubmitted;
		this.reimbursementResolved = reimbursementResolved;
		this.reimbursementDescription = reimbursementDescription;
		this.reimbursementReciept = reimbursementReciept;
		this.authorUsername = authorUsername;
	}









	//GETTERS SETTERS
	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public double getReimbursementAmount() {
		return reimbursementAmount;
	}

	public void setReimbursementAmount(double reimbursementAmount) {
		this.reimbursementAmount = reimbursementAmount;
	}

	public String getReimbursementAuthor() {
		return reimbursementAuthor;
	}

	public void setReimbursementAuthor(String reimbursementAuthor) {
		this.reimbursementAuthor = reimbursementAuthor;
	}

	public String getReimbursementResolver() {
		return reimbursementResolver;
	}

	public void setReimbursementResolver(String reimbursementResolver) {
		this.reimbursementResolver = reimbursementResolver;
	}

	public String getReimbursementStatusId() {
		return reimbursementStatusId;
	}

	public void setReimbursementStatusId(String reimbursementStatusId) {
		this.reimbursementStatusId = reimbursementStatusId;
	}

	public String getReimbursementTypeId() {
		return reimbursementTypeId;
	}

	public void setReimbursementTypeId(String reimbursementTypeId) {
		this.reimbursementTypeId = reimbursementTypeId;
	}

	public String getReimbursementSubmitted() {
		return reimbursementSubmitted;
	}

	public void setReimbursementSubmitted(String reimbursementSubmitted) {
		this.reimbursementSubmitted = reimbursementSubmitted;
	}

	public String getReimbursementResolved() {
		return reimbursementResolved;
	}

	public void setReimbursementResolved(String reimbursementResolved) {
		this.reimbursementResolved = reimbursementResolved;
	}

	public String getReimbursementDescription() {
		return reimbursementDescription;
	}

	public void setReimbursementDescription(String reimbursementDescription) {
		this.reimbursementDescription = reimbursementDescription;
	}

	public String getReimbursementReciept() {
		return reimbursementReciept;
	}

	public void setReimbursementReciept(String reimbursementReciept) {
		this.reimbursementReciept = reimbursementReciept;
	}
	
	public String getAuthorUsername() {
		return authorUsername;
	}

	///////TO STRING//////
	@Override
	public String toString() {
		return "Reimbursement [reimbursementId=" + reimbursementId + ", reimbursementAmount=" + reimbursementAmount
				+ ", reimbursementAuthor=" + reimbursementAuthor + ", reimbursementResolver=" + reimbursementResolver
				+ ", reimbursementStatusId=" + reimbursementStatusId + ", reimbursementTypeId=" + reimbursementTypeId
				+ ", reimbursementSubmitted=" + reimbursementSubmitted + ", reimbursementResolved="
				+ reimbursementResolved + ", reimbursementDescription=" + reimbursementDescription
				+ ", reimbursementReciept=" + reimbursementReciept + ", authorUsername=" + authorUsername + "]";
	}	
	
}
