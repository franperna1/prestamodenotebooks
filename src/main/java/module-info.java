module com.example.demo5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo5 to javafx.fxml;
    exports com.example.demo5;
    opens com.example.demo5.clases to javafx.fxml;
    exports com.example.demo5.clases;


}