//module com.example.flat2d {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires com.almasb.fxgl.all;
//
////    opens com.example.flat2d to javafx.fxml;
////    exports com.example.flat2d;
//}
open module FLAT2D.main{
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.media;

    // Additional Exports
    exports final_project_socket.fxml_controller to javafx.fxml;
    exports final_project_socket.socket to javafx.fxml;

}