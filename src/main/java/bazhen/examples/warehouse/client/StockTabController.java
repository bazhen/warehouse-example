package bazhen.examples.warehouse.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.binding.StringFormatter;

import bazhen.examples.warehouse.model.Material;
import bazhen.examples.warehouse.model.MaterialManager;
import bazhen.examples.warehouse.model.StockManager;
import bazhen.examples.warehouse.model.StockPosition;

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
public class StockTabController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<StockPosition> materialTable;

    @FXML
    private TableColumn<StockPosition, String> idColumn;

    @FXML
    private TableColumn<StockPosition, String> nameColumn;

    @FXML
    private TableColumn<StockPosition, Integer> quantityColumn;
    
    @FXML
    private TextField quantity;
    
    @FXML
    private ListView<String> materials;

	private ObservableList<StockPosition> stockMaterials;

    @FXML
    void onAdd(ActionEvent event) {
		int quantity = Integer.valueOf(this.quantity.getText());
		String material = materials.getSelectionModel().getSelectedItem();
		StockPosition entity = StockManager.createStockPosition(material, quantity);
		stockMaterials.add(entity);
    }
    
	@FXML
    void onDelete(ActionEvent event) {
    	StockPosition material = materialTable.getSelectionModel().getSelectedItem();
    	StockManager.removeStockPosition(material);
		stockMaterials.remove(material);
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
		nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StockPosition,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(final CellDataFeatures<StockPosition, String> arg) {
				return new StringFormatter() {
					
					@Override
					protected String computeValue() {
						 return arg.getValue().getMaterial().getName();
					}
				};
			}
		});
		idColumn.setCellValueFactory(new PropertyValueFactory<StockPosition, String>("id"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<StockPosition, Integer>("amount"));
		stockMaterials = FXCollections.observableList(StockManager.getAllStockPositions());
		materialTable.setItems(stockMaterials);
	}

}

