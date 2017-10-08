package sf.codingcomp.blocks.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.solution.BuildingBlockImpl;
import sf.codingcomp.blocks.solution.StorageBuildingBlockImpl;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.lang.reflect.Method;

public class Main extends Application {
	ArrayList<StorageBuildingBlockImpl> listOfBlocks = new ArrayList<>();
	ObservableList<String> listOfBlockStrings = FXCollections.observableArrayList("");
	HashMap<String, StorageBuildingBlockImpl> map = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(750);
		
		GridPane mainGrid = new GridPane();
		
		GridPane controls = makeControlsPane();
		mainGrid.add(controls, 0, 0,5,1);
		
		HBox buildingBlockView = new HBox(5);
		buildingBlockView.getChildren().add(new Label("Canvas"));
		mainGrid.add(buildingBlockView, 5, 0, 10, 1);
		Scene pane = new Scene(mainGrid);
		primaryStage.setScene(pane);
		primaryStage.show();
		
	}
	public GridPane makeControlsPane() {
		GridPane controls = new GridPane();
		Label title = new Label("Let's Build Blocks!");
		controls.add(title, 0, 0, 1, 1);
		Label addLbl = new Label("Add Block");
		controls.add(addLbl, 0, 1, 1, 1);
		TextField txt = new TextField();
		txt.setPromptText("Enter value of Block");
		controls.add(txt, 1, 1, 1, 1);
		ComboBox<String> options = new ComboBox<String>();
		options.getItems().addAll("stackOver", "stackUnder");
		controls.add(options, 1, 2, 1, 1);
		StorageBuildingBlockImpl SB1 = new StorageBuildingBlockImpl();
		SB1.setValue("First");
		listOfBlocks.add(SB1);
		StorageBuildingBlockImpl SB2 = new StorageBuildingBlockImpl();
		SB2.setValue("Second");
		listOfBlocks.add(SB2);
		ComboBox<String> currentBlocks = new ComboBox<String>();
		for (StorageBuildingBlockImpl aBlock : listOfBlocks) {
			updateObservableList(aBlock.toString(), aBlock);
		}
		currentBlocks.setItems(listOfBlockStrings);
		controls.add(currentBlocks, 1, 3, 1, 1);
		
		Button addBlock = new Button("Add Block");
		addBlock.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    		String selectedOption = options.getValue();
		    		StorageBuildingBlockImpl temp = new StorageBuildingBlockImpl();
		    		try {
						Method m = BuildingBlockImpl.class.getMethod(selectedOption, new Class[] { BuildingBlock.class });
						StorageBuildingBlockImpl arg = map.get(txt.getText());
						m.invoke(temp, arg);
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		temp.setValue(txt.getText());
		    		listOfBlocks.add(temp);
		    		currentBlocks.setItems(listOfBlockStrings);
		    }
		});
		controls.add(addBlock, 0, 5,1,1);
		return controls;
	}
	public void updateObservableList(String newBlock, StorageBuildingBlockImpl block) {
		listOfBlockStrings.add(newBlock);
		map.put(newBlock, block);
	}
	public void addBlocks(HBox pane) {
		HashSet<BuildingBlock> added = new HashSet<>();
		for (BuildingBlock one : listOfBlocks) {
			if (!added.contains(one)) {
				for (BuildingBlock two : one) {
					if (!added.contains(two)) {
						added.add(two);
						Rectangle rec = new Rectangle(25, 18);
						pane.getChildren().add(0, rec);
					}
				}
			}
		}
	}

}
