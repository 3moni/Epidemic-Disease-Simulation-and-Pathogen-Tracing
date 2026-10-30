module com.example.cs115coursework2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs115coursework2 to javafx.fxml;
    exports com.example.cs115coursework2;
}