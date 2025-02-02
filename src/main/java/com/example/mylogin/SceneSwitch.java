package com.example.mylogin;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class SceneSwitch {

//    public SceneSwitch(AnchorPane currentPage, String fxml ) throws IOException {
//        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxml)));
//        currentPage.getChildren().removeAll(nextAnchorPane);
//        currentPage.getChildren().setAll(nextAnchorPane);
//    }

    private final FXMLLoader loader;

    public SceneSwitch(BorderPane currentPage, String fxml) throws IOException {
        loader = new FXMLLoader(DataInfoApp.class.getResource(fxml));
        BorderPane nextBorderPane = loader.load();
        currentPage.getChildren().removeAll(nextBorderPane);
        currentPage.getChildren().setAll(nextBorderPane);
    }

    public <T> T getController() {
        return loader.getController();
    }

}

