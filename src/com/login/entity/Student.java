package com.login.entity;

public class Student {
	
	private String RFIDNumber;
	private String studentFirstName;
	private String studentLastName;
	private String yearLevel;
	private String imagePath;
	private String parentName;
	private String parentCellNumber;
	private int attendance;
	
	public Student()
	{}
	
	public Student(String studentFirstName, String studentLastName, String yearLevel, String imagePath,
			String parentName,
			String parentCellNumber,
			int attendance)
	{
		setStudentFirstName(studentFirstName);
		setStudentLastName(studentLastName);
		setYearLevel(yearLevel);
		setImagePath(imagePath);
		setParentName(parentName);
		setParentCellNumber(parentCellNumber);
		setAttendance(attendance);
	}
	
	public String getRFIDNumber() {
		return RFIDNumber;
	}
	public void setRFIDNumber(String rFIDNumber) {
		RFIDNumber = rFIDNumber;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getYearLevel() {
		return yearLevel;
	}
	public void setYearLevel(String yearLevel) {
		this.yearLevel = yearLevel;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentCellNumber() {
		return parentCellNumber;
	}
	public void setParentCellNumber(String parentCellNumber) {
		this.parentCellNumber = parentCellNumber;
	}
	public int getAttendance() {
		return attendance;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
}
