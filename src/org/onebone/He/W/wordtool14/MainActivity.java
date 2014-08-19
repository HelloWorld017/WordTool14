package org.onebone.He.W.wordtool14;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainActivity extends Application {
	private CheckBox[] checks;
	private VBox optionPanel, rightWrapper;
	private BorderPane root, regexPanel, stringPanel;
	private TextArea customString, customRegex, resultArea;
	private Button fileChooseBtn;
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		//------------------optionPanel------------------
		optionPanel = new VBox();
		optionPanel.getStyleClass().add("background");
		checks = new CheckBox[7]; // Prepare check boxes
		checks[0] = new CheckBox("< inside >");
		checks[1] = new CheckBox("\u300A and \u300B");
		checks[2] = new CheckBox("Contents inside ( and )");
		checks[3] = new CheckBox("Number comments");
		checks[4] = new CheckBox("Chinese charaters");
		checks[5] = new CheckBox("Except English, Korean, Number and Symbols");
		checks[6] = new CheckBox("Clean");
		
		for(CheckBox box : checks){
			if(box == checks[5]){
				box.setSelected(false);
			}else{
				box.setSelected(true);
			}
			optionPanel.getChildren().add(box);
		
		}
		//add checkBoxes
		
		//------------------regexPanel------------------
		regexPanel = new BorderPane();
		regexPanel.getStyleClass().add("background");
		Label customRegexLabel = new Label("Custom Regex");
		customRegexLabel.getStyleClass().add("item-title");
		regexPanel.setLeft(customRegexLabel);
		customRegex = new TextArea("\\[EDIT\\]|\\[편집\\]\n.*문서를 참고하십시오.");
		ScrollPane regexScr = new ScrollPane(customRegex);
		regexScr.setScaleX(optionPanel.getScaleX());
		regexScr.setScaleY(optionPanel.getScaleY());
		regexPanel.setBottom(regexScr);
		
		//customString.setBorder(border);
		//customRegex.setBorder(border);
		
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
		
		//Start Left drawing
		
		//------------------fileChooseBtn------------------
		fileChooseBtn = new Button("Choose File");
		fileChooseBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fc = new FileChooser();
			    ExtensionFilter filter = new ExtensionFilter("Text File", "txt");
			    fc.getExtensionFilters().add(filter);
			    File returnVal = fc.showOpenDialog(stage);
			    if(returnVal != null){
			    	try {
			    		String str = Destroier.delete(returnVal, checks[0].isSelected(), checks[1].isSelected(), checks[2].isSelected(), checks[3].isSelected(), checks[4].isSelected(), checks[5].isSelected(), customRegex.getText(), customString.getText());
			    		if(checks[6].isSelected()){
			    			str = Destroier.removeNewLines(str);
			    		}
						resultArea.setText(str);
					} catch (FileNotFoundException e1) {
						//OptionPane.showMessageDialog(MainActivity_Legacy.this, "File is not found", "No text source found", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						//JOptionPane.showMessageDialog(MainActivity_Legacy.this, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
		
		root = new BorderPane();
		root.setRight(rightWrapper);
		Scene evanescent = new Scene(root);
		evanescent.getStylesheets().add("resources/JMetroDarkTheme.css");
		
		stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/icon.png")));
		stage.setTitle("WordTool 14");
		stage.setScene(evanescent);
		stage.show();
	}
	public static void main(String[] args){
		launch(args);
	}

}
