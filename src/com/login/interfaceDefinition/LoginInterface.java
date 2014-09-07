package com.login.interfaceDefinition;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.login.databaseConnection.DataBaseDriverManager;
import com.login.entity.Serial;
import com.login.entity.Student;
import com.login.serialhandler.SerialClass;
import com.login.serialhandler.SerialHandler;

public class LoginInterface implements ActionListener, SerialPortEventListener{
	
	//serialCod
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;
	public String sample;
	private static String inputLine;
	public SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
																				// OS
																				// X
			"/dev/ttyUSB0", // Linux
			"COM18", // Windows
	};
	private PrintStream printStream;
	
	//serialPort configs new
	private InputStream serialIn;
	private OutputStream serialOut;
	private BufferedReader serialReader;
	
	private JFrame frmLoginSystem;
	
	private JPanel logo_panel;
	private JPanel picture_panel;
	private JPanel info_panel;
	
	private JLabel lblCurrentTime;
	private JLabel lblName;
	private JLabel lblYearLevel;
	private JLabel lblStudentName;
	public JLabel lblYear;
	private JLabel lblPictureLabel;
	
	private JMenuBar menuBar;
	private JMenu mnSystem;
	private JMenu mnAbout;
	private JMenuItem mntmRegister;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	
	private List<Student> results;
	private Student student;
	private int numberOfEntries = 0;
	private int currentEntries;
	private DataBaseDriverManager databaseDriverManager;
	private static Registry registry;
	//private static SerialHandler serial;
	//private static SerialClass serialClass;
	private static Serial serialInput = new Serial();
	public JTextField txtRFIDNumberField;
	private String parentName;
	private String parentCell;
	private int attendance;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
					registry = new Registry();
					//serial = new SerialHandler();
					window.frmLoginSystem.setVisible(true);
					window.begin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public LoginInterface() {
		initialize();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				displayTime();
			}
		}).start();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(){
		
		/*CommPortIdentifier portId = null;
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
			printStream = new PrintStream(output);

			// add event listeners
			serialPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					// TODO Auto-generated method stub
					if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
					try {
						inputLine = input.readLine();
						System.out.println(inputLine);
						txtRFIDNumberField.setText(inputLine);
						inputLine = "";
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
		*/
		databaseDriverManager = new DataBaseDriverManager();
		student = new Student();
		
		frmLoginSystem = new JFrame();
		frmLoginSystem.setTitle("Login System");
		frmLoginSystem.setBounds(100, 100, 650, 510);
		frmLoginSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmLoginSystem.setExtendedState(frmLoginSystem.getExtendedState() | frmLoginSystem.MAXIMIZED_BOTH);
		
		logo_panel = new JPanel();
		logo_panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		
		picture_panel = new JPanel();
		picture_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPictureLabel = new JLabel();
		picture_panel.add(lblPictureLabel);
		
		info_panel = new JPanel();
		info_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lblCurrentTime = new JLabel("SAMPLE");
		lblCurrentTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentTime.setFont(new Font("Verdana", Font.BOLD, 40));
		
		txtRFIDNumberField = new JTextField();
		txtRFIDNumberField.setColumns(10);
		//txtRFIDNumberField.setVisible(false);
		//txtRFIDNumberField.setEnabled(false);
		txtRFIDNumberField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						displayStudentInfo();
						delay();
					}
				}).start();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frmLoginSystem.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(info_panel, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(165)
							.addComponent(lblCurrentTime, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
							.addGap(179))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(256)
							.addComponent(txtRFIDNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblCurrentTime, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(4)
					.addComponent(txtRFIDNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addComponent(info_panel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
					.addGap(17))
		);
		
		lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Verdana", Font.BOLD, 16));
		
		lblYearLevel  = new JLabel("Year Level:");
		lblYearLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblYearLevel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		lblStudentName = new JLabel("Juan Dela Cruz");
		lblStudentName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentName.setFont(new Font("Verdana", Font.BOLD, 16));
		
		lblYear = new JLabel();
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setFont(new Font("Verdana", Font.BOLD, 16));
		GroupLayout gl_info_panel = new GroupLayout(info_panel);
		gl_info_panel.setHorizontalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblYearLevel, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_info_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblStudentName, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		gl_info_panel.setVerticalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStudentName, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblYearLevel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblYear, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addGap(39))
		);
		info_panel.setLayout(gl_info_panel);
		frmLoginSystem.getContentPane().setLayout(groupLayout);
		
		menuBar = new JMenuBar();
		frmLoginSystem.setJMenuBar(menuBar);
		
		mnSystem = new JMenu("System");
		menuBar.add(mnSystem);
		
		mntmRegister = new JMenuItem("Register");
		mntmRegister.addActionListener(this);
		mnSystem.add(mntmRegister);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnSystem.add(mntmExit);
		
		mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(this);
		mnAbout.add(mntmAbout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == mntmRegister)
		{
			registry.frmRegistry.setVisible(true);
		}else if(source == mntmExit){
			System.exit(0);
		}else if(source == mntmAbout){
			/*JOptionPane.showMessageDialog(null, 
					"Developed For Wesleyan University Login System", 
					"About Software", JOptionPane.INFORMATION_MESSAGE);*/
			//String go = readArduino();
			//System.out.println(go);
			//readArduino();
			System.out.println(parentName + " " + parentCell + " " + attendance);
			sendToArduino();
		}
	}
	
	private void displayTime()
	{
		while(true){
			lblCurrentTime.setText(DateFormat.getTimeInstance().format(new Date()));
			Thread.currentThread();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	private void sendToArduino()
	{
		/*//SerialHandler serial = new SerialHandler();
		String cellNumber;
		
		cellNumber = student.getParentCellNumber();
		serial.initialize();
		serial.sendData("ON");
		//serial.printIn("ON");
		serial.close();*/
		/*printStream.println("WOW");
		close();*/
		try {
			serialOut.write(parentCell.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void displayStudentInfo()
	{
		results = databaseDriverManager.getStudentInfo(txtRFIDNumberField.getText());
		if(results.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Student Not Registered!", "Unregistered Student", JOptionPane.ERROR_MESSAGE);
		}else{
		numberOfEntries = results.size();
		if(numberOfEntries != 0){
			currentEntries = 0;
			student = results.get(currentEntries);
			File file = new File(student.getImagePath());
			BufferedImage picture = null;
			try {
				picture = ImageIO.read(file);
				Image dmg = picture.getScaledInstance(288, 218, Image.SCALE_SMOOTH);
				lblPictureLabel.setIcon(new ImageIcon(dmg));
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(student.getYearLevel());
			parentCell = student.getParentCellNumber();
			parentName = student.getParentName();
			attendance = student.getAttendance();
			lblStudentName.setText(student.getStudentFirstName() + " " + student.getStudentLastName());
			switch (Integer.parseInt(student.getYearLevel())) {
			case 1:
				lblYear.setText("1st Year");
				break;
			case 2:
				lblYear.setText("2nd Year");
				break;
			case 3:
				lblYear.setText("3rd Year");
				break;
			case 4:
				lblYear.setText("4th Year");
				break;
			default:
				break;
			}
		  }
	    }
	 }
	
	private void delay()
	{
		Thread.currentThread();
		
		try{
			Thread.sleep(15000);
			lblStudentName.setText("Student Name");
			lblYear.setText("Year Level");
			lblPictureLabel.setIcon(null);
			sendToArduino();
			//System.out.println(parentCell);
			/*try {
				serialOut.write("L1".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	//code
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}


	public void begin() throws Exception{
		CommPortIdentifier port  = CommPortIdentifier.getPortIdentifier("COM18");
		CommPort commPOrt = port.open(this.getClass().getName(), 2000);
		serialPort  = (SerialPort) commPOrt;
		serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialIn = serialPort.getInputStream();
		serialOut = serialPort.getOutputStream();
		serialReader = new BufferedReader(new InputStreamReader(serialIn));
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	}
	
	private void dataInput(String value)
	{
		
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		try{
			String line = serialReader.readLine();
			System.out.println(line);
			//if(line.en)
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
