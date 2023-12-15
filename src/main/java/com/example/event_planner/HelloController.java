package com.example.event_planner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn;
    private int x = 0;
    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
//        btn.setText("You've clicked!");

        x++;
        btn.setText(String.valueOf(x));

//        welcomeText.setText("Welcome to JavaFX Application!");
//        welcomeText.setText("Fahrenheit039");
    }

//    Label dateTime;
//    private int hour;
//    private int minute;
//    private int second;
}