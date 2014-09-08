package com.login.interfaceDefinition;

import gnu.io.SerialPort;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.login.databaseConnection.DataBaseDriverManager;
import com.login.entity.Student;

public class Registry implements ActionListener{
	
	private Student studentProfile;
	private LoginInterface interfaced = new LoginInterface();
	private DataBaseDriverManager databaseManager;
	private BufferedImage convert;
	private BufferedImage  img1;
	private BufferedImage resize;
	private List<Student> results;
	public int item;
	public int temp_edit;
	
	public JFrame frmRegistry;
	
	public JTextField txtFirstName;
	public JTextField txtLastName;
	public JTextField txtRFIDNum;
	public JTextField txtYearLevel;
	public JTextField txtParentName;
	public JTextField txtCellNum;
	public JTextField txtFileLocation;
	public JTextField txtCourseName;
	
	private JFileChooser fcChoosePic;
	
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnFileMenu;
	
	private JPanel information_panel;
	private JPanel picture_panel;
	
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblRfidNum;
	private JLabel lblYearLevel;
	private JLabel lblParentName;
	private JLabel lblParentCellNum;
	public JLabel pic_label;
	private JLabel lblCourseName;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registry window = new Registry();
					
					window.frmRegistry.setVisible(true);
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
	public Registry() {
			initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(){
		frmRegistry = new JFrame();
		
		fcChoosePic = new JFileChooser();
		fcChoosePic.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter ft = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "jfif", "jpe");
		fcChoosePic.addChoosableFileFilter(ft);
		
		BufferedImage picture_default = null;
		
		/*try {
			picture_default = ImageIO.read(new File("/image/blank.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage default_img = (BufferedImage) picture_default.getScaledInstance(picture_panel.getWidth(),picture_panel.getHeight()-10, Image.SCALE_SMOOTH);*/
		pic_label = new JLabel();
		
		//picture = new BufferedImage(0, 0, (Integer) null);
		studentProfile = new Student();
		databaseManager = new DataBaseDriverManager();
		
		frmRegistry.setResizable(false);
		frmRegistry.setTitle("Registry");
		frmRegistry.setBounds(100, 100, 500, 647);
		//frmRegistry.getContentPane().setBackground(Color.GREEN.darker().darker());
		frmRegistry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				insertStudentActionPerformed(arg0);
			}
		});
		btnSave.setFont(new Font("Verdana", Font.BOLD, 14));
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		information_panel = new JPanel();
		//information_panel.setBackground(Color.GREEN.darker().darker());
		information_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GroupLayout groupLayout = new GroupLayout(frmRegistry.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(information_panel, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(information_panel, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGap(19))
		);
		
		lblFirstName = new JLabel("First Name:");
		lblFirstName.setForeground(Color.black.darker());
		lblFirstName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtFirstName.setText("");
		txtFirstName.setColumns(10);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setForeground(Color.black.darker());
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtLastName = new JTextField();
		txtLastName.setText("");
		txtLastName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtLastName.setColumns(10);
		
		txtRFIDNum = new JTextField();
		txtRFIDNum.setEditable(false);
		//txtRFIDNum.setText();
		txtRFIDNum.setText("");
		txtRFIDNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtRFIDNum.setColumns(10);
		
		txtYearLevel = new JTextField();
		txtYearLevel.setText("");
		txtYearLevel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtYearLevel.setColumns(10);
		
		lblRfidNum = new JLabel("RFID Num:");
		lblRfidNum.setForeground(Color.black.darker());
		lblRfidNum.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		lblYearLevel = new JLabel("Year Level:");
		lblYearLevel.setForeground(Color.black.darker());
		lblYearLevel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtParentName = new JTextField();
		txtParentName.setText("");
		txtParentName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtParentName.setColumns(10);
		
		txtCellNum = new JTextField();
		txtCellNum.setText("");
		txtCellNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtCellNum.setColumns(10);
		
		lblParentName = new JLabel("Parent Name:");
		lblParentName.setForeground(Color.black.darker());
		lblParentName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		lblParentCellNum = new JLabel("Parent Cell Num:");
		lblParentCellNum.setForeground(Color.black.darker());
		lblParentCellNum.setFont(new Font("Times New Roman", Font.BOLD, 17));

		picture_panel = new JPanel();
		//picture_panel.setBackground(Color.GREEN.darker().darker());
		File person = new File(".\\image\\person.png");
		BufferedImage personLogo = null;
		try {
			personLogo = ImageIO.read(person);
			Image dmg = personLogo.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
			pic_label.setIcon(new ImageIcon(dmg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picture_panel.add(pic_label);
		//picture_panel.setBorder(new LineBorder(Color.GRAY));
			
		
		txtFileLocation = new JTextField();
		txtFileLocation.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtFileLocation.setColumns(10);
		
		btnFileMenu = new JButton("File");
		btnFileMenu.addActionListener(this);
		/*btnFileMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fcChoosePic.showOpenDialog(frmRegistry);
			}
		});*/
		btnFileMenu.setFont(new Font("Verdana", Font.BOLD, 14));
		
		lblCourseName = new JLabel("Course Name:");
		lblCourseName.setForeground(Color.BLACK.darker());
		lblCourseName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtCourseName = new JTextField();
		txtCourseName.setText("");
		txtCourseName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtCourseName.setColumns(10);
		GroupLayout gl_information_panel = new GroupLayout(information_panel);
		gl_information_panel.setHorizontalGroup(
			gl_information_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_information_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_information_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblFirstName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblRfidNum, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtRFIDNum, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblYearLevel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtYearLevel, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblParentName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtParentName, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(lblParentCellNum, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtCellNum, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addComponent(btnFileMenu, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
							.addComponent(txtFileLocation, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
					.addGap(24))
				.addGroup(gl_information_panel.createSequentialGroup()
					.addGap(92)
					.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addGap(113))
				.addGroup(gl_information_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCourseName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtCourseName, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(64, Short.MAX_VALUE))
		);
		gl_information_panel.setVerticalGroup(
			gl_information_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_information_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFirstName)
						.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRfidNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRFIDNum, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYearLevel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtYearLevel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblParentName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtParentName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblParentCellNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCellNum, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCourseName, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCourseName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(picture_panel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addGroup(gl_information_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFileMenu, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtFileLocation, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		information_panel.setLayout(gl_information_panel);
		frmRegistry.getContentPane().setLayout(groupLayout);
		frmRegistry.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source.equals(btnCancel)){
			frmRegistry.setVisible(false);
			System.out.println(temp_edit);
		}else if(source.equals(btnFileMenu)){
			item = fcChoosePic.showOpenDialog(frmRegistry);
			studentProfile.setImagePath(String.valueOf(fcChoosePic.getSelectedFile()));
			
			if(item == fcChoosePic.APPROVE_OPTION)
			{
				System.out.println(txtFileLocation.getText());
				txtFileLocation.setText(studentProfile.getImagePath());
				File file = fcChoosePic.getSelectedFile();
				BufferedImage picture = null;
				try {
					picture = ImageIO.read(file);
					Image dmg = picture.getScaledInstance(253, 150,Image.SCALE_SMOOTH);
					pic_label.setIcon(new ImageIcon(dmg));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(item == fcChoosePic.CANCEL_OPTION)
			{
				JOptionPane.showMessageDialog(null, "Operation Cancelled", "Operation Cancelled", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	
	
	
	private void insertStudentActionPerformed(ActionEvent evt){
		int result = 0;
		if(temp_edit == 0)
		{
			result = databaseManager.addStudent(txtRFIDNum.getText(), 
					txtFirstName.getText(), 
					txtLastName.getText(), 
					txtYearLevel.getText(), 
					txtFileLocation.getText(), 
					txtParentName.getText(), 
					txtCellNum.getText(),
					txtCourseName.getText());
		}else if(temp_edit == 1){
			result = databaseManager.editStudent(txtRFIDNum.getText(), 
					txtFirstName.getText(), 
					txtLastName.getText(), 
					txtYearLevel.getText(), 
					txtFileLocation.getText(), 
					txtParentName.getText(), 
					txtCellNum.getText(),
					txtCourseName.getText());
		}
		
		if(result == 1)
		{
			JOptionPane.showMessageDialog(null, "Student Successfully Added!", "Student Added", JOptionPane.INFORMATION_MESSAGE);
			txtRFIDNum.setText("");
			txtFirstName.setText("");
			txtLastName.setText("");
			txtYearLevel.setText("");
			txtFileLocation.setText("");
			txtParentName.setText("");
			txtCellNum.setText("");
			pic_label.setIcon(null);
			frmRegistry.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(null, "Student Not Added", "Unsuccessful!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
