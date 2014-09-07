package com.login.serialhandler;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import com.login.entity.Serial;

public class SerialHandler implements SerialPortEventListener{
	private InputStream in;
	//private  BufferedInputStream inputStream;
	private static OutputStream output;
	public static BufferedReader input;
	private PrintStream printStream;
	private CommPortIdentifier portID;
	private CommPort commPort;
	
	private static String inputLine;
	
	SerialPort serialPort;
	Serial serialHandling;
	
	private static final String PORT_NAMES[] = {"COM3","COM4","COM5","COM6", "COM18"};
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	
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
			
			/*output = serialPort.getOutputStream();
			in = serialPort.getInputStream();
			
			printStream = new PrintStream(output);*/
			//inputOne = new BufferedInputStream(new InputStreamReader(serialPort.getInputStream()));
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			serialPort.addEventListener(this);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
	public void printIn(String input){
		printStream.println(input);
	}
	
	public void readData3()
	{
		try {
			serialPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent oEvent) {
					// TODO Auto-generated method stub
					if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE){
						try {
							inputLine = input.readLine();
							System.out.println(inputLine);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readData2()
	{
		byte[] buffer = new byte[1024];
		int data;
		try {
			int len = 0;
			while ((data = in.read()) > -1) {
				if (data == '\n')
					break;
				buffer[len++] = (byte) data;
				String string = new String(buffer, 0, len);
				System.out.println(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized void sendData(String data)
	{
		System.out.println("Sent: " + data);
		try {
			output.write(data.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readData()
	{
		String sync = "";
		String message = "";
		serialHandling = new Serial();
			try {
				while(in.available() > 0){
					sync += (char) in.read();
					message = sync;
					System.out.println(message);
					if(in.equals('\n')){
						message.trim();
					}
					serialHandling.setOutput(sync);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sync = "";
		return message;
	}
	
	public synchronized void close()
	{
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(serialPort != null){
			serialPort.removeEventListener();
			serialPort.close();
		}
	}


	@Override
	public void serialEvent(SerialPortEvent oEvent) {
		// TODO Auto-generated method stub
		if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			try {
				inputLine = input.readLine();
				System.out.println(inputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
