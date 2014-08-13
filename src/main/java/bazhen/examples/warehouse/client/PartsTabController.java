package bazhen.examples.warehouse.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.binding.StringFormatter;

import bazhen.examples.warehouse.model.Material;
import bazhen.examples.warehouse.model.MaterialManager;
import bazhen.examples.warehouse.model.ProductPart;
import bazhen.examples.warehouse.model.ProductPartManager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * 
 * @author bazheneg
 *
 */
public class PartsTabController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ProductPart> materialTable;

    @FXML
    private TableColumn<ProductPart, String> idColumn;

    @FXML
    private TableColumn<ProductPart, String> materialColumn;

    @FXML
    private TableColumn<ProductPart, String> nameColumn;

    @FXML
    private TableColumn<ProductPart, Integer> quantityColumn;
    
    @FXML
    private TextField quantity;
    
    @FXML
    private TextField name;
    
    @FXML
    private ListView<String> materials;

	private ObservableList<ProductPart> allParts;

    @FXML
    void onAdd(ActionEvent event) {
		int quantity = Integer.valueOf(this.quantity.getText());
		String material = materials.getSelectionModel().getSelectedItem();
		ProductPart entity = ProductPartManager.createProductPart(name.getText(), material, quantity);
		allParts.add(entity);
    }
    
	@FXML
    void onDelete(ActionEvent event) {
    	ProductPart productPart = materialTable.getSelectionModel().getSelectedItem();
    	ProductPartManager.removeProductPart(productPart);
		allParts.remove(productPart);
    }
	
	@FXML
	void onRefresh(ActionEvent event) {
		initMaterials();
	}
	

	@FXML
    void initialize() {
		initStock();
		initMaterials();
    }

	private void initMaterials() {
		List<String> allMaterials = createMaterialNames(MaterialManager.getAllMaterials());
		ObservableList<String> items = FXCollections.observableList(allMaterials);
		materials.setItems(items);
	}

	private List<String> createMaterialNames(List<Material> materials) {
		List<String> list = new ArrayList<>();
		for (Material material : materials) {
			list.add(material.getName());
		}
		return list;
	}

	private void initStock() {
		materialColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductPart,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(final CellDataFeatures<ProductPart, String> arg) {
				return new StringFormatter() {
					
					@Override
					protected String computeValue() {
						 return arg.getValue().getMaterial().getName();
					}
				};
			}
		});
		idColumn.setCellValueFactory(new PropertyValueFactory<ProductPart, String>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<ProductPart, String>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductPart, Integer>("quantity"));
		allParts = FXCollections.observableList(ProductPartManager.getAllProductParts());
		materialTable.setItems(allParts);
	}

}

