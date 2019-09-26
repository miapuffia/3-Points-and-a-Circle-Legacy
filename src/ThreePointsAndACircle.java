import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ThreePointsAndACircle extends Application {
	static Circle point1;
	static Circle point2;
	static Circle point3;
	static Circle circle = new Circle();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		int width = (int) Screen.getPrimary().getVisualBounds().getWidth();
		int height = (int) Screen.getPrimary().getVisualBounds().getHeight();
		
		point1 = new Circle(width / 2, height / 2.2, 10);
		point2 = new Circle(width / 2.2, height / 2.5, 10);
		point3 = new Circle(width / 3, height / 1.8, 10);
		
		point1.setOnMouseDragged(e -> {
			point1.setCenterX(e.getSceneX());
			point1.setCenterY(e.getSceneY());
			
			drawCircle();
		});
		
		point1.setOnMouseEntered(e -> point1.setCursor(Cursor.HAND));
		
		point2.setOnMouseDragged(e -> {
			point2.setCenterX(e.getSceneX());
			point2.setCenterY(e.getSceneY());
			
			drawCircle();
		});
		
		point2.setOnMouseEntered(e -> point2.setCursor(Cursor.HAND));
		
		point3.setOnMouseDragged(e -> {
			point3.setCenterX(e.getSceneX());
			point3.setCenterY(e.getSceneY());
			
			drawCircle();
		});
		
		point3.setOnMouseEntered(e -> point3.setCursor(Cursor.HAND));
		
		Tooltip tooltip1 = new Tooltip("Point 1");
		Tooltip tooltip2 = new Tooltip("Point 2");
		Tooltip tooltip3 = new Tooltip("Point 3");
		
		Tooltip.install(point1, tooltip1);
		Tooltip.install(point2, tooltip2);
		Tooltip.install(point3, tooltip3);
		
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		
		drawCircle();
		
		Label watermarkLabel = new Label("Created by Robert D. Rioja");
		watermarkLabel.setFont(Font.font("Verdana", 20));
		watermarkLabel.setLayoutX(5);
		
		Pane paneMain = new Pane(circle, point1, point2, point3, watermarkLabel);
		
		Scene scene = new Scene(paneMain);
		
		watermarkLabel.layoutYProperty().bind(scene.heightProperty().subtract(30));
		
		primaryStage.setTitle("3 Points and a Circle");
        primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		
		primaryStage.show();
	}
	
	private static void drawCircle() {
		double x1 = point1.getCenterX();
		double y1 = point1.getCenterY();
		
		double x2 = point2.getCenterX();
		double y2 = point2.getCenterY();
		
		double x3 = point3.getCenterX();
		double y3 = point3.getCenterY();
		
		double m12 = (point2.getCenterY() - point1.getCenterY()) / (point2.getCenterX() - point1.getCenterX());
		double m23 = (point3.getCenterY() - point2.getCenterY()) / (point3.getCenterX() - point2.getCenterX());
		
		double pb12x = ((x2 - x1) / 2) + x1;
		double pb12y = ((y2 - y1) / 2) + y1;
		double pb12m = - 1 / m12;
		double pb12b = pb12m * (-1 * pb12x) + pb12y;
		
		double pb23x = ((x3 - x2) / 2) + x2;
		double pb23y = ((y3 - y2) / 2) + y2;
		double pb23m = - 1 / m23;
		double pb23b = pb23m * (-1 * pb23x) + pb23y;
		
		double x = (pb23b - pb12b) / (pb12m - pb23m);
		double y = (pb12m * x) + pb12b;
		double r = Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
		
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setRadius(r);
	}
}
