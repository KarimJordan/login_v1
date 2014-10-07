package com.login.interfaceDefinition;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.login.databaseConnection.DataBaseDriverManager;
import com.login.entity.StudentLog;

public class DataTable implements ActionListener{

	private JFrame frmDataTable;
	private JTextField nameField;
	private JTextField courseField;
	
	private JButton btnFilter;
	
	private JLabel lblName;
	private JLabel lblCourse;
	
	private JPanel filterPanel;
	private JPanel dataPanel;
	
	private ButtonGroup buttonGroup;
	private JRadioButton rdbtnName;
	private JRadioButton rdbtnDate;
	
	private JComboBox spanCombo;
	
	private final String[] columnNames = {"First Name", "Last Name","Student Number", "Course", "Log In", "Log Out"};
	private DefaultTableModel model;
	private JTable dataTable;
	private JScrollPane scrollPane;
	
	private DataBaseDriverManager databaseDriverManager;
	private StudentLog studentLog;
	private List<StudentLog> results;
	private List<StudentLog> list;
	
	
	private String sFirstName;
	private String sLastName;
	private String sCourse;
	private String sStudentNumber;
	private String sLogIn;
	private String sLogOut;
	private String filterType = "Show All";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataTable window = new DataTable();
					window.frmDataTable.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DataTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		databaseDriverManager = new DataBaseDriverManager();
		studentLog = new StudentLog();
		
		frmDataTable = new JFrame();
		frmDataTable.setTitle("Report Table");
		frmDataTable.setBounds(100, 100, 800, 480);
		frmDataTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataTable.getContentPane().setLayout(new BoxLayout(frmDataTable.getContentPane(), BoxLayout.X_AXIS));
		
		filterPanel = new JPanel();
		frmDataTable.getContentPane().add(filterPanel);
		
		//Table Parameters
		model = new DefaultTableModel();
		dataTable = new JTable();
		scrollPane = new JScrollPane(dataTable);
		model.setColumnIdentifiers(columnNames);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		dataTable.setModel(model);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		dataTable.setFillsViewportHeight(true);
		
		btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				displayStudentLog();
			}
		});
		
		lblName = new JLabel("Name:");
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		lblCourse = new JLabel("Course :");
		
		courseField = new JTextField();
		courseField.setColumns(10);
		
		rdbtnName = new JRadioButton("Name");
		rdbtnName.setActionCommand("Name");
		rdbtnName.addActionListener(this);
		
		rdbtnDate = new JRadioButton("Span");
		rdbtnDate.setActionCommand("Span");
		rdbtnDate.addActionListener(this);
		
		buttonGroup = new ButtonGroup();
		
		buttonGroup.add(rdbtnName);
		buttonGroup.add(rdbtnDate);
		
		spanCombo = new JComboBox();
		spanCombo.setModel(new DefaultComboBoxModel(new String[] {"One Hour", "One Day", "One Week"}));
		GroupLayout gl_filterPanel = new GroupLayout(filterPanel);
		gl_filterPanel.setHorizontalGroup(
			gl_filterPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_filterPanel.createSequentialGroup()
					.addGroup(gl_filterPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_filterPanel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_filterPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_filterPanel.createSequentialGroup()
									.addGroup(gl_filterPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblCourse, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_filterPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
										.addComponent(courseField, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
										.addComponent(spanCombo, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_filterPanel.createSequentialGroup()
									.addComponent(rdbtnName)
									.addGap(18)
									.addComponent(rdbtnDate, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_filterPanel.createSequentialGroup()
							.addGap(87)
							.addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_filterPanel.setVerticalGroup(
			gl_filterPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_filterPanel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_filterPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnName)
						.addComponent(rdbtnDate))
					.addGap(26)
					.addGroup(gl_filterPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_filterPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCourse)
						.addComponent(courseField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(spanCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		filterPanel.setLayout(gl_filterPanel);
		
		dataPanel = new JPanel();
		frmDataTable.getContentPane().add(dataPanel);
		/*JButton btnNewButton = new JButton("New button");
		dataPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		dataPanel.add(btnNewButton_1);*/
		dataPanel.add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String choice = buttonGroup.getSelection().getActionCommand();
		//System.out.println(choice);
		if(choice.equals("Span")){
			nameField.setEnabled(false);
			nameField.setText("");
			courseField.setEnabled(false);
			courseField.setText("");
			spanCombo.setEnabled(true);
			filterType = "Show Span";
		}else if (choice.equals("Name")){
			nameField.setEnabled(true);
			courseField.setEnabled(true);
			spanCombo.setEnabled(false);
			filterType = "Show Name";
		}
		/*else if(choice.equals("Filter")){
			System.out.println("SKJAHJ");
		}*/
		
	}
	
	private void displayStudentLog(){
		if(filterType.equals("Show All")){
			results = databaseDriverManager.getStudentLog();
			model.setRowCount(0);
			for(StudentLog list:results){
				sFirstName = list.getFirstName();
				sLastName = list.getLastName();
				sStudentNumber = list.getStudentNumber();
				sCourse = list.getCourse();
				sLogIn = list.getLog_in();
				sLogOut = list.getLog_out();
				model.addRow(new Object[]{sFirstName, sLastName, sStudentNumber, sCourse, sLogIn, sLogOut});
			}
		}else if(filterType.equals("Show Name")){
			System.out.println("sjhag");
			results = databaseDriverManager.getStudentLogOnInput(nameField.getText(), nameField.getText(), courseField.getText());
			model.setRowCount(0);
			for(StudentLog list:results){
				sFirstName = list.getFirstName();
				sLastName = list.getLastName();
				sStudentNumber = list.getStudentNumber();
				sCourse = list.getCourse();
				sLogIn = list.getLog_in();
				sLogOut = list.getLog_out();
				model.addRow(new Object[]{sFirstName, sLastName, sStudentNumber, sCourse, sLogIn, sLogOut});
			}
		}else if(filterType.equals("Show Span")){
			System.out.println("sjahska");
		}
		
	}
	
	
}
