package com.login.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import oracle.jdbc.proxy.annotation.Pre;

import com.login.entity.Student;
import com.login.entity.StudentLog;

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
	
/*	private String connectionMySQLDriver = "com.mysql.jdbc.Driver";
	private String connectionURLMySQL = "jdbc:mysql://localhost:3307/TEST";
	private String connectionNameMySQL = "root";
	private String connectionPasswordMySQL = "12345";
	*/
	private PreparedStatement selectFromStudents = null;
	private PreparedStatement insertIntoStudents = null;
	private PreparedStatement insertAttendance = null;
	private PreparedStatement editStudent = null;
	private PreparedStatement deleteStudent = null;
	private PreparedStatement logStudent = null;
	private PreparedStatement insertLogin = null;
	private PreparedStatement insertLogout = null;
	private PreparedStatement viewLogin = null;
	private PreparedStatement viewAllRecords = null;
	private PreparedStatement viewLogOnFirstName = null;
	private PreparedStatement viewLogOnLastName = null;
	private PreparedStatement viewLogOnCourse = null;
	private PreparedStatement viewLogOnInput = null;
	private PreparedStatement viewDate = null;
	private String selectFromStudentsQuery = "SELECT * FROM STUDENTS " +
			"WHERE RFID_Number = ?";
	private String insertInfoStudentsQuery = "INSERT INTO STUDENTS (RFID_Number, STUDENT_FIRST_NAME, " +
			"STUDENT_LAST_NAME, YEAR_LEVEL, IMAGE_PATH, PARENT_NAME, PARENT_CELL_NUM, COURSE, LOG) VALUES (?,?,?,?,?,?,?,?, ?)";
	private String editStudentQuery =  "UPDATE STUDENTS SET STUDENT_FIRST_NAME = ?, STUDENT_LAST_NAME = ?, YEAR_LEVEL = ?,"
			+ "IMAGE_PATH = ?, PARENT_NAME = ?, PARENT_CELL_NUM = ?, COURSE = ? WHERE RFID_Number = ?";
	private String deleteStudentQuery = "DELETE FROM STUDENTS WHERE RFID_Number = ?";
	private String insertAttendanceQuery = "UPDATE STUDENTS SET ATTENDANCE = ? WHERE RFID_Number = ?";
	private String logQuery = "UPDATE STUDENTS SET LOG = ? WHERE RFID_Number = ?";
	private String insertLoginQuery = "INSERT INTO STUDENT_LOG (firstName, lastName, studentNumber, course, log_in) " +
			"VALUES (?,?,?,?,?)";
	private String insertLogoutQuery = "UPDATE STUDENT_LOG SET log_out = ? WHERE log_in = ? ";
	private String viewLoginQuery = "SELECT * FROM STUDENT_LOG WHERE studentNumber = ?";
	private String viewLogOnFirstNameQuery = "SELECT * FROM STUDENT_LOG WHERE FIRSTNAME =?";
	private String viewLogOnLastNameQuery = "SELECT * FROM STUDENT_LOG WHERE LASTNAME = ?";
	private String viewLogOnCourseQuery = "SELECT * FROM STUDENT_LOG WHERE COURSE = ?";
	private String viewLogOnInputQuery = "SELECT * FROM STUDENT_LOG WHERE FIRSTNAME = ? OR LASTNAME = ? OR COURSE = ?";
	private String viewDateQuery = "SELECT * FROM STUDENT_LOG WHERE LOG_IN = ?";
	
	//query sample
	private String viewAllQuery = "SELECT * FROM STUDENT_LOG";
	
	public DataBaseDriverManager()
	{
		try{
			connection = DriverManager.getConnection(connectionURLMySQL, connectionNameMySQL, connectionPasswordMySQL);
		
			selectFromStudents = connection.prepareStatement(selectFromStudentsQuery);
			insertIntoStudents = connection.prepareStatement(insertInfoStudentsQuery);
			insertAttendance = connection.prepareStatement(insertAttendanceQuery);
			editStudent = connection.prepareStatement(editStudentQuery);
			deleteStudent = connection.prepareStatement(deleteStudentQuery);
			logStudent = connection.prepareStatement(logQuery);
			insertLogin = connection.prepareStatement(insertLoginQuery);
			insertLogout = connection.prepareStatement(insertLogoutQuery);
			viewLogin = connection.prepareStatement(viewLoginQuery);
			viewAllRecords = connection.prepareStatement(viewAllQuery);
			viewLogOnFirstName = connection.prepareStatement(viewLogOnFirstNameQuery);
			viewLogOnLastName = connection.prepareStatement(viewLogOnLastNameQuery);
			viewLogOnCourse = connection.prepareStatement(viewLogOnCourseQuery);
			viewLogOnInput = connection.prepareStatement(viewLogOnInputQuery);
			if(connection != null){
				System.out.println("Successful Connection");
			}else{
				System.out.println("Connection Failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<StudentLog> getStudentLog(){
		List<StudentLog> results = null;
		ResultSet resultSet = null;
		
		try{
			resultSet = viewAllRecords.executeQuery();
			results = new ArrayList<StudentLog>();
			while(resultSet.next()){
				results.add(new StudentLog(
						resultSet.getString("FIRSTNAME"),
						resultSet.getString("LASTNAME"),
						resultSet.getString("STUDENTNUMBER"),
						resultSet.getString("COURSE"),
						resultSet.getString("LOG_IN"),
						resultSet.getString("LOG_OUT")
						));
			}
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return results;
	}
	
	public List<StudentLog> getStudentLogOnDate(String logIn){
		List<StudentLog> results = null;
		ResultSet resultSet = null;
		try{
			viewDate.setString(1, logIn);
			resultSet = viewDate.executeQuery();
			results = new ArrayList<StudentLog>();
			while(resultSet.next()){
				StudentLog logList = new StudentLog();
				logList.setFirstName(resultSet.getString("FIRSTNAME"));
				logList.setLastName(resultSet.getString("LASTNAME"));
				logList.setStudentNumber(resultSet.getString("STUDENTNUMBER"));
				logList.setCourse(resultSet.getString("COURSE"));
				logList.setLog_in(resultSet.getString("LOG_IN"));
				logList.setLog_out(resultSet.getString("LOG_OUT"));
				results.add(logList);
			}
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return results;
	}
	
	public List<StudentLog> getStudentLogOnInput(String firstName, String lastName, String course){
		List<StudentLog> results = null;
		ResultSet resultSet = null;
		if(lastName.equals("") && course.equals("")){
			try{
				viewLogOnFirstName.setString(1, firstName);
				resultSet = viewLogOnFirstName.executeQuery();
				results = new ArrayList<StudentLog>();
				while(resultSet.next()){
					StudentLog logList = new StudentLog();
					logList.setFirstName(resultSet.getString("FIRSTNAME"));
					logList.setLastName(resultSet.getString("LASTNAME"));
					logList.setStudentNumber(resultSet.getString("STUDENTNUMBER"));
					logList.setCourse(resultSet.getString("COURSE"));
					logList.setLog_in(resultSet.getString("LOG_IN"));
					logList.setLog_out(resultSet.getString("LOG_OUT"));
					results.add(logList);
				}
			}catch(SQLException e){
				e.printStackTrace();
				close();
			}
		}else if(firstName.equals("") && course.equals("")){
			try{
				viewLogOnLastName.setString(1, lastName);
				resultSet = viewLogOnLastName.executeQuery();
				results = new ArrayList<StudentLog>();
				while(resultSet.next()){
					StudentLog logList = new StudentLog();
					logList.setFirstName(resultSet.getString("FIRSTNAME"));
					logList.setLastName(resultSet.getString("LASTNAME"));
					logList.setStudentNumber(resultSet.getString("STUDENTNUMBER"));
					logList.setCourse(resultSet.getString("COURSE"));
					logList.setLog_in(resultSet.getString("LOG_IN"));
					logList.setLog_out(resultSet.getString("LOG_OUT"));
					results.add(logList);
				}
			}catch(SQLException e){
				e.printStackTrace();
				close();
			}
		}else if(firstName.equals("") && lastName.equals("")){
			try{
				viewLogOnCourse.setString(1, course);
				resultSet = viewLogOnCourse.executeQuery();
				results = new ArrayList<StudentLog>();
				while(resultSet.next()){
					StudentLog logList = new StudentLog();
					logList.setFirstName(resultSet.getString("FIRSTNAME"));
					logList.setLastName(resultSet.getString("LASTNAME"));
					logList.setStudentNumber(resultSet.getString("STUDENTNUMBER"));
					logList.setCourse(resultSet.getString("COURSE"));
					logList.setLog_in(resultSet.getString("LOG_IN"));
					logList.setLog_out(resultSet.getString("LOG_OUT"));
					results.add(logList);
				}
			}catch(SQLException e){
				e.printStackTrace();
				close();
			}
		}else if(firstName.equals("") || lastName.equals("") || course.equals("")){
			try{
				viewLogOnInput.setString(1, firstName);
				viewLogOnInput.setString(2, lastName);
				viewLogOnInput.setString(3, course);
				resultSet = viewLogOnInput.executeQuery();
				results = new ArrayList<StudentLog>();
				while(resultSet.next()){
					StudentLog logList = new StudentLog();
					logList.setFirstName(resultSet.getString("FIRSTNAME"));
					logList.setLastName(resultSet.getString("LASTNAME"));
					logList.setStudentNumber(resultSet.getString("STUDENTNUMBER"));
					logList.setCourse(resultSet.getString("COURSE"));
					logList.setLog_in(resultSet.getString("LOG_IN"));
					logList.setLog_out(resultSet.getString("LOG_OUT"));
					results.add(logList);
				}
			}catch(SQLException e){
				e.printStackTrace();
				close();
			}
		}
		return results;
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
						resultSet.getInt("ATTENDANCE"),
						resultSet.getString("LOG")
						));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return results;
	}
	
	public int addLogOut(String log_in, String log_out)
	{
/*		System.out.println("IN: " + log_in);
		System.out.println("OUT: " + log_out);*/
		int result = 0;
		try{
			insertLogout.setString(1, log_in);
			insertLogout.setString(2, log_out);
			result = insertLogout.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return result;
	}
	
	public int addLog(String firstName, String lastName, 
			String studentNumber, String course,
			String log_in){
		int result = 0;
		try{
				insertLogin.setString(1, firstName);
				insertLogin.setString(2, lastName);
				insertLogin.setString(3, studentNumber);
				insertLogin.setString(4, course);
				insertLogin.setString(5, log_in);
				result = insertLogin.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			close();
		}
		return result;
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
				insertIntoStudents.setString(9, "0");
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
	
	public int updateLog(String Log, String rfidNumber){
		int result = 0;
		try{
		logStudent.setString(1, Log);
		logStudent.setString(2, rfidNumber);
		result = logStudent.executeUpdate();
		}catch(SQLException e){
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
