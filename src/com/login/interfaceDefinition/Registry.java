package com.login.interfaceDefinition;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.login.entity.Student;

public class Registry implements ActionListener{
	
	private Student studentProfile;
	private BufferedImage picture;
	public int item;
	
	public JFrame frmRegistry;
	
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtRFIDNum;
	private JTextField txtYearLevel;
	private JTextField txtParentName;
	private JTextField txtCellNum;
	private JTextField txtFileLocation;
	
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
	private JLabel pic_label;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
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
	}*/

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
		
		pic_label = new JLabel();
		
		//picture = new BufferedImage(0, 0, (Integer) null);
		studentProfile = new Student();
		frmRegistry.setResizable(false);
		frmRegistry.setTitle("Registry");
		frmRegistry.setBounds(100, 100, 460, 600);
		frmRegistry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Verdana", Font.BOLD, 14));
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		information_panel = new JPanel();
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
		lblFirstName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtFirstName.setText("sasas");
		txtFirstName.setColumns(10);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtLastName = new JTextField();
		txtLastName.setText("sasas");
		txtLastName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtLastName.setColumns(10);
		
		txtRFIDNum = new JTextField();
		txtRFIDNum.setEditable(false);
		txtRFIDNum.setText("sasas");
		txtRFIDNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtRFIDNum.setColumns(10);
		
		txtYearLevel = new JTextField();
		txtYearLevel.setText("sasas");
		txtYearLevel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtYearLevel.setColumns(10);
		
		lblRfidNum = new JLabel("RFID Num:");
		lblRfidNum.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		lblYearLevel = new JLabel("Year Level:");
		lblYearLevel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		txtParentName = new JTextField();
		txtParentName.setText("sasas");
		txtParentName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtParentName.setColumns(10);
		
		txtCellNum = new JTextField();
		txtCellNum.setText("sasas");
		txtCellNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtCellNum.setColumns(10);
		
		lblParentName = new JLabel("Parent Name:");
		lblParentName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		lblParentCellNum = new JLabel("Parent Cell Num:");
		lblParentCellNum.setFont(new Font("Times New Roman", Font.BOLD, 17));
		
		
		/*BufferedImage picture;
		pic_label = new JLabel();
		
		try {
			picture = ImageIO.read(new File(txtFileLocation.getText()));
			pic_label = new JLabel(new ImageIcon(picture));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		picture_panel = new JPanel();
		picture_panel.add(pic_label);
		picture_panel.setBorder(new LineBorder(Color.GRAY));
			
		
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
		GroupLayout gl_information_panel = new GroupLayout(information_panel);
		gl_information_panel.setHorizontalGroup(
			gl_information_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_information_panel.createSequentialGroup()
					.addGroup(gl_information_panel.createParallelGroup(Alignment.LEADING)
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
									.addComponent(txtCellNum, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addGap(92)
							.addComponent(picture_panel, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
							.addGap(89))
						.addGroup(gl_information_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnFileMenu, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
							.addComponent(txtFileLocation, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
					.addGap(24))
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
					.addGap(18)
					.addComponent(picture_panel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
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
		}else if(source.equals(btnFileMenu)){
			item = fcChoosePic.showOpenDialog(frmRegistry);
			studentProfile.setImagePath(String.valueOf(fcChoosePic.getSelectedFile()));
			txtFileLocation.setText(studentProfile.getImagePath());
			if(item == fcChoosePic.APPROVE_OPTION)
			{
				System.out.println(txtFileLocation.getText());
				
				try {
					picture = ImageIO.read(new File(txtFileLocation.getText()));
					//pic_label.setText("SAKSJA");
					//picture_panel.add(pic_label);
					//picture_panel.setBackground(ImageIO.read(new File(txtFileLocation.getText())));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}
