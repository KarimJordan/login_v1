package com.login.serialhandler;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import com.login.entity.Serial;

public class SerialHandler {
	private InputStream in;
	private BufferedInputStream inputStream;
	private OutputStream output;
	private BufferedReader input;
	private PrintStream printStream;
	private CommPortIdentifier portID;
	private CommPort commPort;
	
	private String inputLine;
	
	SerialPort serialPort;
	Serial serialHandling;
	
	private static final String PORT_NAMES[] = {"COM3","COM4","COM5","COM6", "COM18"};
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 19200;
	
	public void initialize()
	{
		Enumeration portNum = CommPortIdentifier.getPortIdentifiers();
		
		while(portNum.hasMoreElements())
		{
			CommPortIdentifier currId = (CommPortIdentifier) portNum.nextElement();
			for(String portNames: PORT_NAMES){
				if(currId.getName().equals(portNames)){
					portID = currId;
					break;
				}
			}
		}
		
		if(portID == null){
			JOptionPane.showMessageDialog(null, "Connect Device!",
					"Please Connect Device!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		try{
			serialPort = (SerialPort) portID.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE, 
					SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1, 
					SerialPort.PARITY_NONE);
			
			output = serialPort.getOutputStream();
			in = serialPort.getInputStream();
			printStream = new PrintStream(output);
			inputStream = new BufferedInputStream(in);
			
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
	public void printIn(String input){
		printStream.println(input);
	}
	
	public void readData() throws IOException
	{
		String sync = "";
		serialHandling = new Serial();
			if(inputStream.available() > 0){
				sync += (char) inputStream.read();
				if(inputStream.equals('\n')){
					sync.trim();
				}
				serialHandling.setOutput(sync);
			}
			sync = "";
	}
	
	public synchronized void close()
	{
		try {
			in.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(serialPort != null){
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

}
