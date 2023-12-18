package com.example.event_planner;

import eu.hansolo.toolboxfx.geom.Dimension;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorInput;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.awt.Toolkit;

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
    private static final int TitleSize = 34;
    private static final int firstLineHeight = 50;
    private static final double lastLineWidth = 35;
    private static final int insetsConst = 10;
    private static final int gridOneRowConst = 17;

    GridPane gridEvents;
    VBox calendarAndEvents;
    TextArea textArea;
    Button addNewEvent;
    HBox addLine;
    Stage stage;
    double WIDTH = 400, HEIGHT = 600;
    String title = "Event Planner";

    private ObservableList<dateTableEvents> selected;
    final private KeyCombination SHIFT_ENTER     = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
    final private KeyCombination ENTER           = new KeyCodeCombination(KeyCode.ENTER);
    private TableView<dateTableEvents> table = new TableView<>();
    private ObservableList<dateTableEvents> data = FXCollections.observableArrayList();
    private void fillTableObservableListWithSampleData() {
        data.addAll(
                new dateTableEvents(0,"Tom"),
                new dateTableEvents(1,"Bob"),
                new dateTableEvents(2,"Sam"),
                new dateTableEvents(3,"Alice"),
                new dateTableEvents(4, "app1"),
                new dateTableEvents(5, "\n \n1\n \n\n2"));
    }
    private void setTableAppearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//        table.setPrefWidth(WIDTH);
        table.setPrefWidth(WIDTH);
//        table.setMinWidth(WIDTH);
//        table.setPrefHeight(400);
//        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();

    }
    private void setStage(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX(primaryScreenBounds.getMinX());
//        stage.setY(primaryScreenBounds.getMinY());
//        stage.setWidth();

//        stage.setMinWidth(WIDTH/1.5);
//        stage.setMinWidth(400);

        stage.setMaxWidth(primaryScreenBounds.getWidth());
        stage.setMaxHeight(primaryScreenBounds.getHeight());
    }
    public String checkString(String s){
        return s; //заглушка для хаба
//        Pattern p = Pattern.compile("\\S\\w*\n");

//        String[] tmp_split = s.split("\\S\\w*\\n+");
//        String[] tmp_split = s.split("\\S\\\\n+");
//        String[] tmp_split = s.split("\\n+");
        String[] tmp_split = s.split(System.lineSeparator());

        // TODO: 18.12.2023 проверяем последнюю строку на "\n", если есть - удаляем '\n'

        if (tmp_split.length == 0) return s;
        if (tmp_split[tmp_split.length-1].length() == 0) return tmp_split[tmp_split.length-1];
        else return tmp_split[tmp_split.length-1].substring(0, tmp_split[tmp_split.length-1].length()-1);

//        Pattern p = Pattern.compile("\\S\\w*");
//        Matcher m = p.matcher(s);
//        String tmp = "";
//        while(m.find()){
//            tmp += m.group(); // .split("\n")[0];
//            System.out.println("Prepared: " + matcher.group());
//        }
//        System.out.println("before trim: " + tmp + " \\ l: " + tmp.length());

//        return  tmp.substring(0, tmp.length()-1); //substring( [begin ; end) ) / end - not included ~ [begin ; end-1]
    }
    public void addNewEvent(){
        if (textArea.getText().length() == 0) return;

        data.add(new dateTableEvents(data.size(), checkString(textArea.getText())));
        table.setItems(data);

        autoResizeColumns1(table);
        autoResizeTableHeight(table);

//            System.out.println("table.getWidth(): "+table.getWidth());
        textArea.setText("");
        textArea.clear();
    }
    public void autoResizeTableHeight(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
//        table.getColumns().forEach( (column) ->
//                {
               TableColumn column = table.getColumns().get(1);
                    Text t = new Text( column.getText() );
                    double sum = (t.getLayoutBounds().getHeight() *1.8235); //+ 9.1d;
//        t.getLayoutBounds().
//                    double sum = 21.75;
                    double newEmptyLineHeight = 25; //25
//                    double sum = t.getLayoutBounds().getHeight();

//                    System.out.println("handle: "+t.getLayoutBounds().getHeight());

                    for ( int i = 0; i < table.getItems().size(); i++ )
                    {
                        //cell must not be empty
                        if ( column.getCellData( i ) != null )
                        {
                            t = new Text( column.getCellData( i ).toString() );
                            double calcHeight = t.getLayoutBounds().getHeight();
                            System.out.print(i+" "+t.getLayoutBounds().getHeight()+" \\ ");

//                            System.out.print(i+" "+t.getBoundsInParent().getHeight()+" \\ ");

//                            System.out.print(i+" "+calcHeight+" \\ ");

                            //remember new max-width
//                            if ( calcwidth > max )
//                            {
//                            double multiplier = 17;
//                            int count = (int)Math.floor(calcHeight / multiplier);
//                            if (count == 0) count = 1;

//                            System.out.println("calcHeight: "+calcHeight +" \\ multiplier: "+multiplier);
//                            sum += (count * multiplier);
                            long count = Arrays.stream((t.getText() + " ").split("\n")).count();
//                            System.out.println("t.getText(): "+t.getText()+" \\ count: "+count);

                            if (count == 1 ) sum += 14;
                            else sum += 6;
//                                sum += (calcHeight+14);
                                sum += (calcHeight);
//                                sum += (calcHeight*1.8235);
//                            }
                        }
                    }
                    //set the new max-widht with some extra space
//        System.out.println("\nsum: "+(sum+10.0d));
        System.out.print("\nstage: "+(TitleSize + sum+newEmptyLineHeight + lastLineWidth + 4*insetsConst));
                    stage.setHeight(TitleSize + sum+newEmptyLineHeight + lastLineWidth + 4*insetsConst);
        System.out.println(" \\ table after stage: "+table.getHeight());
//                    table.setPrefHeight(sum + 10.0d);
//                }
//        );

//        table.getColumns().stream().forEach( (column) ->
//        {
//            //Minimal width = columnheader
//            Text t = new Text( column.getText() );
//            double max = t.getLayoutBounds().getWidth();
//            for ( int i = 0; i < table.getItems().size(); i++ )
//            {
//                //cell must not be empty
//                if ( column.getCellData( i ) != null )
//                {
//                    t = new Text( column.getCellData( i ).toString() );
//                    double calcwidth = t.getLayoutBounds().getWidth();
//                    //remember new max-width
//                    if ( calcwidth > max )
//                    {
//                        max = calcwidth;
//                    }
//                }
//            }
//            //set the new max-widht with some extra space
//            column.setPrefWidth( max + 10.0d );
//        } );
    }
    public static void autoResizeColumns1( TableView<?> table ) {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);

