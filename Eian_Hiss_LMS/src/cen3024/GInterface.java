/*
 * Eian Hiss - CEN3024C - APR 7, 2024
 * Class - GInterface
 * Graphical interface for program with user I/O.
 * Created via Eclipse WindowBuilder.
 */

package cen3024;

import java.util.*;
import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GInterface {

	private JFrame frmLibraryManagement;
	private int ID;
	private String title = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBLibrary.connect();
					GInterface window = new GInterface();
					window.frmLibraryManagement.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Database not found.");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryManagement = new JFrame();
		frmLibraryManagement.setResizable(false);
		frmLibraryManagement.setTitle("Library Management - EH");
		frmLibraryManagement.setBounds(100, 100, 700, 560);
		frmLibraryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryManagement.getContentPane().setLayout(null);
		
		JScrollPane sPane = new JScrollPane();
		sPane.setBounds(12, 12, 676, 471);
		frmLibraryManagement.getContentPane().add(sPane);
		
		JTable tbCollection = new JTable();
		sPane.setViewportView(tbCollection);
		DefaultTableModel model = new DefaultTableModel();
		Object[] column = {	"ID", "Title", "Author", "Genre", "Checked Out", "Due Date"};
		ArrayList<Object> row = new ArrayList<Object>();
		model.setColumnIdentifiers(column);
		try { // Populating the table
			ResultSet query = DBLibrary.refresh(); // Retrieve information from Database
			ResultSetMetaData queryMeta = query.getMetaData();
			while(query.next()) {
				for (int col = 1; col <= queryMeta.getColumnCount(); col++) {
					row.add(query.getObject(col)); // Build record
				}
				model.addRow(row.toArray()); // Add record to table
				row.clear(); // Clear list for next record
			}
			query.close();
			tbCollection.setModel(model); 
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}
		
		JButton btnRemID = new JButton("Remove ID"); // Removing books by ID
		btnRemID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				ID = Integer.parseInt(JOptionPane.showInputDialog("Enter ID of book to remove."));
				ResultSet query = DBLibrary.checkID(ID);
				if (query.next()) {
					DBLibrary.removeID(ID); // Remove Book after confirming it exists
				}
				else
					JOptionPane.showMessageDialog(null, "Book not found.");
				query.close();
				while (model.getRowCount() > 0) // This updates the table.
					model.removeRow(0);
				query = DBLibrary.refresh();
				ResultSetMetaData queryMeta = query.getMetaData();
				while(query.next()) {
					for (int col = 1; col <= queryMeta.getColumnCount(); col++) {
						row.add(query.getObject(col));
					}
					model.addRow(row.toArray());
					row.clear();
				}
				query.close();
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle.getMessage());
				}
			}
		});
		btnRemID.setBounds(22, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnRemID);
		
		JButton btnRemoveTitle = new JButton("Remove Title"); // Removing books by Title
		btnRemoveTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
					title = JOptionPane.showInputDialog("Enter title of book to remove.");
					ResultSet query = DBLibrary.checkTitle(title);
					if (query.next()) {
						DBLibrary.removeTitle(title); // Remove Book after confirming it exists
					}
					else
						JOptionPane.showMessageDialog(null, "Book not found.");
					query.close();
					while (model.getRowCount() > 0) // This updates the table.
						model.removeRow(0);
					
						query = DBLibrary.refresh();
						ResultSetMetaData queryMeta = query.getMetaData();
						while(query.next()) {
							for (int col = 1; col <= queryMeta.getColumnCount(); col++) {
								row.add(query.getObject(col));
							}
							model.addRow(row.toArray());
							row.clear();
						}
						query.close();
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle.getMessage());
				}
			}
		});
		btnRemoveTitle.setBounds(151, 495, 135, 25);
		frmLibraryManagement.getContentPane().add(btnRemoveTitle);
		
		JButton btnCheckOut = new JButton("Check Out"); // Checking books out
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					title = JOptionPane.showInputDialog("Enter title of book to check out.");
					ResultSet query = DBLibrary.checkTitle(title);
					if (query.next())  // Check if book exists
						if (query.getBoolean("Status")) // Check if book is checked out
							JOptionPane.showMessageDialog(null, "Book already checked out");
						else
							DBLibrary.checkOut(title);
					else
						JOptionPane.showMessageDialog(null, "Book Not Found");
					query.close();
					while (model.getRowCount() > 0) // This updates the table.
						model.removeRow(0);
					query = DBLibrary.refresh();
					ResultSetMetaData queryMeta = query.getMetaData();
					while(query.next()) {
						for (int col = 1; col <= queryMeta.getColumnCount(); col++) {
							row.add(query.getObject(col));
						}
						model.addRow(row.toArray());
						row.clear();
					}
					query.close();
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle.getMessage());
				}
			}
		});
		btnCheckOut.setBounds(298, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnCheckOut);
		
		JButton btnCheckIn = new JButton("Check In"); // Checking books in
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					title = JOptionPane.showInputDialog("Enter title of book to check in.");
					ResultSet query = DBLibrary.checkTitle(title);
					if (query.next())  // Check if book exists
						DBLibrary.checkIn(title);
					else
						JOptionPane.showMessageDialog(null, "Book Not Found");
					query.close();
					while (model.getRowCount() > 0) // This updates the table.
						model.removeRow(0);
					query = DBLibrary.refresh();
					ResultSetMetaData queryMeta = query.getMetaData();
					while(query.next()) {
						for (int col = 1; col <= queryMeta.getColumnCount(); col++) {
							row.add(query.getObject(col));
						}
						model.addRow(row.toArray());
						row.clear();
					}
					query.close();
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle.getMessage());
				}
			}
		});
		btnCheckIn.setBounds(427, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnCheckIn);
		
		JButton btnExit = new JButton("Exit"); // Exit the program
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DBLibrary.close(); //close database connection
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				} finally {			
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(556, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnExit);
		tbCollection.getColumnModel().getColumn(1).setPreferredWidth(116);
		tbCollection.getColumnModel().getColumn(2).setPreferredWidth(133);
	}
}
