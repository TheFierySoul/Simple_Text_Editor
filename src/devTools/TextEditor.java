package devTools;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.io.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextEditor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextEditor window = new TextEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTextPane textPane;
	File file = null;
	/**
	 * Create the application.
	 */
	public TextEditor() {
		initialize();
	}

	private void writeToFile() {
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;

		try {

			String content = "This is the content to write into file\n";

			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
	
	private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int ret = fileChooser.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			writeToFile();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmnNewFile = new JMenuItem("New File");
		mntmnNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textPane.getText().trim().equals(""))
				{
					//System.out.println("It must work.");
					//JFrame alertFrame = new JFrame();
					int answer = JOptionPane.showConfirmDialog( null,
						    "Are you sure?\n"
						    + "Unsaved data will be mistaken.\n",
						    "Alert!",
						    JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						textPane.setText("");
					}
				}
			}
		});
		mnFile.add(mntmnNewFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser openFile = new JFileChooser();
				int ret = openFile.showDialog(null, "Open File");
				
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					FileReader fileReader = null;
					BufferedReader fileInput = null;
					try
					{
						file = openFile.getSelectedFile();
						String currentLine;
						fileReader = new FileReader(file);
						fileInput = new BufferedReader(fileReader);
						textPane.setText("");
						while ((currentLine = fileInput.readLine()) != null) {
							//System.out.println(currentLine);
							textPane.setText(textPane.getText() + currentLine + "\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (fileReader != null)
							{
								fileReader.close();
							}
							if (fileInput != null)
							{
								fileInput.close();
							}
						} 
					 catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		}});
		mnFile.add(mntmOpenFile);
		
		JMenuItem mntmSaveFile = new JMenuItem("Save File");
		mntmSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (file == null) {
					saveAs();
				}
				else {
					writeToFile();
				}
			}
		});
		mnFile.add(mntmSaveFile);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAs();
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);

	}

}
