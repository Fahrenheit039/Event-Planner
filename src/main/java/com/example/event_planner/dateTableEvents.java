package com.example.event_planner;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

//import javafx.event.*;

//public class tableTest{
//    private SimpleIntegerProperty index;
//    private SimpleStringProperty text;
//    private SimpleBooleanProperty status;
//    private Button edit;
//    private Button delete;
//
//    tableTest(int index, String text){
//        this.index = new SimpleIntegerProperty(index);
//        this.text = new SimpleStringProperty(text);
//
//        this.status = new SimpleBooleanProperty(false);
//
//        this.edit = new Button("edit");
//        this.delete = new Button("delete");
//    }
//
//    public int getIndex(){ return index.get();}
//    public void setIndex(int value){ index.set(value);}
//
//    public String getText(){ return text.get();}
//    public void setText(String value){ text.set(value);}
//
//    public boolean getStatus(){ return status.get();}
//    public void setStatus(boolean value){ status.set(value);}
//
//}

public class dateTableEvents{
    //    private SimpleIntegerProperty index;
//    private SimpleStringProperty text;
//    private SimpleBooleanProperty status;
//    private Button edit;
//    private Button delete;
    private int index;
    private String text;
//    private int status;
    private boolean status;

    dateTableEvents(int index, String text){
//        this.index = new SimpleIntegerProperty(index);
//        this.text = new SimpleStringProperty(text);
//        this.status = new SimpleBooleanProperty(false);

        this.index = index;
        this.text = text;
//        this.status = 0;
        this.status = false;

//        this.edit = new Button("edit");
//        this.delete = new Button("delete");
    }
    dateTableEvents(String text){
        this.text = text;
//        this.status = 0;
        this.status = false;
    }

//    public int getIndex(){ return index.get();}
//    public void setIndex(int value){ index.set(value);}
//    public String getText(){ return text.get();}
//    public void setText(String value){ text.set(value);}
//    public boolean getStatus(){ return status.get();}
//    public void setStatus(boolean value){ status.set(value);}

    public int getIndex(){ return index;}
    public void setIndex(int value){ this.index = value;}
    public String getText(){ return text;}
    public void setText(String value){ this.text = value;}
    public boolean getStatus(){ return status;}
    public void setStatus(boolean value){ this.status = value;}

}

//class TableActions{
//
//    private TableView<dateTableEvents> table;
//
//    public TableActions(TableView<dateTableEvents> table){
//        this.table = table;
//    }
//
//    table.getFocusModel().focus(null);
//    TableView.setOnMouseClicked
//
//    TableView table1 = getTableView().getItems().get(getIndex());
//
//    table.(new EventHandler<MouseEvent>() { //click
//
//        public void handle(MouseEvent event) {
//            if(event.getClickCount()==2){ // double click
//
//                String selected =   tableView.getSelectionModel().getSelectedItem();
//                if(selected !=null){
//                    System.out.println("select : "+selected);
////        ...
//                }
//            }
//        }
//    });
//
//        tableView.setOnDragDetected(new EventHandler<MouseEvent>() { //drag
//
//        public void handle(MouseEvent event) {
//            // drag was detected, start drag-and-drop gesture
//            String selected = tableView.getSelectionModel().getSelectedItem();
//            if(selected !=null){
//
//                Dragboard db = tableView.startDragAndDrop(TransferMode.ANY);
//                ClipboardContent content = new ClipboardContent();
//                content.putString(selected);
//                db.setContent(content);
//                event.consume();
//            }
//        }
//    });
//
//        tableView.setOnDragOver(new EventHandler<DragEvent>() {
//
//        public void handle(DragEvent event) {
//            // data is dragged over the target
//            Dragboard db = event.getDragboard();
//            if (event.getDragboard().hasString()){
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        }
//    });
//
//        tableView.setOnDragDropped(new EventHandler<DragEvent>() {
//
//        public void handle(DragEvent event) {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (event.getDragboard().hasString()) {
//
//                String text = db.getString();
//                tableContent.add(text);
//                tableView.setItems(tableContent);
//                success = true;
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        }
//    });
//
//
//}