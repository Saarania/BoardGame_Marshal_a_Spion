/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Sara Praks
 * 
 * 4
 * 
 * Po vytvoreni hry Vojak jsem chtel vytvorit dalsi hru, kde bude herni pole mrizka,
 * ale jelikoz jsem sachy udelal, na rade byla hra marshal a spion. Vsechny obrazky jsou
 * stazene z internetu. Nejtezsi na tom vsem bylo otacet herni plochu a zaroven skryt vsechny
 * figurky protihrace.
 */
public class MarshalFinal extends Application {
    
    public static StackPane root = new StackPane();        
    public static Scene scene = new Scene(root, 300, 250);
    public static Group group = new Group();// po hlavnim menu se zmeni ze stackpanu na root 
    public static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        group.setTranslateX(350); //Aby se nezacinalo v levem hornim rohu
        group.setTranslateY(50);
        MarshalFinal.primaryStage = primaryStage;
        Classes.vytvorHlavniNabidku();
        
        primaryStage.setTitle("Marshal a spion!");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
