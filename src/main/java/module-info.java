module com.example.event_planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.fx.countries;
    requires com.almasb.fxgl.all;

    opens com.example.event_planner to javafx.fxml;
    exports com.example.event_planner;
}