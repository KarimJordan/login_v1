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
import java.text.SimpleDateFormat;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.login.databaseConnection.DataBaseDriverManager;
import com.login.entity.Serial;
import com.login.entity.Student;

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
	private JLabel lblLogoLabel;
	
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
	private String firstName;
	private String lastName;
	private String courseName;
	private String yearLevel;
	private String parentName;
	private String parentCell;
	private String fileLocation;
	private String day;
	private int attendance;
	private JMenuItem mntmEdit;
	private JMenuItem mntmDelete;
	private JLabel lblCourseName;
	private JLabel lblSpecifyCourse;
	

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
					window.frmLoginSystem.setBackground(Color.GREEN);
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
		
		databaseDriverManager = new DataBaseDriverManager();
		student = new Student();
		
		frmLoginSystem = new JFrame();
		frmLoginSystem.setTitle("Login System");
		frmLoginSystem.setBounds(100, 100, 845, 650);
		frmLoginSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginSystem.getContentPane().setBackground(Color.GREEN.darker().darker());
		//frmLoginSystem.setExtendedState(frmLoginSystem.getExtendedState() | frmLoginSystem.MAXIMIZED_BOTH);
		//frmLoginSystem.setResizable(false);
		
		logo_panel = new JPanel();
		logo_panel.setBackground(Color.GREEN.darker().darker());
		//logo_panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		//System.out.println(logo_panel.getHeight() + " " + logo_panel.getWidth());
		lblLogoLabel = new JLabel();
		//File file = new File("C:\\Users\\Karim\\Desktop\\docs\\thesis\\Parking_System\\login_v1\\newlogo.png");
		File file = new File(".\\image\\newlogo.png");
		BufferedImage logoPic = null;
		try {
			logoPic = ImageIO.read(file);
			Image dmg = logoPic.getScaledInstance(470, 130, Image.SCALE_SMOOTH);
			lblLogoLabel.setIcon(new ImageIcon(dmg));
			//lblLogoLabel.setIcon(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lblLogoLabel.setSize(logo_panel.getWidth(), logo_panel.getHeight());
		logo_panel.add(lblLogoLabel);
		
		picture_panel = new JPanel();
		//picture_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPictureLabel = new JLabel();
		File person = new File(".\\image\\person.png");
		BufferedImage personLogo = null;
		try {
			personLogo = ImageIO.read(person);
			Image dmg = personLogo.getScaledInstance(240, 190, Image.SCALE_SMOOTH);
			lblPictureLabel.setIcon(new ImageIcon(dmg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picture_panel.setBackground(Color.GREEN.darker().darker());
		picture_panel.add(lblPictureLabel);
		
		info_panel = new JPanel();
		info_panel.setBackground(Color.GREEN.darker().darker());
		//info_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lblCurrentTime = new JLabel("SAMPLE");
		lblCurrentTime.setForeground(Color.black.darker());
		lblCurrentTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentTime.setFont(new Font("Verdana", Font.BOLD, 40));
		
		txtRFIDNumberField = new JTextField();
		txtRFIDNumberField.setColumns(10);
		txtRFIDNumberField.setVisible(false);
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
						//addAttendance();
						if(attendance > 5){
							updateAttendance();
						}
						if(getCurrentDay().equals("Monday"))
						{
							if(attendance == 5){
								sendToArduino("0");
								updateAttendance();
							}else if(attendance != 5){
								sendToArduino("2");
								updateAttendance();
							}
						}else if(getCurrentDay().equals("Tuesday")){
							if(attendance == 1){
								sendToArduino("0");
								modifyAttendance(true);
							}else if(attendance != 5){
								sendToArduino("1");
								modifyAttendance(true);
							}
						}else if(getCurrentDay().equals("Wednesday")){
							if(attendance == 2){
								sendToArduino("0");
								modifyAttendance(true);
							}else if(attendance != 2){
								sendToArduino("1");
								modifyAttendance(true);
							}
						}else if(getCurrentDay().equals("Thursday")){
							if(attendance == 3){
								sendToArduino("0");
								modifyAttendance(true);
							}else if(attendance != 3){
								sendToArduino("1");
								modifyAttendance(true);
							}
						}else if(getCurrentDay().equals("Friday")){
							if(attendance == 4){
								sendToArduino("0");
								modifyAttendance(true);
							}else if(attendance != 4){
								sendToArduino("1");
								modifyAttendance(true);
							}
						}else if(getCurrentDay().equals("Saturday") || getCurrentDay().equals("Sunday")){
							modifyAttendance(false);
						}else{
							modifyAttendance(true);
						}
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
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(info_panel, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(165)
					.addComponent(lblCurrentTime, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
					.addGap(189))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(373)
					.addComponent(txtRFIDNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(378, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblCurrentTime, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(txtRFIDNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
						.addComponent(info_panel, GroupLayout.PREFERRED_SIZE, 279, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setForeground(Color.black.darker());
		lblName.setFont(new Font("Verdana", Font.BOLD, 19));
		
		lblYearLevel  = new JLabel("Year Level:");
		lblYearLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblYearLevel.setForeground(Color.black.darker());
		lblYearLevel.setFont(new Font("Verdana", Font.BOLD, 19));
		
		lblStudentName = new JLabel("Student Name");
		lblStudentName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentName.setForeground(Color.black.darker());
		lblStudentName.setFont(new Font("Verdana", Font.BOLD, 19));
		
		lblYear = new JLabel();
		lblYear.setText("Year Level");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setForeground(Color.black.darker());
		lblYear.setFont(new Font("Verdana", Font.BOLD, 19));
		
		JLabel lblGreetings = new JLabel("Have A Good Day!");
		lblGreetings.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreetings.setForeground(Color.BLACK);
		lblGreetings.setFont(new Font("Verdana", Font.BOLD, 36));
		
		lblCourseName = new JLabel("Course Name");
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setForeground(Color.BLACK);
		lblCourseName.setFont(new Font("Verdana", Font.BOLD, 30));
		
		lblSpecifyCourse = new JLabel();
		lblSpecifyCourse.setText("Course Name:");
		lblSpecifyCourse.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpecifyCourse.setForeground(Color.BLACK);
		lblSpecifyCourse.setFont(new Font("Verdana", Font.BOLD, 19));
		GroupLayout gl_info_panel = new GroupLayout(info_panel);
		gl_info_panel.setHorizontalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStudentName, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblYearLevel)
							.addGap(32)
							.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSpecifyCourse))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addGap(70)
							.addComponent(lblCourseName, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblGreetings, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_info_panel.setVerticalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addGap(34)
					.addComponent(lblGreetings, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_info_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblStudentName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_info_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblYearLevel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSpecifyCourse, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCourseName, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(4, Short.MAX_VALUE))
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
		
		mntmEdit = new JMenuItem("Edit");
		mntmEdit.addActionListener(this);
		mnSystem.add(mntmEdit);
		
		mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(this);
		mnSystem.add(mntmDelete);
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
			registry.temp_edit = 0;
			File person = new File(".\\image\\person.png");
			BufferedImage personLogo = null;
			try {
				personLogo = ImageIO.read(person);
				Image dmg = personLogo.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
				registry.pic_label.setIcon(new ImageIcon(dmg));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(source == mntmExit){
			System.exit(0);
		}else if(source == mntmAbout){
			/*JOptionPane.showMessageDialog(null, 
					"Developed For Wesleyan University Login System", 
					"About Software", JOptionPane.INFORMATION_MESSAGE);*/
			//DateFormat.getTimeInstance().format(new Date());
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("EEEE");
			System.out.println(dateFormat.format(date));
		}else if(source == mntmEdit){
			registry.frmRegistry.setVisible(true);
			registry.txtFirstName.setText(firstName);
			registry.txtLastName.setText(lastName);
			registry.txtRFIDNum.setText(txtRFIDNumberField.getText());
			registry.txtYearLevel.setText(yearLevel);
			registry.txtParentName.setText(parentName);
			registry.txtCellNum.setText(parentCell);
			registry.txtFileLocation.setText(fileLocation);
			registry.txtCourseName.setText(courseName);
			registry.temp_edit = 1;
			File person = new File(fileLocation);
			BufferedImage personLogo = null;
			try {
				personLogo = ImageIO.read(person);
				Image dmg = personLogo.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
				registry.pic_label.setIcon(new ImageIcon(dmg));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(registry.temp_edit);
		}else if(source == mntmDelete){
			deleteStudentPerformed();
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
	
	private void sendToArduino(String attendanceFlag)
	{
		
		String overallStream = parentCell + "," + attendanceFlag;
		try {
			serialOut.write(overallStream.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void deleteStudentPerformed(){
		int result = 0;
		result = databaseDriverManager.deleteStudent(txtRFIDNumberField.getText());
		File person = new File(".\\image\\person.png");
		BufferedImage personLogo = null;
		try {
			personLogo = ImageIO.read(person);
			Image dmg = personLogo.getScaledInstance(240, 190, Image.SCALE_SMOOTH);
			lblPictureLabel.setIcon(new ImageIcon(dmg));
			lblStudentName.setText("Student Name");
			lblYear.setText("Year Level");
			lblCourseName.setText("Course Name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result == 1)
		{
			JOptionPane.showMessageDialog(null,"Student Deleted!", "Student Successfully Deleted!", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null,"Student Not Deleted!", "Unsucessful!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void modifyAttendance(boolean mod){
		if(mod == false)
		{
			databaseDriverManager.addAttendance(attendance - 1, txtRFIDNumberField.getText());
		}else if(mod == true)
		{
			databaseDriverManager.addAttendance(attendance + 1, txtRFIDNumberField.getText());
		}
		
	}
	
	private void updateAttendance()
	{
		databaseDriverManager.updateAttendance(txtRFIDNumberField.getText());
	}
	
	private String getCurrentDay()
	{
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("EEEE");
		day = String.valueOf(dateFormat.format(date));
		return day;
	}
	
	private void displayStudentInfo()
	{
		results = databaseDriverManager.getStudentInfo(txtRFIDNumberField.getText());
		if(results.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Student Not Registered!", "Unregistered Student", JOptionPane.ERROR_MESSAGE);
			parentCell = "";
			registry.temp_edit = 0;
			registry.frmRegistry.setVisible(true);
			registry.txtRFIDNum.setText(txtRFIDNumberField.getText());
			File person = new File(".\\image\\person.png");
			BufferedImage personLogo = null;
			try {
				personLogo = ImageIO.read(person);
				Image dmg = personLogo.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
				registry.pic_label.setIcon(new ImageIcon(dmg));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
		numberOfEntries = results.size();
		if(numberOfEntries != 0){
			currentEntries = 0;
			student = results.get(currentEntries);
			File file = new File(student.getImagePath());
			BufferedImage picture = null;
			try {
				picture = ImageIO.read(file);
				//Image dmg = picture.getScaledInstance(288, 200, Image.SCALE_SMOOTH);
				Image dmg = picture.getScaledInstance(300, 240, Image.SCALE_SMOOTH);
				//System.out.println(picture_panel.getHeight() + " " + picture_panel.getWidth());
				lblPictureLabel.setIcon(new ImageIcon(dmg));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(student.getYearLevel());
			firstName = student.getStudentFirstName();
			lastName = student.getStudentLastName();
			yearLevel = student.getYearLevel();
			parentCell = student.getParentCellNumber();
			parentName = student.getParentName();
			fileLocation = student.getImagePath();
			attendance = student.getAttendance();
			courseName = student.getCourseName();
			System.out.println(attendance);
			lblStudentName.setText(student.getStudentFirstName() + " " + student.getStudentLastName());
			lblCourseName.setText(student.getCourseName());
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
			lblCourseName.setText("Course Name");
			File person = new File(".\\image\\person.png");
			BufferedImage personLogo = null;
			try {
				personLogo = ImageIO.read(person);
				Image dmg = personLogo.getScaledInstance(240, 190, Image.SCALE_SMOOTH);
				lblPictureLabel.setIcon(new ImageIcon(dmg));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sendToArduino();
			//close();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	//code
	public void close() {
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
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		try{
			String line = serialReader.readLine();
			System.out.println(line);
			registry.txtRFIDNum.setText(line);
			txtRFIDNumberField.setText(line);
			//if(line.en)
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
