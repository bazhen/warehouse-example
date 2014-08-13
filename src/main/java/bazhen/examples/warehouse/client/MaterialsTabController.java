package bazhen.examples.warehouse.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import bazhen.examples.warehouse.model.Material;
import bazhen.examples.warehouse.model.MaterialManager;

/**
 * 
 * @author bazheneg
 *
 */
public class MaterialsTabController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Material, String> idColumn;

    @FXML
    private TableView<Material> materialTable;

    @FXML
    private TableColumn<Material, String> nameColumn;
    
    @FXML
    private TextField newName;

	private ObservableList<Material> materialList;

    @FXML
    void onAdd(ActionEvent event) {
    	Material entity = MaterialManager.createMaterial(newName.getText());
		materialList.add(entity);
    }
    
	@FXML
    void onDelete(ActionEvent event) {
    	Material material = materialTable.getSelectionModel().getSelectedItem();
    	MaterialManager.removeMaterial(material);
		materialList.remove(material);
    }

	@FXML
    void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<Material, String>("id"));
		materialList = FXCollections.observableList(MaterialManager.getAllMaterials());
		materialTable.setItems(materialList);
    }

}

