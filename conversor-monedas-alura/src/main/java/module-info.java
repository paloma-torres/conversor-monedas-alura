module com.example.conversormonedasalura {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.conversormonedasalura to javafx.fxml;
    exports com.example.conversormonedasalura;
}