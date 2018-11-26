import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Fx extends Application {

    Main queries=new Main();

    public static void main(String[] args) {
        launch(args);
    }

    public static String col="", num="",usName="",date="",first_day="";

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        stage.setTitle("Data Base queries");


        /**buttons for user control**/

/**1**/
        BorderPane box10 = new BorderPane();
        final TextField text10 = new TextField(col);
        Label label10 = new Label("Car color");
        box10.setLeft(label10);
        box10.setRight(text10);

        BorderPane box11 = new BorderPane();
        final TextField text11 = new TextField(num);
        Label label11 = new Label("Car number");
        box11.setLeft(label11);
        box11.setRight(text11);

        BorderPane box12 = new BorderPane();
        final TextField text12 = new TextField(usName);
        Label label12 = new Label("Username");
        box12.setLeft(label12);
        box12.setRight(text12);

        BorderPane box20 = new BorderPane();
        final TextField text20 = new TextField(date);
        Label label20 = new Label("Date");
        box20.setLeft(label20);
        box20.setRight(text20);



        final Button but1 = new Button("Query 1");
        final TextArea textArea= new TextArea();
        but1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                col = String.valueOf(text10.getText());
                num = String.valueOf(text11.getText());
                usName = String.valueOf(text12.getText());
                try {
                    textArea.setText(queries.q1(col,num,usName));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });


        final Button but2 = new Button("Query 2");
        but2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                date = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q2(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but3 = new Button("Query 3");
        but3.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                first_day = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q3(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but4 = new Button("Query 4");
        but4.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                usName = String.valueOf(text12.getText());
                date = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q4(usName,date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but5 = new Button("Query 5");
        but5.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                date = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q5(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but6 = new Button("Query 6");
        but6.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    textArea.setText(queries.q6());
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but7 = new Button("Query 7");
        but7.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                date = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q7(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

        final Button but8 = new Button("Query 8");
        but8.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                date = String.valueOf(text20.getText());
                try {
                    textArea.setText(queries.q8(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                    textArea.setText("something is wrong");
                }


            }
        });

//        final Button but9 = new Button("Query 9");
//        but9.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                try {
//                    textArea.setText(queries.q9());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    textArea.setText("something is wrong");
//                }
//
//
//            }
//        });

//        final Button but10 = new Button("Query 10");
//        but10.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                String result="";
//                try {
//                    textArea.setText(queries.q10());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    textArea.setText("something is wrong");
//                }
//
//
//            }
//        });

        VBox dataVBox = new VBox();
        dataVBox.getChildren().addAll(box10, box11,box12,box20);

        VBox typeVBox = new VBox();
        typeVBox.getChildren().addAll(but1,but2,but3,but4,but5,but7,but8,but6/*,but9,but10*/);

        HBox topBox = new HBox();
        topBox.setSpacing(5);
        topBox.getChildren().addAll(typeVBox, dataVBox);
        topBox.setPadding(new Insets(0, 10, 10, 10));

        BorderPane bord = new BorderPane();
        bord.setTop(topBox);

        bord.setCenter(textArea);


        root.getChildren().add(bord);
        stage.setScene(new Scene(root, 655, 600));
        stage.show();
    }
}
