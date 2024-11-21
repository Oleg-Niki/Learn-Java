module com.example.bankaccountapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bankaccountapp to javafx.fxml;
    exports com.example.bankaccountapp;
}
