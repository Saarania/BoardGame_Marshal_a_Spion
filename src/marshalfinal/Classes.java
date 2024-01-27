/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import static marshalfinal.MarshalFinal.group;

/**
 *
 * @author Pocitac
 */
public class Classes {

    private static StackPane root = MarshalFinal.root;
    private static ArrayList<ImageView> images = new ArrayList<>();
    public static ArrayList<Pocet> ukazateleFigur = new ArrayList<>();
    private static Group group = MarshalFinal.group;
    private static final int x = -300;
    private static final int y = 40;

    public static void vytvorHlavniNabidku() {
        ImageView background = new ImageView("images/background.jpg");
        root.getChildren().add(background);

        ImageView end = new ImageView("images/Konec.png");
        setScale(end, 2);
        end.setTranslateY(140);
        root.getChildren().add(end);
        end.setOnMouseClicked((MouseEvent event) -> {
            MarshalFinal.primaryStage.close();
        });

        ImageView start = new ImageView("images/Start.png");
        setScale(start, 2.5);
        start.setTranslateY(-120);
        root.getChildren().add(start);
        start.setOnMouseClicked((MouseEvent event) -> {
            zavriHlavniNabidku(background, start, end);
            MarshalFinal.scene.setRoot(MarshalFinal.group);
            Controller.getFields();//nic nedela jen zavola privatni konstruktor
            vytvorNabidkuPostav();
        });
    }

    public static void zavriHlavniNabidku(ImageView background, ImageView start, ImageView end) {
        background.setVisible(false);
        start.setVisible(false);
        end.setVisible(false);
    }

    public static void vytvorNabidkuPostav() {

        if (images.isEmpty()) { //musi byt stejne poradi jako ma 
            for (int i = 0; i < Settings.VSECHNY_TYPY_STRING.length; i++) {
                images.add(new ImageView("units/" + Settings.VSECHNY_TYPY_STRING[i] + ".png"));
            }
        }

        for (int i = 0; i < images.size(); i++) {
            setScale(images.get(i), 2);
            if (i % 2 != 0) {
                images.get(i).setTranslateX(images.get(i).getTranslateX() + x + (Settings.SIZE + 20));
            } else {
                images.get(i).setTranslateX(x + i % 2 * (Settings.SIZE + 20));
            }
            images.get(i).setTranslateY(y + i / 2 * (Settings.SIZE + 20));
            final int FinalI = i;//potrebuju do handlaru finalni promennou
            images.get(i).setOnMouseClicked((MouseEvent event) -> {
                Settings.lastTypeType = Settings.VSECHNY_TYPY[FinalI];
            });
            group.getChildren().add(images.get(i));
            naplnUkazateleFigur();
        }
    }

    public static void naplnUkazateleFigur() {
        for (int i = 0; i < ukazateleFigur.size(); i++) {
             Pocet p = ukazateleFigur.get(i);
             p.setVisible(false);
             p = null;
        }
        ukazateleFigur.clear();
        for (int i = 0; i < images.size(); i++) {
            Pocet pocet = new Pocet(Settings.POCET_TYPU[i], Settings.VSECHNY_TYPY[i], images.get(i)); //vytvori zbyvajici pocet jednotek
            ukazateleFigur.add(pocet);
        }
    }
    //nenaplni to do maxima ale podle parametru
    public static void naplnUkazateleFigur(int [] poleCisel) {
        for (int i = 0; i < ukazateleFigur.size(); i++) {
             Pocet p = ukazateleFigur.get(i);
             p.setVisible(false);
             p = null;
        }
        ukazateleFigur.clear();
        for (int i = 0; i < images.size(); i++) {
            Pocet pocet = new Pocet(poleCisel[i], Settings.VSECHNY_TYPY[i], images.get(i)); //vytvori zbyvajici pocet jednotek
            ukazateleFigur.add(pocet);
        }
    }
    
    private static void setScale(ImageView image, double d) {
        image.setScaleX(d);
        image.setScaleY(d);
    }
}
