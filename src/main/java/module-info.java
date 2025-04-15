module com.example.demohyi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demohyi to javafx.fxml;
    exports com.example.demohyi;
}