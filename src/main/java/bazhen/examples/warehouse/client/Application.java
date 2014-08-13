package bazhen.examples.warehouse.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private Parent root;

	public static void main(String[] args) {
        Application.launch(Application.class, args);
    }
    
	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("main.fxml"));
		stage.setTitle("Warehouse Client");
		stage.setScene(new Scene(root));
		stage.show();
	}

}
