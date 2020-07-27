package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.model.Cliente;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AlertBoxProfilo {

    public static void display(String title, Cliente c) throws IOException {
        Stage window = new Stage();
        ImageView imageProfilo = new ImageView();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(c.getNome() + " " +c.getCognome());
        Label label2 = new Label();
        label2.setText(c.getTelefono() + " " +c.getEta());
        Label label3 = new Label();
        label3.setText(c.getCap() + " " +c.getCitta() + " " + c.getIndirizzo());
        Image image=CommonBusiness.getInstance().getFotoCliente(c.getId());
        imageProfilo.setImage(image);
        Button closeButton = new Button("Chiudi");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(40);
        layout.getChildren().addAll(label, label2, label3, imageProfilo, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
