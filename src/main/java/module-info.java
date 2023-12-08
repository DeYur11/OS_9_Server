module com.example.os_9_server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.os_9_server to javafx.fxml;
    exports com.example.os_9_server;
}