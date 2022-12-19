module com.example.oopkursach {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires spring.jdbc;
    requires spring.beans;
    requires org.postgresql.jdbc;
    requires json.simple;
    requires jackson.mapper.asl;
    requires com.fasterxml.jackson.databind;
    opens com.example.oopkursach to javafx.fxml;
    exports com.example.oopkursach;
}