module com.parseridea.powerpointparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.logging.log4j;

    opens com.parseridea.powerpointparser to javafx.fxml;
    exports com.parseridea.powerpointparser;
}