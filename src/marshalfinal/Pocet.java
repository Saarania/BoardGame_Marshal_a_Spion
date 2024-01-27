/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Pocitac
 */
public class Pocet extends Text {
    //pocet na textu
    private int pocet;
    //typ jednotky
    private Typ typ;
    //obrazek u ktereho bude
    private ImageView imageView;
    //text ktery bude napsany
    private final String text;

    public Pocet(int pocet, Typ typ, ImageView imageView) {
        this.pocet = pocet;
        this.typ = typ;
        this.imageView = imageView;
        this.text = "x "+pocet;
        setText(text);
        setTranslateY(imageView.getTranslateY());
        setTranslateX(imageView.getTranslateX() + 60);
        setScaleX(2);
        setScaleY(2);
        MarshalFinal.group.getChildren().add(this);
    }

    public void pricti() {
        pocet++;
        setText("x "+ pocet);
    }
    
    public void odecti() {
        pocet--;
        setText("x "+ pocet);
    }
    
    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
        setText("x "+ pocet);
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    
    
}
