package com.example.event_planner;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static javafx.geometry.Pos.BASELINE_CENTER;


class Event extends Node {
    String text;
    boolean status;
    public Event(String s){
        this.text = s;
        this.status = false;
    }
    public void changeText(String s){
        text = s;
    }
}

public class HelloApplication extends Application {
    private Label time = new Label();
    private Label dt = new Label();
    private Label tm = new Label();
    private Label flags = new Label();
    private int completed = 0;
    private int total = 0;
    private int calendarSwitchFlag = 0;
    private Label tmp_lbl = new Label();
    private static final int firstLineHeight = 50;

//    ObservableList<String> langs = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
//    ComboBox<String> langsComboBox = new ComboBox<String>(langs);
//        langsComboBox.setValue("Java"); // устанавливаем выбранный элемент по умолчанию
    private ArrayList<Event> events = new ArrayList<Event>();

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {

        /////////////////////////////////////////////////////////////// 1 /////////////////////////////////////////////////////////////////////
//        #1
        ObservableList<String> settings = FXCollections.observableArrayList("Settings", "About", "Exit");
        ComboBoxBase<String> settingsComboBox = new ComboBox<String>(settings);
        settingsComboBox.setMaxSize(25, firstLineHeight);
        settingsComboBox.setMinSize(25, firstLineHeight);

        Label selectedMenuItem = new Label();
        settingsComboBox.setOnAction(event -> {
            selectedMenuItem.setText(settingsComboBox.getValue());
        });

//        #2
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalDateTime ldt_now = LocalDateTime.now();
//                time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss\ndd.MM.yyyy")));
                tm.setText(ldt_now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                dt.setText(ldt_now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        };
        timer.start();

        DateTimeFormatter formatter1, formatter2;
        formatter1 = DateTimeFormatter.ofPattern("hh:mm");
        formatter2 = DateTimeFormatter.ofPattern("dd/MM/yy");

        GridPane gridpane = new GridPane();

        GridPane.setConstraints(tm, 0, 0);
        GridPane.setConstraints(dt, 0, 1);

        gridpane.getColumnConstraints().add(new ColumnConstraints(firstLineHeight*1.5));
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        gridpane.getRowConstraints().add(row1);
        GridPane.setHalignment(tm, HPos.CENTER);
        GridPane.setValignment(tm, VPos.CENTER);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        gridpane.getRowConstraints().add(row2);
        GridPane.setHalignment(dt, HPos.CENTER);
        GridPane.setValignment(dt, VPos.CENTER);

        gridpane.setGridLinesVisible(true);

        gridpane.add(tm, 0, 0);
        gridpane.add(dt, 0, 1);

//        #3
        flags.setText(String.valueOf(completed)+"/"+String.valueOf(total));
//        CheckBox.setOnAction(event -> { //тут объект нажатия флага CheckBox
//            flags.setText(String.valueOf(completed)+"/"+String.valueOf(total));
//        });
        flags.setMaxHeight(firstLineHeight);
        flags.setAlignment(Pos.CENTER);


//        #4
        Button calendarSwitch = new Button("Switch\nCalendar");
        calendarSwitch.setOnAction(e -> {
            calendarSwitchFlag++;
            if (calendarSwitchFlag == 3) calendarSwitchFlag = 0;
            tmp_lbl.setText(String.valueOf(calendarSwitchFlag));
        });

        calendarSwitch.setMaxHeight(firstLineHeight);
        calendarSwitch.setTextAlignment(TextAlignment.CENTER);

//        #merger
        BorderPane stateLine = new BorderPane();

        BorderPane.setAlignment(settingsComboBox, Pos.CENTER);
        stateLine.setLeft(settingsComboBox);

        HBox time_flag = new HBox(30, gridpane, flags);
        stateLine.setCenter(time_flag);
        time_flag.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(calendarSwitch, Pos.CENTER);
        stateLine.setRight(calendarSwitch);

//        BorderPane.setMargin(gridEvents, new Insets(10)); // отступы

//        BorderPane.setAlignment(addLine, Pos.CENTER);
//        root.setBottom(addLine);

//        HBox stateLine = new HBox(30, settingsComboBox, gridpane, flags, calendarSwitch);
//
//        HBox.setHgrow(gridpane, Priority.ALWAYS);
//        HBox.setHgrow(flags, Priority.ALWAYS);
//        HBox.setHgrow(calendarSwitch, Priority.ALWAYS);
//
//        gridpane.setMaxWidth(Double.MAX_VALUE);
//        flags.setMaxWidth(Double.MAX_VALUE);
//        calendarSwitch.setMaxWidth(Double.MAX_VALUE);
//
////        stateLine.setStyle("-fx-backround-color: #46dca3");
////        stateLine.setBackground(new Background(new BackgroundFill(Color.DARKORANGE,null,null)));
        stateLine.setBackground(new Background(new BackgroundFill(Color.web("0x46DCA3FF", 1.0),null,null)));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////// 2 /////////////////////////////////////////////////////////////////////
        GridPane calendar = new GridPane();
        switch(calendarSwitchFlag) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }

        tmp_lbl.setText(String.valueOf(calendarSwitchFlag));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////// 3 /////////////////////////////////////////////////////////////////////
        GridPane gridEvents = new GridPane();

//        GridPane.setConstraints(tm, 0, 0);
//        GridPane.setConstraints(dt, 0, 1);

        gridEvents.getColumnConstraints().add(new ColumnConstraints(20));
        RowConstraints gridEventsRow1 = new RowConstraints();
//        gridEventsRow1.setPercentHeight(50);
        gridEvents.getRowConstraints().add(gridEventsRow1);
//        GridPane.setHalignment(tm, HPos.CENTER);
//        GridPane.setValignment(tm, VPos.CENTER);

        gridEvents.getColumnConstraints().add(new ColumnConstraints(firstLineHeight*1.5));
        RowConstraints gridEventsRow2 = new RowConstraints();
//        gridEventsRow2.setPercentHeight(50);
        gridEvents.getRowConstraints().add(gridEventsRow2);

        gridEvents.setGridLinesVisible(true);

//        for (int i=0; i<events.size(); i++) {
        for (Event e : events) {
//            gridEvents.add(new Label(String.valueOf(i)), 0, 0); //col, row
            gridEvents.add(new Label(String.valueOf(events.indexOf(e))), 0, 0); //col, row
            gridEvents.add(new Label(e.text), 1, 0); //col, row
        }


//        gridEvents.add(tm, 0, 0);
//        gridEvents.add(dt, 0, 1);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////// 4 /////////////////////////////////////////////////////////////////////
//        #1
        TextArea textArea = new TextArea();
//        textArea.setPrefColumnCount(20);
//        textArea.setPrefRowCount(2);

//        #2
        Button addNewEvent = new Button("+"); //⊕

        addNewEvent.setOnAction(e -> {
            events.add(new Event(textArea.getText()));
            gridEvents.add(new Label(String.valueOf(events.size())), 0, events.size()); //col, row
            gridEvents.add(new Label(textArea.getText()), 1, events.size()); //col, row
            textArea.setText("");
        });
//        #merger
        HBox addLine = new HBox(textArea, addNewEvent);

        HBox.setHgrow(textArea, Priority.ALWAYS);
        HBox.setHgrow(addNewEvent, Priority.ALWAYS);

        textArea.setPrefSize(225, 50);
//        textArea.setPrefHeight(50);
//        textArea.setPrefWidth(225);
        addNewEvent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        HBox.setMargin(addNewEvent, new Insets(8)); // отступы

        addLine.setBackground(new Background(new BackgroundFill(Color.web("0x46DCA3FF", 1.0),null,null)));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




//        #merger 1-4
//        VBox root = new VBox(10, stateLine, calendar, gridEvents, addLine);
        BorderPane root = new BorderPane();

        BorderPane.setAlignment(stateLine, Pos.CENTER);
        root.setTop(stateLine);

        root.setCenter(calendar);
        root.setCenter(gridEvents);
        BorderPane.setMargin(gridEvents, new Insets(10)); // отступы

        BorderPane.setAlignment(addLine, Pos.CENTER);
        root.setBottom(addLine);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////// scene ///////////////////////////////////////////////////////////////////

        Scene scene = new Scene(root);

        stage.setMinWidth(300);
        stage.setMaxWidth(300);
        stage.setWidth(300);

        stage.setMinHeight(150);
//        stage.setMaxHeight(300);
        stage.setHeight(300);

        stage.setScene(scene);
        stage.setTitle("Event Planner");
        stage.show();

    }

}