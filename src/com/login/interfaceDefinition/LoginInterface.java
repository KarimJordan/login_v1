package com.login.interfaceDefinition;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.NumericShaper;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.login.databaseConnection.DataBaseDriverManager;
import com.login.entity.Student;

public class LoginInterface implements ActionListener{

	private JFrame frmLoginSystem;
	
	private JPanel logo_panel;
	private JPanel picture_panel;
	private JPanel info_panel;
	
	private JLabel lblCurrentTime;
	private JLabel lblName;
	private JLabel lblYearLevel;
	private JLabel lblStudentName;
	private JLabel lblYear;
	
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
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
					window.frmLoginSystem.setVisible(true);
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
		frmLoginSystem.setBounds(100, 100, 650, 510);
		frmLoginSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		logo_panel = new JPanel();
		logo_panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		
		picture_panel = new JPanel();
		picture_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		info_panel = new JPanel();
		info_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lblCurrentTime = new JLabel("SAMPLE");
		lblCurrentTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentTime.setFont(new Font("Verdana", Font.BOLD, 40));
		GroupLayout groupLayout = new GroupLayout(frmLoginSystem.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(info_panel, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(165)
							.addComponent(lblCurrentTime, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
							.addGap(179)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(logo_panel, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblCurrentTime, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
						.addComponent(info_panel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
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
		
		lblYear = new JLabel("4th Year");
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
			Registry registry = new Registry();
			registry.frmRegistry.setVisible(true);
			if(registry.frmRegistry.isVisible())
			{
			mntmRegister.setEnabled(false);
			}
			else{
			mntmRegister.setEnabled(true);
			}
		}else if(source == mntmExit){
			//System.exit(0);
			results = databaseDriverManager.getStudentInfo("123");
			numberOfEntries = results.size();
			if(numberOfEntries != 0){
				currentEntries = 0;
				student = results.get(currentEntries);
				System.out.println(student.getStudentFirstName());
				System.out.println(student.getYearLevel());
			}
		}else if(source == mntmAbout){
			JOptionPane.showMessageDialog(null, 
					"Developed For Wesleyan University Login System", 
					"About Software", JOptionPane.INFORMATION_MESSAGE);
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
	
	private void displayStudenInfo(ActionEvent evt)
	{
		results = databaseDriverManager.getStudentInfo("123");
		numberOfEntries = results.size();
		if(numberOfEntries != 0){
			currentEntries = 0;
			student = results.get(currentEntries);
			System.out.println(student.getStudentFirstName());
			System.out.println(student.getYearLevel());
		}
	}
}
