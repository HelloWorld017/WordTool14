package org.onebone.He.W.wordtool14;

//------------------About Clipboard------------------
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

//------------------About Files------------------
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


//------------------JavaFX GUI------------------
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

public class MainActivity extends Application {
	
	//------------------Declare variables------------------
	private CheckBox[] checks;
	private VBox optionPanel, rightWrapper;
	private HBox buttonWrapper;
	private GridPane root, leftWrapper;
	private BorderPane regexPanel, stringPanel;
	private TextArea customString, customRegex, textArea, resultArea;
	private Button fileChooseBtn, convertBtn, copyBtn, clearBtn, Res2srcBtn ;
	
	@Override
	public void start(Stage stage) throws Exception {
		//------------------closeSplash------------------
		
		Splash.dispose();
		
		//------------------optionPanel------------------
		optionPanel = new VBox();
		optionPanel.getStyleClass().add("background");
		checks = new CheckBox[9]; // Prepare check boxes
		checks[0] = new CheckBox("< inside >");
		checks[1] = new CheckBox("\u300A and \u300B");
		checks[2] = new CheckBox("Contents inside ( and )");
		checks[3] = new CheckBox("Number comments");
		checks[4] = new CheckBox("Chinese charaters");
		checks[5] = new CheckBox("Except English, Korean, Number and Symbols");
		checks[6] = new CheckBox("Empty Lines");
		checks[7] = new CheckBox("Empty Brackets");
		checks[8] = new CheckBox("Use CRNF (if newline has been deleted.)");
		
		for(CheckBox box : checks){
			if(box == checks[5]){
				box.setSelected(false);
			}else{
				box.setSelected(true);
			}
			optionPanel.getChildren().add(box);
		
		}
		//------------------add checkBoxes------------------
		
		//------------------regexPanel------------------
		regexPanel = new BorderPane();
		regexPanel.getStyleClass().add("background");
		Label customRegexLabel = new Label("Custom Regex");
		customRegexLabel.getStyleClass().add("item-title");
		regexPanel.setLeft(customRegexLabel);
		customRegex = new TextArea("\\[EDIT\\]|\\[편집\\]\n.*문서를 참고하십시오[.]");
		ScrollPane regexScr = new ScrollPane(customRegex);
		regexScr.setScaleX(optionPanel.getScaleX());
		regexScr.setScaleY(optionPanel.getScaleY());
		regexScr.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		regexScr.setVbarPolicy(ScrollBarPolicy.NEVER);
		regexPanel.setBottom(regexScr);
		
		//------------------customPanel------------------
		stringPanel = new BorderPane();
		stringPanel.getStyleClass().add("background");
		Label customStringLabel = new Label("Custom String");
		customStringLabel.getStyleClass().add("item-title");
		stringPanel.setLeft(customStringLabel);
		customString = new TextArea("");
		ScrollPane stringScr = new ScrollPane(customString);
		stringScr.setScaleX(optionPanel.getScaleX());
		stringScr.setScaleY(optionPanel.getScaleY());
		stringPanel.setBottom(stringScr);
		
		//------------------rightWrapper------------------
		rightWrapper = new VBox();
		rightWrapper.getStyleClass().add("background");
		rightWrapper.getChildren().add(optionPanel);
		rightWrapper.getChildren().add(regexPanel);
		rightWrapper.getChildren().add(stringPanel);
		
		//------------------Start drawing left side------------------
		
		//------------------addButtons------------------
		
		//------------------fileChooseBtn------------------
		fileChooseBtn = new Button("Choose File");
		fileChooseBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fc = new FileChooser();
			    ExtensionFilter TextFile = new ExtensionFilter("Text File", "*.txt");
			    ExtensionFilter AllFiles = new ExtensionFilter("All Files", "*.*");
			    fc.getExtensionFilters().add(TextFile);
			    fc.getExtensionFilters().add(AllFiles);
			    File returnVal = fc.showOpenDialog(stage);
			    if(returnVal != null){
			    	try {
			    		String str = Destroier.delete(returnVal, checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
			    		if(checks[6].isSelected()){
			    			str = Destroier.removeNewLines(str);
			    		}
			    		if(checks[7].isSelected()){
			    			str = Destroier.removeEmptyBrackets(str);
			    		}
						resultArea.setText(str);
					} catch (FileNotFoundException e1) {
						Dialogs.create().owner(stage).title("Error!").masthead("File is not found.").message("File is not found.\r\nIf this can't find file, please use textbox.\r\nSorry for inconvenience.").showError();
					} catch (IOException e1) {
						Dialogs.create().owner(stage).title("Error!").masthead("An IOException is occurred.").showException(e1);
					}
			    }
			}
		});
		
		//------------------convertBtn------------------
				convertBtn = new Button("Eliminate");
				convertBtn.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e) {
						boolean status = false;
						for(CheckBox checkBox:checks){
							if(checkBox.isSelected()){
								status = true;
								break;
							}
						}
						if(status == false){
							Dialogs.create().owner(stage).title("Error!").masthead(null).message("Please select elimination options.").showInformation();
							return;
						}
						if(!textArea.getText().equals("")){
							String str = Destroier.delete(textArea.getText(), checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
							if(checks[6].isSelected()){
								str = Destroier.removeNewLines(str);
							}
							if(checks[7].isSelected()){
				    			str = Destroier.removeEmptyBrackets(str);
				    		}
							if(checks[8].isSelected()){
								str = Destroier.useCRNF(str);
							}
							resultArea.setText(str);
						}else{
							Dialogs.create().owner(stage).title("Error!").masthead("Input is empty").message("Please input text!").showInformation();
						}
					}
				});
		
		//------------------copyBtn------------------
		copyBtn = new Button("Copy");
		copyBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				if(resultArea.getText().equals("")){
					Dialogs.create().owner(stage).title("Error!").masthead("Result text is empty.").message("Result text is empty. Please elimnate text first.").showInformation();
					return;
				}
				try{
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection selection = new StringSelection(resultArea.getText());
					clipboard.setContents(selection, null);
				}catch(IllegalStateException e1){
					Dialogs.create().owner(stage).title("Error!").masthead("Clipboard is currently unavailable.").message("Clipboard is currently unavailable.\r\nPlease try again.\r\nIf you are seeing this message more than one time, try to kill another task that is using Clipboard or use ctrl+c").showError();
				}catch(Exception e2){
					Dialogs.create().owner(stage).title("Error!").masthead("An Exception has been occured").showException(e2);
				}
			}
		});
		
		//------------------clearBtn------------------
		clearBtn = new Button("Clear");
		clearBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				textArea.setText("");
			}
		});
		
		//------------------Res2srcBtn------------------
				Res2srcBtn = new Button("Result2Source");
				Res2srcBtn.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						if(resultArea.getText().equals("")){
							Dialogs.create().owner(stage).title("Error!").masthead("Result text is empty.").message("Result text is empty. Please elimnate text first.").showInformation();
							return;
						}else{
							String res = resultArea.getText();
							String src = textArea.getText();
							resultArea.setText(src);
							textArea.setText(res);
						}
					}
					
				});
		
		//------------------textArea------------------		
		textArea = new TextArea();
		textArea.setWrapText(true);
				
		ScrollPane textAreaScroll = new ScrollPane(textArea);
		textAreaScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		textAreaScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		textAreaScroll.setFitToHeight(true);
		textAreaScroll.setFitToWidth(true);
				
		//------------------resultArea------------------
		resultArea = new TextArea();
		resultArea.setEditable(false);
		resultArea.setWrapText(true);
		
		ScrollPane resultAreaScroll = new ScrollPane(resultArea);
		resultAreaScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		resultAreaScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		resultAreaScroll.setFitToHeight(true);
		resultAreaScroll.setFitToWidth(true);
		
		//------------------buttonWrapper------------------
		buttonWrapper = new HBox();
		buttonWrapper.getStyleClass().add("background");
		buttonWrapper.getChildren().add(fileChooseBtn);
		buttonWrapper.getChildren().add(convertBtn);
		buttonWrapper.getChildren().add(copyBtn);
		buttonWrapper.getChildren().add(clearBtn);
		buttonWrapper.getChildren().add(Res2srcBtn);
	
		//------------------Constraints------------------
		ColumnConstraints columnConstraints = new ColumnConstraints();
	    columnConstraints.setFillWidth(true);
	    columnConstraints.setHgrow(Priority.ALWAYS);
	    RowConstraints rowConstraints = new RowConstraints();
	    rowConstraints.setFillHeight(true);
	    rowConstraints.setVgrow(Priority.ALWAYS);
	    
		//------------------leftWrapper------------------
		leftWrapper = new GridPane();
		leftWrapper.getStyleClass().add("background");
		leftWrapper.add(textAreaScroll, 0, 0);
		leftWrapper.add(buttonWrapper, 0, 1);
		leftWrapper.add(resultAreaScroll, 0, 2);
	    leftWrapper.getColumnConstraints().add(columnConstraints);
	    leftWrapper.getRowConstraints().add(rowConstraints);
	    
		
		//------------------root------------------
		root = new GridPane();
		root.getStyleClass().add("background");
		root.add(leftWrapper, 0, 0);
		root.add(rightWrapper, 1, 0);
	    root.getColumnConstraints().add(columnConstraints);
	    root.getRowConstraints().add(rowConstraints);
						
		//------------------evanescent------------------
		Scene evanescent = new Scene(root);
		evanescent.getStylesheets().add("resources/JMetroDarkTheme.css");
				
		//------------------stage------------------
		stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("resources/icon.png")));
		stage.setTitle("WordTool 14");
		stage.setScene(evanescent);
		stage.show();
		
		//------------------Trash of swing------------------
		//textArea.setBorder(border);
		//customString.setBorder(border);
		//customRegex.setBorder(border);
		//resultArea.setBorder(border);
		

	}
	public static void lc(String[] args){
		launch(args);
	}
	
	public static void main(String[] args){
		try {
		//------------------Splash Screen (java.awt.SplashScreen is not compatible with JavaFX8)------------------
		
		//------------------Java Version check------------------
		String java_version = System.getProperty("java.specification.version");
		System.out.println(java_version);
		
		if(Integer.parseInt(java_version.split("[.]")[1]) >= 8){
		//------------------If java version is over than 1.8------------------
			Splash.splash(true, args);
		}else{
			Splash.splash(false, args);
		}
		
		}catch (Exception e){
			System.exit(0);
		}
	}

}
