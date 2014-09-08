package com.login.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.login.entity.Student;

public class DataBaseDriverManager {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private String connectionOracleDriver = "oracle.jdbc.driver.OracleDriver";
	private String connectionURLOracle = "jdbc:oracle:thin:@localhost:1521:LISTENER3";
	private String connectionUserNameOracle = "sys as sysdba";
	private String connectionUserPasswordOracle = "Apple123";
	
	private String connectionMySQLDriver = "com.mysql.jdbc.Driver";
	private String connectionURLMySQL = "jdbc:mysql://localhost:3306/STUDENT";
	private String connectionNameMySQL = "root";
	private String connectionPasswordMySQL = "panduck27";
	
	private PreparedStatement selectFromStudents = null;
	private PreparedStatement insertIntoStudents = null;
	private PreparedStatement insertAttendance = null;
	private PreparedStatement queryAttendance = null;
	private PreparedStatement editStudent = null;
	private PreparedStatement deleteStudent = null;
	private String selectFromStudentsQuery = "SELECT * FROM STUDENTS " +
			"WHERE RFID_Number = ?";
	private String insertInfoStudentsQuery = "INSERT INTO STUDENTS (RFID_Number, STUDENT_FIRST_NAME, " +
			"STUDENT_LAST_NAME, YEAR_LEVEL, IMAGE_PATH, PARENT_NAME, PARENT_CELL_NUM, COURSE) VALUES (?,?,?,?,?,?,?,?)";
	private String editStudentQuery =  "UPDATE STUDENTS SET STUDENT_FIRST_NAME = ?, STUDENT_LAST_NAME = ?, YEAR_LEVEL = ?,"
			+ "IMAGE_PATH = ?, PARENT_NAME = ?, PARENT_CELL_NUM = ?, COURSE = ? WHERE RFID_Number = ?";
	private String deleteStudentQuery = "DELETE FROM STUDENTS WHERE RFID_Number = ?";
	private String insertAttendanceQuery = "UPDATE STUDENTS SET ATTENDANCE = ? WHERE RFID_Number = ?";
	private String updateAttendanceQuery = "UPDATE STUDENTS SET ATTENDANCE = 1 WHERE RFID_Number = ?";
	
	//query sample
	private String queryStudent = "SELECT * FROM SCH_DELIVERY_INFO_EVENT";
	
	public DataBaseDriverManager()
	{
		try{
			connection = DriverManager.getConnection(connectionURLMySQL, connectionNameMySQL, connectionPasswordMySQL);
		
			selectFromStudents = connection.prepareStatement(selectFromStudentsQuery);
			insertIntoStudents = connection.prepareStatement(insertInfoStudentsQuery);
			insertAttendance = connection.prepareStatement(insertAttendanceQuery);
			queryAttendance = connection.prepareStatement(updateAttendanceQuery);
			editStudent = connection.prepareStatement(editStudentQuery);
			deleteStudent = connection.prepareStatement(deleteStudentQuery);
		
			if(connection != null){
				System.out.println("Successful Connection");
			}else{
				System.out.println("Connection Failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<Student> getStudentInfo(String RFIDNumber)
	{
		List<Student> results = null;
		ResultSet resultSet = null;		
		
		try{
			
			selectFromStudents.setString(1, RFIDNumber);
			
			resultSet = selectFromStudents.executeQuery();
			results = new ArrayList<Student>();
			
			while(resultSet.next())
			{
				results.add(new Student(
						resultSet.getString("STUDENT_FIRST_NAME"),
						resultSet.getString("STUDENT_LAST_NAME"),
						resultSet.getString("YEAR_LEVEL"),
						resultSet.getString("IMAGE_PATH"),
						resultSet.getString("PARENT_NAME"),
						resultSet.getString("PARENT_CELL_NUM"),
						resultSet.getString("COURSE"),
						resultSet.getInt("ATTENDANCE")
						));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return results;
	}
	
	public int addStudent(String rfidNumber, 
			String studentFirstName, 
			String studentLastName,
			String yearLevel, 
			String imagePath,
			String parentName,
			String parentCellNum,
			String courseName)
	{
		int result = 0;
		if(isNumber(yearLevel) == false)
		{
			JOptionPane.showMessageDialog(null, "Incorrect Year Level", "Incorrect Year Level", JOptionPane.WARNING_MESSAGE);
		}else if(studentFirstName.isEmpty() || studentLastName.isEmpty() || yearLevel.isEmpty() || imagePath.isEmpty() || parentName.isEmpty() || parentCellNum.isEmpty()){
			JOptionPane.showMessageDialog(null, "Please Fill Out All TextFields", "Incorrect Input", JOptionPane.WARNING_MESSAGE);
		}else if(isNumber(yearLevel) == true){
		try{	
				insertIntoStudents.setString(1, rfidNumber);
				insertIntoStudents.setString(2, studentFirstName);
				insertIntoStudents.setString(3, studentLastName);
				insertIntoStudents.setString(4,  yearLevel);
				insertIntoStudents.setString(5, imagePath);
				insertIntoStudents.setString(6, parentName);
				insertIntoStudents.setString(7, parentCellNum);
				insertIntoStudents.setString(8, courseName);
				result = insertIntoStudents.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			close();
			}
		}
		return result;
	}
	
	public int editStudent(String rfidNumber, 
			String studentFirstName, 
			String studentLastName,
			String yearLevel, 
			String imagePath,
			String parentName,
			String parentCellNum,
			String courseName)
	{
		int result = 0;
		if(isNumber(yearLevel) == false)
		{
			JOptionPane.showMessageDialog(null, "Incorrect Year Level", "Incorrect Year Level", JOptionPane.WARNING_MESSAGE);
		}else if(studentFirstName.isEmpty() || studentLastName.isEmpty() || yearLevel.isEmpty() || imagePath.isEmpty() || parentName.isEmpty() || parentCellNum.isEmpty()){
			JOptionPane.showMessageDialog(null, "Please Fill Out All TextFields", "Incorrect Input", JOptionPane.WARNING_MESSAGE);
		}else if(isNumber(yearLevel) == true){
		try{	
				editStudent.setString(1, studentFirstName);
				editStudent.setString(2, studentLastName);
				editStudent.setString(3,  yearLevel);
				editStudent.setString(4, imagePath);
				editStudent.setString(5, parentName);
				editStudent.setString(6, parentCellNum);
				editStudent.setString(7, courseName);
				editStudent.setString(8, rfidNumber);
				result = editStudent.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			close();
			}
		}
		return result;
	}
	
	public int deleteStudent(String rfidNumber)
	{
		int result = 0;
		try {
			deleteStudent.setString(1, rfidNumber);
			result = deleteStudent.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	
	public int addAttendance(int attendance, String rfidNumber)
	{
		int result = 0;
		try{
			insertAttendance.setInt(1, attendance);
			insertAttendance.setString(2, rfidNumber);
			result = insertAttendance.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return result;
	}
	
	public int updateAttendance(String rfidNumber)
	{
		int result = 0;
		try {
			queryAttendance.setString(1, rfidNumber);
			result = queryAttendance.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return result;
	}
	
	public void close()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isNumber(String value)
	{
		if(value.matches("[0-9]+"))
		{
			if(Integer.parseInt(value) > 4 || Integer.parseInt(value) < 1)
			{
				return false;
			}else{
			return true;
			}
		}else if(value.equals(""))
		{
			return false;
		}
		return false;
	}

}
