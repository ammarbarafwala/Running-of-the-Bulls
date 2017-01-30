package lab9;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BullDriver extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Application.launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MazeGUIPane mz=new MazeGUIPane();
		mz.startGame();
		Scene sc=new Scene(mz);
		sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}

}
