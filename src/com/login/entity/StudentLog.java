package com.login.entity;

public class StudentLog {
	
	private String firstName;
	private String lastName;
	private String studentNumber;
	private String course;
	private String log_in;
	private String log_out;
	
	public StudentLog()
	{}

	public StudentLog(String firstName,
			String lastName,
			String studentNumber, 
			String course,
			String log_in,
			String log_out) {
	
		setFirstName(firstName);
		setLastName(lastName);
		setStudentNumber(studentNumber);
		setCourse(course);
		setLog_in(log_in);
		setLog_out(log_out);
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getLog_in() {
		return log_in;
	}

	public void setLog_in(String log_in) {
		this.log_in = log_in;
	}

	public String getLog_out() {
		return log_out;
	}

	public void setLog_out(String log_out) {
		this.log_out = log_out;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	
	
	
	
}	