//        table.getColumns().get(0).setPrefWidth(table.getWidth()*5/100);
//        table.getColumns().get(1).setPrefWidth(table.getWidth()*50/100);
        double minus = 15; //2
        for (int i=0; i<table.getColumns().size(); i++) {
            if (i==1) continue;
            minus += table.getColumns().get(i).getWidth();
        }
//        double tmp_insets = stage.getWidth();
//        System.out.println(oldVal+" "+newVal+"\n"+table.getWidth()+" d:"+tmp_insets);

        table.getColumns().get(1).setPrefWidth(table.getWidth() - minus);
//        table.getColumns().get(1).setPrefWidth(stageWidth - 36 - minus);
//        table.getColumns().get(2).setPrefWidth(table.getWidth()*15/100);
//        table.getColumns().get(3).setPrefWidth(table.getWidth()*15/100);
//        table.getColumns().get(4).setPrefWidth(table.getWidth()*15/100);

    }
    public static void autoResizeColumns1( TableView<?> table , double stageWidth) {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);

//        table.getColumns().get(0).setPrefWidth(table.getWidth()*5/100);
//        table.getColumns().get(1).setPrefWidth(table.getWidth()*50/100);
        double minus = 15; //2
        for (int i=0; i<table.getColumns().size(); i++) {
            if (i==1) continue;
            minus += table.getColumns().get(i).getWidth();
        }
//        double tmp_insets = stage.getWidth();
//        System.out.println(oldVal+" "+newVal+"\n"+table.getWidth()+" d:"+tmp_insets);

