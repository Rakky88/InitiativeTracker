module initiativetracker {
    requires javafx.controls;
    requires javafx.fxml;
    exports view;
    opens controller to javafx.fxml;
}