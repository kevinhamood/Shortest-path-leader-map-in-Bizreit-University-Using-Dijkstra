package application;

import java.io.File;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {
	Compress compressFile;

	@Override

	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();

			// top
			VBox top = new VBox();
			HBox htop = new HBox();
			HBox htop1 = new HBox();

			Button btcompress = new Button("compress");
			Button btextract = new Button("extract");

			btcompress.setStyle(
					"-fx-background-color: #cccccc; -fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
			btextract.setStyle("-fx-background-color: #cccccc; -fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
			htop.getChildren().addAll(btcompress, btextract);
			htop.setSpacing(70);
			top.getChildren().addAll(htop);

			top.setStyle("-fx-background-color: #bbbbb ;-fx-background-radius: 0px 0px 20% 20% ;");
			btcompress.setPadding(new Insets(8, 60, 8, 60));
			btextract.setPadding(new Insets(8, 65, 8, 65));
			htop.setPadding(new Insets(30, 10, 30, 10));
			root.setTop(top);

			Label Ls = new Label("Source file");
			Label fsource = new Label("No foder selected");
			fsource.setStyle(
					"-fx-background-color: WHITE; -fx-text-fill: black; -fx-border-color: black;-fx-padding: 5;");

			VBox v1 = new VBox();
			v1.getChildren().addAll(Ls, fsource);
			v1.setAlignment(Pos.CENTER);

			Label Ld = new Label("Destination file");
			Label fdist = new Label("No foder selected");
			fdist.setStyle(
					"-fx-background-color: WHITE; -fx-text-fill: black; -fx-border-color: black;-fx-padding: 5;");
			VBox v2 = new VBox();
			v2.getChildren().addAll(Ld, fdist);
			v2.setAlignment(Pos.CENTER);

			htop1.getChildren().addAll(v1, v2);
			htop1.setSpacing(70);
			htop.setAlignment(Pos.CENTER);
			htop1.setAlignment(Pos.CENTER);

			htop1.setPadding(new Insets(10, 10, 20, 10));

			// options

			Button btHeader = new Button("Header");
			btHeader.setPadding(new Insets(10, 50, 10, 50));
			btHeader.setStyle("-fx-background-color: #bbbbbb; -fx-text-fill: black;-fx-font-size: 16px;");

			Button btHaffman = new Button("Haffman Code");
			btHaffman.setPadding(new Insets(10, 26, 10, 26));
			btHaffman.setStyle("-fx-background-color: #bbbbbb; -fx-text-fill: black;-fx-font-size: 16px;");

			Button btFrequancy = new Button("Frequancy");
			btFrequancy.setPadding(new Insets(10, 40, 10, 40));
			btFrequancy.setStyle("-fx-background-color: #bbbbbbb; -fx-text-fill: black;-fx-font-size: 16px;");

			HBox options = new HBox();
			options.getChildren().addAll(btHeader, btHaffman, btFrequancy);
			options.setSpacing(45);
			options.setAlignment(Pos.CENTER);
			options.setPadding(new Insets(20, 10, 20, 10));

			Label masseg = new Label("Select file where files located");
			masseg.setAlignment(Pos.CENTER);
			masseg.setStyle(" -fx-text-fill: #ddddddd ;-fx-font-size: 30px;-fx-font-weight: bold;");
			VBox Center = new VBox();
			root.setCenter(Center);
			Center.setAlignment(Pos.CENTER);
			Center.setSpacing(10);
			Center.setStyle("-fx-background-color: #EEFFFA");

			javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea();

			textArea.setStyle("-fx-background-color:  #f2f2f2");
			textArea.setPrefHeight(300);
			textArea.setMaxWidth(560);

			Scene scene = new Scene(root, 750, 680);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			btcompress.setOnAction(e -> {
				// read file from file browser

				try {
					FileChooser inputFile = new FileChooser();
					inputFile.setTitle("Open source File to compress");
					inputFile.setInitialDirectory(new File("C:\\Users\\"));
					File selectedFile = inputFile.showOpenDialog(primaryStage);
					String File = selectedFile.getPath();

					compressFile = new Compress(File);
					fsource.setText(File);
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Set Destination File to Save");
					fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HUF file ", "*.huf"));
					File savefile = fileChooser.showSaveDialog(primaryStage);
					String save = savefile.getPath();
					fdist.setText(save);
					masseg.setText("In progress... ");

					compressFile.readFile();
					compressFile.createHeap();
					compressFile.writeHuffmanCode(save);

					Center.getChildren().removeAll(htop1, options, masseg, textArea);
					Center.getChildren().addAll(htop1, options, masseg, textArea);
					btHaffman.setVisible(true);
					btHeader.setVisible(true);
					btFrequancy.setVisible(true);
					masseg.setText("Successfully compressed ");

				} catch (Exception e1) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("Eror! choose file again.");
					alert.show();
				}

			});

			btextract.setOnAction(e -> {
				// read file from file browser

				try {
					FileChooser inputFile = new FileChooser();
					inputFile.setTitle("Open source File to Decompress");
					File selectedFile = inputFile.showOpenDialog(primaryStage);
					String File = selectedFile.getPath();
					inputFile.setInitialDirectory(new File("C:\\Users\\Home\\Desktop"));
					compressFile = new Compress(File);

					if (File.endsWith(".huf")) {
						masseg.setText("In progress... ");
						Center.getChildren().removeAll(htop1, options, masseg, textArea);
						Center.getChildren().addAll(htop1, masseg);

						Decompress decompress = new Decompress(File);
						try {
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Set Destination File to Save");
							File savefile = fileChooser.showSaveDialog(primaryStage);
							String save = savefile.getPath();
							fdist.setText(save);
							String file = decompress.readHuffFile(save);
							fsource.setText(File);
							masseg.setText("Sucessfully Decompressed");

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setContentText("Selected file should be (.huf) file, choose another one.");
						alert.show();

					}

				} catch (Exception e1) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("Eror! choose file again.");
					alert.show();
				}

			});

			btFrequancy.setOnAction(e -> {
				// print freq/ count for each byte
				textArea.setText("Byte \t frequency \n");
				Counter counter[] = compressFile.counter;
				
				for (int i = 0; i < counter.length; i++) {					
				textArea.setText(textArea.getText() +"\""+ ((char) counter[i].byteCount)+ "\"      " + counter[i].intCount + "\n");
				}
			});

			btHeader.setOnAction(e -> {
				Header header = compressFile.getHeader();
				textArea.setText("File Name: " + header.getFileName() + "\nFile Size: " + header.getFileSize() + "\n "
						+ "Byte " + "     " + "Count \n");
				// view on screen
				for (int i = 0; i < header.getBytes().length; i++)
					textArea.setText(
							textArea.getText() + header.getBytes()[i] + "      " + header.getCount()[i] + "\n");

			});

			btHaffman.setOnAction(e -> {
				// print huffman code into screen
				textArea.setText("Byte \t Huffman Code \n");
				textArea.setText( "\nCompression Ratio: " + compressFile.getRatio() + "%\n ");

				

				Huffman table[] = compressFile.getHuffTable();
				for (int i = 0; i < table.length; i++)
					textArea.setText(textArea.getText() + table[i].byteCount + " \t " + table[i].Huffman + "\n");
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
