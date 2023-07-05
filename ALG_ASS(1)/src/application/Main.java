package application;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

//import java.awt.TextField;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
	private static final int NO_P = -1;
	private static final int I = 999;

	@Override
	public void start(Stage primaryStage) {

		try {
			Pane root = new Pane();
			Scene scene = new Scene(root, 400, 400);

			int arr[][] = new int[39][100];

			File myObj = new File("C:\\Users\\moham\\Desktop\\412,640.txt");
			Scanner myReader = new Scanner(myObj);
			int o = 0;

			String names[] = new String[39];

			int adjacencyMatrix[][] = new int[39][39];
			for (int i = 0; i < 39; i++) {
				for (int p = 0; p < 39; p++) {
					adjacencyMatrix[i][p] = 0;
				}
			}

			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();

				if (o >= 39 && line.contains(",")) {
					String r[] = line.split(",");
					String s[] = r[1].split("    ");
					// String rr[]=(r[1]).split(" ");
					adjacencyMatrix[Integer.parseInt(r[0])][Integer.parseInt(s[0])] = Integer.parseInt(s[1]);

				} else {
					if (line != "" && line.contains(",")) {
						String array[] = line.split("-");
						arr[o][0] = Integer.parseInt(array[0].replaceAll(" ", ""));
						String h[] = array[2].split(",");
						names[o] = array[1];
						arr[o][1] = Integer.parseInt(h[0].replaceAll(" ", ""));
						arr[o][2] = Integer.parseInt(h[1].replaceAll(" ", ""));
						o++;
					}
				}

			}

			Image image = new Image(new FileInputStream("D:\\Birzeit University 2.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(800);
			imageView.setFitWidth(1200);
			Button run = new Button("Run");
			run.setTranslateX(1370);
			run.setTranslateY(250);
			Text source = new Text(1220, 20, "Source :");
			source.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

			Text target = new Text(1220, 150, "Target : ");
			target.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

			Text path = new Text(1220, 350, "Path :");
			path.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

			Text distance = new Text(1220, 500, "Distance :");
			distance.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

			ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(names));
			combo_box.setLayoutX(1320);

			ComboBox combo_box2 = new ComboBox(FXCollections.observableArrayList(names));

			combo_box2.setLayoutX(1320);
			combo_box2.setLayoutY(136);

			TextField textField = new TextField();
			textField.setTranslateX(1210);
			textField.setTranslateY(360);
			textField.setPrefSize(320, 120);
			Label lll = new Label();

			TextField textFiel = new TextField();
			textFiel.setTranslateX(1210);
			textFiel.setTranslateY(510);
			textFiel.setPrefSize(320, 120);

			// Line line = new Line(10,10,10,10);
			// line.setStrokeWidth(10);
			root.getChildren().addAll(imageView, combo_box2, combo_box, source, target, path, distance, run, lll);
			root.getChildren().addAll(textField, textFiel);
			imageView.setX(0);
			imageView.setY(0);

			combo_box.setLayoutY(8);
			// int my[][]=new int [2][2];

			run.setOnAction(e -> {

				Group rooot = new Group();
				Scene scenne = new Scene(rooot, 600, 300);
				String ss[] = dijkstra(adjacencyMatrix, combo_box.getSelectionModel().getSelectedIndex(),
						combo_box2.getSelectionModel().getSelectedIndex()).split(" ");
				textField.setText(ss[1]);

				textFiel.setText(ss[0]);

				String yy[] = textField.getText().split("->");
				int uj = yy.length;
				// System.out.print(uj);
				int mee[] = new int[uj];
				for (int jj = 1; jj < yy.length; jj++) {
					System.out.println(yy[jj]);
					mee[jj] = Integer.parseInt(yy[jj]);

				}

				for (int fff = 1; fff < yy.length - 1; fff++) {
					Line linee = new Line(arr[mee[fff]][1], arr[mee[fff]][2], arr[mee[fff + 1]][1],
							arr[mee[fff + 1]][2]);
					// Line linee = new Line(10,20,20,40);
					linee.setStrokeWidth(10);
					root.getChildren().remove(linee);
					root.getChildren().add(linee);

					// rooot.add(linee);

				}
				// scenne.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				// primaryStage.setScene(scenne);
				// primaryStage.show();
				// Creating a Scene

				// root.getChildren().addAll(linee);

			});

			myReader.close();

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String dijkstra(int[][] gr, int start, int endy) {
		int n = gr[0].length;

		int[] shortestDistances = new int[n];

		boolean[] added = new boolean[n];

		for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		shortestDistances[start] = 0;

		int[] parents = new int[n];

		parents[start] = NO_P;

		for (int i = 1; i < n; i++) {

			int nearest = 0;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearest = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];

				}
				added[nearest] = true;
			}

			for (int v = 0; v < n; v++) {
				int edgeDistance = gr[nearest][v];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[v])) {
					parents[v] = nearest;
					shortestDistances[v] = shortestDistance + edgeDistance;
				}
			}
		}

		return ans(start, shortestDistances, parents, endy);
	}

	private static String ans(int start, int[] d, int[] p, int endy) {
		String sol = "";
		int n = d.length;

		for (int v = 0; v < n; v++) {
			if ((v != start) && v == endy) {

				sol = sol + d[v] + " ";

				sol = sol + vert(v, p, "");

			}

		}
		return sol;
	}

	private static String vert(int Vertex, int[] parents, String h) {

		if (Vertex != -1) {

			h = "->" + Vertex + h;

		}
		if (Vertex == -1) {
			return h;
		}

		return vert(parents[Vertex], parents, h);

	}

	public static void main(String[] args) {

		launch(args);

	}
}
