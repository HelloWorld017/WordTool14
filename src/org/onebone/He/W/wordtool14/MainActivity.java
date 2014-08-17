package org.onebone.He.W.wordtool14;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MainActivity extends JFrame{
	private JPanel mainPanel, optionPanel, contentPanel, buttonPanel;
	//, bottomPanel;
	private JTextArea textArea, resultArea, customRegex, customString;
	private JButton fileChooseBtn, convertBtn;
	private JCheckBox[] checks;
	private File retFile;
	
	public MainActivity(){
		super("WordTool14");
		
		URL url = ClassLoader.getSystemResource("images/icon.png");
		
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(url));
		try{
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			setUIFont(new javax.swing.plaf.FontUIResource("맑은 고딕", Font.BOLD, 12));
			UIManager.put("TextArea.font", new javax.swing.plaf.FontUIResource("Gulim", Font.BOLD, 12));
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error during rendering", JOptionPane.ERROR_MESSAGE);
		}
		
		Border border = BorderFactory.createLineBorder(Color.decode("#d1d1d1"));
		optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		 
		checks = new JCheckBox[7]; // Prepare check boxes
		checks[0] = new JCheckBox("< inside >", true);
		checks[1] = new JCheckBox("\u300A and \u300B", true);
		checks[2] = new JCheckBox("Contents inside ( and )", true);
		checks[3] = new JCheckBox("Number comments", true);
		checks[4] = new JCheckBox("Chinese charaters", true);
		checks[5] = new JCheckBox("Except English, Korean, Number and Symbols", false);
		checks[6] = new JCheckBox("Clean", true);
		
		for(JCheckBox box : checks){
			optionPanel.add(box, "SOUTH");
		}
		
		JPanel regexPanel = new JPanel(new BorderLayout());
		regexPanel.add(new JLabel("Custom regex"), "West");
		customRegex = new JTextArea("\\[EDIT\\]|\\[편집\\]\n.*문서를 참고하십시오.");
		customRegex.setBorder(border);
		JScrollPane regexScr = new JScrollPane(customRegex);
		regexPanel.add(regexScr);
		
		JPanel stringPanel = new JPanel(new BorderLayout());
		stringPanel.add(new JLabel("Custom string"), "West");
		customString = new JTextArea("");
		customString.setBorder(border);
		JScrollPane stringScr = new JScrollPane(customString);
		stringPanel.add(stringScr);
		
		optionPanel.add(regexPanel);
		optionPanel.add(stringPanel);
		
		//prepare FileChooser
		fileChooseBtn = new JButton("Choose File");
		fileChooseBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
			    fc.setFileFilter(filter);
			    int returnVal = fc.showOpenDialog(MainActivity.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION){
			    	try {
			    		String str = Destroier.delete(fc.getSelectedFile(), checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
			    		if(checks[6].isSelected()){
			    			str = Destroier.removeNewLines(str);
			    		}
						resultArea.setText(str);
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(MainActivity.this, "File is not found", "No text source found", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainActivity.this, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
		
		JButton copyBtn = new JButton("Copy"); // copy button
		copyBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(resultArea.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Result text is empty", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(resultArea.getText());
				clipboard.setContents(selection, null);
			}
		});
		JButton Res2srcBtn = new JButton("Result2Source");
		Res2srcBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(resultArea.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Result text is empty", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					String res = resultArea.getText();
					String src = textArea.getText();
					resultArea.setText(src);
					textArea.setText(res);
				}
			}
			
		});
		
		//prepare ConvertButton
		convertBtn = new JButton("Eliminate");
		convertBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean status = false;
				for(JCheckBox checkBox:checks){
					if(checkBox.isSelected()){
						status = true;
						break;
					}
				}
				if(status == false){
					JOptionPane.showMessageDialog(MainActivity.this, "Please select elimination options", "ERROR", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(!textArea.getText().equals("")){
					String str = Destroier.delete(textArea.getText(), checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
					if(checks[6].isSelected()){
						str = Destroier.removeNewLines(str);
					}
					resultArea.setText(str);
				}else{
					if(retFile == null){
						JOptionPane.showMessageDialog(MainActivity.this, "Select file or insert text","No text source found", JOptionPane.ERROR_MESSAGE);
					}else{
						try {
							String str = Destroier.delete(retFile, checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
							if(checks[6].isSelected()){
								str = Destroier.removeNewLines(str);
							}
							resultArea.setText(str);
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(MainActivity.this, "Requested file is not found", "No text source found", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(MainActivity.this, e1.getMessage(), "ERROR",  JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		textArea = new JTextArea(10, 20);
		textArea.setBorder(border);
		textArea.setLineWrap(true);
		JScrollPane textAreaScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		contentPanel.add(textAreaScroll);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(fileChooseBtn);
		buttonPanel.add(convertBtn);
		buttonPanel.add(copyBtn);
		buttonPanel.add(clearBtn);
		buttonPanel.add(Res2srcBtn);
		contentPanel.add(buttonPanel);
		
		//bottomPanel = new JPanel();
		resultArea = new JTextArea(10, 20);
		resultArea.setEditable(false);
		resultArea.setBorder(border);
		resultArea.setLineWrap(true);
		//contentPanel.add(bottomPanel, "South");
		
		JScrollPane resultAreaScroll = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//bottomPanel.add(resultAreaScroll);
		contentPanel.add(resultAreaScroll, "South");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(contentPanel, "West");
		mainPanel.add(optionPanel, "East");
		
		this.setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	@SuppressWarnings("rawtypes")
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 
}