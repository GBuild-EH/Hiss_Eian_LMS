package cen3024;

import java.util.*;
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
	private JTable tbCollection;
	static ArrayList<Book> collection = new ArrayList<Book>();
	private List<Integer> indices = new ArrayList<Integer>();
	private int ID, index = 0;
	private String title = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String path = JOptionPane.showInputDialog("Please enter collection file name");
					if (path.length() > 0) {
						Library.importRecords(path, collection);
						GInterface window = new GInterface();
						window.frmLibraryManagement.setVisible(true);
					}
					else
						JOptionPane.showMessageDialog(null, "Please enter a file name.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "File not found.");
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
		frmLibraryManagement.setBounds(100, 100, 700, 600);
		frmLibraryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryManagement.getContentPane().setLayout(null);
		
		JScrollPane sPane = new JScrollPane();
		sPane.setBounds(12, 12, 676, 471);
		frmLibraryManagement.getContentPane().add(sPane);
		
		tbCollection = new JTable();
		sPane.setViewportView(tbCollection);
		DefaultTableModel model = new DefaultTableModel();
		Object[] column = {	"ID", "Title", "Author", "Genre", "Checked Out", "Due Date"};
		Object[] row = new Object[6];
		model.setColumnIdentifiers(column);
		for (int i = 0; i < collection.size(); i++) {
			row = collection.get(i).toString().split(",");
			model.addRow(row);
		}
		tbCollection.setModel(model);
		
		JButton btnRemID = new JButton("Remove ID");
		btnRemID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID = Integer.parseInt(JOptionPane.showInputDialog("Enter ID of book to remove."));
				index = Library.indexByID(collection, ID);
				if (index != -1)
					Library.removeRecord(collection, index);
				else
					JOptionPane.showMessageDialog(null, "Book not found.");
				while (model.getRowCount() > 0) // This refreshes the table.
					model.removeRow(0);
				for (int x = 0; x < collection.size(); x++) {
					Object[] row = collection.get(x).toString().split(",");
					model.addRow(row);
				}
			}
		});
		btnRemID.setBounds(22, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnRemID);
		
		JButton btnRemoveTitle = new JButton("Remove Title");
		btnRemoveTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = JOptionPane.showInputDialog("Enter title of book to remove.");
				indices = Library.indexByTitle(collection, title);
				if (indices.isEmpty())
					JOptionPane.showMessageDialog(null, "Title not found.");
				else if (indices.size() == 1)
					Library.removeRecord(collection, indices.get(0));
				else {
					String message = "Multiple records found. Enter ID of book to remove.\n";
					for (int x = 0; x < indices.size(); x++) {
						message += (collection.get(indices.get(x)).toString() + "\n");
					}
					ID = Integer.parseInt(JOptionPane.showInputDialog(message));
					index = Library.indexByID(collection, ID);
					if (index != -1)
						Library.removeRecord(collection, index);
					else
						JOptionPane.showMessageDialog(null, "Book not found.");					
				}
				while (model.getRowCount() > 0) // This refreshes the table.
					model.removeRow(0);
				for (int x = 0; x < collection.size(); x++) {
					Object[] row = collection.get(x).toString().split(",");
					model.addRow(row);
				}
			}
		});
		btnRemoveTitle.setBounds(151, 495, 135, 25);
		frmLibraryManagement.getContentPane().add(btnRemoveTitle);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = JOptionPane.showInputDialog("Enter title of book to check out.");
				indices = Library.indexByTitle(collection, title);
				if (indices.isEmpty())
					JOptionPane.showMessageDialog(null, "Title not found.");
				else if (indices.size() == 1)
					if (!collection.get(indices.get(0)).getCheckedStatus())
						Library.checkOut(collection.get(indices.get(0)));
					else
						JOptionPane.showMessageDialog(null, "Book already checked out.");
				else {
					String message = "Multiple records found. Enter ID of book to check out.\n";
					for (int x = 0; x < indices.size(); x++) {
						message += (collection.get(indices.get(x)).toString() + "\n");
					}
					ID = Integer.parseInt(JOptionPane.showInputDialog(message));
					index = Library.indexByID(collection, ID);
					if (index != -1)
						if(!collection.get(index).getCheckedStatus())	
							Library.checkOut(collection.get(index));
						else
							JOptionPane.showMessageDialog(null, "Book already checked out.");
					else
						JOptionPane.showMessageDialog(null, "Book not found.");
				}
				while (model.getRowCount() > 0) // This refreshes the table.
					model.removeRow(0);
				for (int x = 0; x < collection.size(); x++) {
					Object[] row = collection.get(x).toString().split(",");
					model.addRow(row);
				}
			}
		});
		btnCheckOut.setBounds(298, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnCheckOut);
		
		JButton btnCheckIn = new JButton("Check In");
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = JOptionPane.showInputDialog("Enter title of book to check in.");
				indices = Library.indexByTitle(collection, title);
				if (indices.isEmpty())
					JOptionPane.showMessageDialog(null, "Title not found.");
				else if (indices.size() == 1)
					if (collection.get(indices.get(0)).getCheckedStatus())
						Library.checkIn(collection.get(indices.get(0)));
					else
						JOptionPane.showMessageDialog(null, "Book already checked in.");
				else {
					String message = "Multiple records found. Enter ID of book to check in.\n";
					for (int x = 0; x < indices.size(); x++) {
						message += (collection.get(indices.get(x)).toString() + "\n");
					}
					ID = Integer.parseInt(JOptionPane.showInputDialog(message));
					index = Library.indexByID(collection, ID);
					if (index != -1)
						if(collection.get(index).getCheckedStatus())	
							Library.checkOut(collection.get(index));
						else
							JOptionPane.showMessageDialog(null, "Book already checked in.");
					else
						JOptionPane.showMessageDialog(null, "Book not found.");
				}
				while (model.getRowCount() > 0) // This refreshes the table.
					model.removeRow(0);
				for (int x = 0; x < collection.size(); x++) {
					Object[] row = collection.get(x).toString().split(",");
					model.addRow(row);
				}
			}
		});
		btnCheckIn.setBounds(427, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnCheckIn);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(556, 495, 117, 25);
		frmLibraryManagement.getContentPane().add(btnExit);
		tbCollection.getColumnModel().getColumn(1).setPreferredWidth(116);
		tbCollection.getColumnModel().getColumn(2).setPreferredWidth(133);
	}
}
