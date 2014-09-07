package com.login.serialhandler;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import com.login.entity.Serial;
import com.login.interfaceDefinition.LoginInterface;

public class SerialClass {
	
	private static Serial serial = new Serial();
	private LoginInterface LInterface;
	
	public SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
																				// OS
																				// X
			"/dev/ttyUSB0", // Linux
			"COM18", // Windows
	};

	public static BufferedReader input;
	public static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;
	public String sample;
	private static String inputLine;
	public void initialize() {
		CommPortIdentifier portId = null;
		LInterface = new LoginInterface();
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			JOptionPane.showMessageDialog(null, "Connect Device!",
					"Please Connect Device!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			char ch = 1;
			output.write(ch);

			// add event listeners
			serialPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					// TODO Auto-generated method stub
					if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
					try {
						inputLine = input.readLine();
						serial.setOutput(inputLine);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				}
			});
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/*public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				inputLine = input.readLine();
				//System.out.println(inputLine);
				
				//sample = inputLine;
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}*/
	
	public String dataOutGet()
	{
		System.out.println(serial.getOutput());
		return serial.getOutput();
	}

	public static synchronized void writeData(String data) {
		System.out.println("Sent: " + data);
		try {
			output.write(data.getBytes());
		} catch (Exception e) {
			System.out.println("could not write to port");
		}
	}

	public static void main(String[] args) throws Exception {
		SerialClass main = new SerialClass();
		main.initialize();
		main.dataOutGet();
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1500);
					//writeData("2");
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Started");
	}
}