//        table.getColumns().get(1).setPrefWidth(table.getWidth() - minus);
        table.getColumns().get(1).setPrefWidth(stageWidth - 36 - minus);
//        table.getColumns().get(2).setPrefWidth(table.getWidth()*15/100);
//        table.getColumns().get(3).setPrefWidth(table.getWidth()*15/100);
//        table.getColumns().get(4).setPrefWidth(table.getWidth()*15/100);

    }
    private void tableUpdate(){
        table.setItems(data);

        //index\id
        TableColumn<dateTableEvents, Integer> colId = new TableColumn<>("№");
        colId.setCellValueFactory(new PropertyValueFactory<>("index"));

        // text
        TableColumn<dateTableEvents, String> colText = new TableColumn<>("Text");
        colText.setCellValueFactory(new PropertyValueFactory<>("text"));

        // status
        TableColumn<dateTableEvents, Boolean> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(colId, colText, colStatus);

//        System.out.println(table.getPrefWidth());

//        table.getColumns().get(0).setPrefWidth(25);
//        table.getColumns().get(1).setPrefWidth(250);
//        table.getColumns().get(2).setPrefWidth(75);

        addButtonToTable("Edit", "edit");
        addButtonToTable("Delete", "delete");

//        autoResizeColumns(table);

        table.getColumns().get(0).setPrefWidth(30);

        table.getColumns().get(2).setPrefWidth(45);
        table.getColumns().get(3).setPrefWidth(45);
        table.getColumns().get(4).setPrefWidth(45);

        autoResizeColumns1(table);

//        for (int i=0; i<table.getColumns().size(); i++){
//            TableColumn column = table.getColumns().get(i);
//            column.setPrefWidth(10+i*20);
//        }

//        table.getColumns().get(3).setPrefWidth(75);
//        table.getColumns().get(4).setPrefWidth(75);
    }
    private void addButtonToTable(String name, String action) {
        TableColumn<dateTableEvents, Void> colBtn = new TableColumn(name);

        Callback<TableColumn<dateTableEvents, Void>, TableCell<dateTableEvents, Void>> cellFactory = new Callback<TableColumn<dateTableEvents, Void>, TableCell<dateTableEvents, Void>>() {
            @Override
            public TableCell<dateTableEvents, Void> call(final TableColumn<dateTableEvents, Void> param) {
                final TableCell<dateTableEvents, Void> cell = new TableCell<dateTableEvents, Void>() {

                    private final Button btn = new Button();
                    {
                        switch(action){
                            case "edit": btn.setText(action); break;
                            case "delete": btn.setText("X"); break;
                        }

                        btn.setOnAction((ActionEvent event) -> {

                            dateTableEvents data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);

                            switch(action){
                                case "edit":
                                    Stage stage = new Stage();
                                    stage.setWidth(400);

                                    TextArea textArea = new TextArea(data.getText());
                                    Button addNewEvent = new Button("Confirm"); //⊕
                                    addNewEvent.setOnAction(e -> {
                                        if (textArea.getText().length() == 0) return;
                                        getTableView().getItems().set(data.getIndex(), new dateTableEvents(data.getIndex(), textArea.getText()));
                                    });

                                    HBox addLine = new HBox(textArea, addNewEvent);
                                    HBox.setHgrow(textArea, Priority.ALWAYS);
                                    HBox.setHgrow(addNewEvent, Priority.ALWAYS);

                                    textArea.setPrefSize(stage.getWidth()-lastLineWidth*5/2, lastLineWidth);
                                    addNewEvent.setPrefSize(lastLineWidth*5/2, lastLineWidth);
                                    System.out.println("all:"+stage.getWidth()+" \\ textArea: "+textArea.getWidth()+" \\ btn: "+addNewEvent.getWidth());

                                    HBox.setMargin(addNewEvent, new Insets(insetsConst)); // отступы. кнопка в своем квадрате

                                    addLine.setBackground(new Background(new BackgroundFill(Color.web("0x46DCA3FF", 1.0),null,null)));


                                    BorderPane root = new BorderPane();
                                    BorderPane.setAlignment(addLine, Pos.CENTER);
                                    root.setTop(addLine);

                                    Scene scene = new Scene(root);
                                    stage.setTitle("Edit");
                                    stage.setScene(scene);
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.showAndWait();
                                    break;

                                case "delete":
                                    table.getItems().remove(data);
                                    int i = data.getIndex();
                                    for (dateTableEvents d : getTableView().getItems()) {
                                        if (d.getIndex() < i) continue;
                                        d.setIndex(d.getIndex()-1);
                                    }
//                                    updateData();
                                    break;
                            }
//                            autoResizeColumns(table);
                            autoResizeColumns1(table);
                            autoResizeTableHeight(table);
                        });

                        btn.prefWidthProperty().bind(colBtn.widthProperty().multiply(1));
//                        colBtn.setPrefWidth(10);//.prefWidthProperty().setValue(10);
//                        colBtn.prefWidthProperty().;//.prefWidthProperty().setValue(10);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
    }

    private void takeAllItems(){
        MultipleSelectionModel<dateTableEvents> someSelectionModel = table.getSelectionModel();
        someSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        someSelectionModel.selectedItemProperty().addListener(new ChangeListener<dateTableEvents>() {
//            private ObservableList<dateTableEvents> selected = null;
            @Override
            public void changed(ObservableValue<? extends dateTableEvents> changed, dateTableEvents oldValue, dateTableEvents newValue) {
                Label selectedLbl = new Label(); // test
                String selectedItems = "";
                ObservableList<dateTableEvents> selected = someSelectionModel.getSelectedItems();
//                this.selected = selected;
                for (dateTableEvents item : selected){
                    selectedItems += item + " ";
                }
                selectedLbl.setText("Selected: " + selectedItems); // test
                System.out.println(selectedLbl.getText());
            }
        });
    }
    private void dragAndDrop() {
//        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            dateTableEvents selected = table.getSelectionModel().getSelectedItem();
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if (mouseEvent.getClickCount() == 2) { //double click
//                    if (selected != null)
//                        System.out.println(selected.getText());
//                }
//            }
//        });
        table.setOnDragDetected(new EventHandler<MouseEvent>() { //drag
            @Override
            public void handle(MouseEvent event) {
                // drag was detected, start drag-and-drop gesture
                dateTableEvents selected = table.getSelectionModel().getSelectedItem();
                if(selected !=null){
                    Dragboard db = table.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(selected.getText());
                    db.setContent(content);
                    event.consume();
                }
            }
        });

        table.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // data is dragged over the target
                Dragboard db = event.getDragboard();
                if (event.getDragboard().hasString()){
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        table.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (event.getDragboard().hasString()) {

                    String text = db.getString();
//                    new dateTableEvents(0,"Tom"),
//                    data.set(data.size(), text);
                    data.add(new dateTableEvents(data.size()-1,text));
                    table.setItems(data);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private ArrayList<Event> events = new ArrayList<Event>();


    /////////////////////////////////////////////////////////////// start /////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {

        /////////////////////////////////////////////////////////////// 3 /////////////////////////////////////////////////////////////////////
        setTableAppearance();
        fillTableObservableListWithSampleData();
        tableUpdate();
//        takeAllItems();
//        dragAndDrop();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        /////////////////////////////////////////////////////////////// 4 /////////////////////////////////////////////////////////////////////
//        #1
        textArea = new TextArea();
//        #2
        addNewEvent = new Button("+"); //⊕
        addNewEvent.setOnAction(e -> { addNewEvent(); });

        textArea.setOnKeyPressed(event -> {
//            System.out.println(textArea.getText().trim());

//            String[] tmp_split = textArea.getText().split("\n");
//            String tmp = "";
//            for (String line : tmp_split) {
//                if (line.equals("\n")) continue;
//                line.replaceAll("  ", " ");
//                tmp += line;
//            }
//            System.out.println(tmp);
                if (event.getCode() == KeyCode.ENTER) {
//                    event.consume(); // otherwise a new line will be added to the textArea after the sendFunction() call
//                if (ENTER.match(event)){
//                    addNewEvent();
//                }
//                else if (SHIFT_ENTER.match(event)){
//                    textArea.deleteText(textArea.getSelection());
//                    textArea.insertText(textArea.getCaretPosition(), "\n");
//                    textArea.setText(textArea.getText()+"\n");
//                    textArea.insertText(textArea.getCaretPosition(), "\n");
//                }
                    if (event.isShiftDown()) {
                        textArea.appendText(System.getProperty("line.separator"));
//
//                        textArea.deleteText(textArea.getSelection());
//                        textArea.insertText(textArea.getCaretPosition(), "\n");
                    } else {
                        if (!textArea.getText().trim().isEmpty()) {
                            addNewEvent();
                        }
                    }
//                event.consume(); // otherwise a new line will be added to the textArea after the sendFunction() call
            }

        });

//        #merger
        addLine = new HBox(textArea, addNewEvent);

        HBox.setHgrow(textArea, Priority.ALWAYS);
        HBox.setHgrow(addNewEvent, Priority.ALWAYS);

        textArea.setPrefSize(stage.getWidth()-lastLineWidth, lastLineWidth);
        addNewEvent.setPrefSize(lastLineWidth, lastLineWidth);

        HBox.setMargin(addNewEvent, new Insets(insetsConst)); // отступы. кнопка в своем квадрате

        addLine.setBackground(new Background(new BackgroundFill(Color.web("0x46DCA3FF", 1.0),null,null)));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////// merge? ///////////////////////////////////////////////////////

        BorderPane root = new BorderPane();
//        root = new BorderPane();

//        BorderPane.setAlignment(stateLine, Pos.TOP_CENTER);
//        BorderPane.setAlignment(stateLine, Pos.CENTER);
//        root.setTop(stateLine);

        root.setCenter(table);
        BorderPane.setMargin(table, new Insets(insetsConst)); // отступы
//        root.getChildren().add(new Group(table));
//        root.setCenter(new Group(table));

//        root.setCenter(tmp_hbox);
//        root.setCenter(gridEvents);
//        BorderPane.setMargin(calendarAndEvents, new Insets(insetsConst)); // отступы

        BorderPane.setAlignment(addLine, Pos.BOTTOM_CENTER);
//        BorderPane.setAlignment(addLine, Pos.CENTER);
        root.setBottom(addLine);

//        VBox root = new VBox(stateLine, calendarAndEvents, addLine);
//        VBox root = new VBox(center, addLine);

//        StackPane pre_root = new StackPane(calendarAndEvents, addLine, stateLine);

//        AnchorPane root = new AnchorPane(stateLine, calendarAndEvents, addLine);
//        AnchorPane.setTopAnchor(stateLine, 0.0);
//        AnchorPane.setBottomAnchor(addLine, 0.0);

//        StackPane.setAlignment(stateLine, Pos.TOP_CENTER);
//
//        StackPane.setAlignment(calendarAndEvents, Pos.CENTER);
//        StackPane.setMargin(calendarAndEvents, new Insets(insetsConst)); // отступы
//
//        StackPane.setAlignment(addLine, Pos.BOTTOM_CENTER);
//        root.setBottom(addLine);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////// scene ///////////////////////////////////////////////////////////////////
        createWindow(root, stage);
        autoResizeColumns1(table);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(stage.getHeight() +" stage \\ table "+table.getHeight());
            autoResizeColumns1(table, (double)newVal);
        });
    }

//    private void printEvents(){
    private void createWindow(Pane root, Stage stage) throws IOException{
        this.stage = stage;
        Scene scene = new Scene(root, this.WIDTH, this.HEIGHT);
        stage.setTitle(this.title);
        stage.setScene(scene);

        setStage();

        stage.show();
    }
    private void updateScreen(Pane root){
        Scene scene = new Scene(root, this.WIDTH, this.HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Event Planner");
        stage.show();
    }

    private void updateGridEvents(){
        gridEvents = new GridPane();
        gridEvents.getColumnConstraints().add(new ColumnConstraints(20));
        gridEvents.getColumnConstraints().add(new ColumnConstraints(firstLineHeight*1.5));
        gridEvents.getRowConstraints().add(new RowConstraints());
        gridEvents.setGridLinesVisible(true);

        for (Event e : events) {
            gridEvents.add(new Label(String.valueOf(events.indexOf(e))), 0, 0); //col, row
            gridEvents.add(new Label(e.text), 1, 0); //col, row
        }
    }
}