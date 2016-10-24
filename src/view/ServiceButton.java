package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author           :   Sandro Guerotto
 * @Created          :   22.10.2016
 * @Project          :   cloud
 * @Package          :   view
 * @version          :   1.0
 * @LastUpdated      :
 * @Description      :   ServiceButton Klasse
 */

public class ServiceButton extends Button {
    private String type;
    private String path;

    private ImageView imageView;
    private Image image;


    public ServiceButton(String type){
        this.type = type;
        setPath();
        setBoundaries();
        setImage();
        setId("btn_" + path);
        getStyleClass().add("btn-home");
        imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        setGraphic(imageView);


    }

    private void setImage(){
        image = new Image(getClass().getResource("../icons/white/PNG/" + path + ".png").toString());
    }


    private void setBoundaries(){
        setHeight(45);
        setPrefHeight(45);
        setMaxHeight(45);

        setWidth(45);
        setPrefWidth(45);
        setMaxWidth(45);
    }

    private void setPath(){
        switch (type){
            case "plus":
                path = "plus";
                break;
            case "onedrive":
                path = "onedrive";
                break;
            case "Dropbox":
                path = "dropbox";
                break;
            case "googledrive":
                path = "google-drive";
                break;
        }

    }
}
