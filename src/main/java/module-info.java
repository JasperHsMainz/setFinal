module org.midterm.setfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens org.midterm.setfinal to javafx.fxml;
    exports org.midterm.setfinal;
    exports org.midterm.setfinal.menu;
    opens org.midterm.setfinal.menu to javafx.fxml;
    exports org.midterm.setfinal.gamePackage;
    opens org.midterm.setfinal.gamePackage to javafx.fxml;
    exports org.midterm.setfinal.winning;
    opens org.midterm.setfinal.winning to javafx.fxml;
}