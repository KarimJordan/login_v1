package com.login.databaseConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseDriverManager {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private String connectionURLOracle = "jdbc:oracle:thin:@localhost:1523:TEST";
	private String connectionUserNameOracle = "sys as sysdba";
	private String connectionUserPasswordOracle = "Apple123";
	
	//query sample
	private String queryStudent = "SELECT * FROM SCH_DELIVERY_INFO_EVENT";
	
	public void establishConnection() throws SQLException{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(connectionURLOracle, connectionUserNameOracle, connectionUserPasswordOracle);
		}catch(ClassNotFoundException e){
			e.getMessage();
		}
		
		if(connection != null){
			System.out.println("Successful Connection");
		}else{
			System.out.println("Connection Failed");
		}
			
	}
	
	public void querySample() {
		try{
			preparedStatement = connection.prepareStatement(queryStudent);
			while(preparedStatement.getResultSet().next()){
				
			}
		}catch(SQLException e){
			e.getMessage();
		}
	}

}
