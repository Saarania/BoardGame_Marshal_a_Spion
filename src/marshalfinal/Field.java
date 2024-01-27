/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import static marshalfinal.Controller.destroyAllMoveable;

/**
 *
 * @author Pocitac
 */
public class Field {

    private ImageView rectangle;
    private FieldView fieldView;
    private Typ typ;
    private Player player;
    private ImageView kryt;
    private Moveable moveable;
    int i;
    int j;

    public Field(ImageView rectangle, int i, int j) {
        this.rectangle = rectangle;
        rectangle.setTranslateX(i * Settings.SIZE);
        rectangle.setTranslateY(j * Settings.SIZE);
        if (j > 6) { //nastavovani ctvercu pri rozmistovani
            rectangle.setOnMousePressed((MouseEvent event) -> {
                if (!Settings.figuryRozestaveny) {
                    if (kontrola()) {//zkontroluje jestli tento typ figur nedosel
                        if (this.getTyp() != null) {//vraci figury pri chybe
                            for (int k = 0; k < Classes.ukazateleFigur.size(); k++) {
                                Pocet pomoc = Classes.ukazateleFigur.get(k);
                                if (pomoc.getTyp() == this.getTyp()) {
                                    pomoc.pricti();
                                }
                            }
                            getFieldView().setVisible(false);
                        }
                        //polozi figuru
                        if (Settings.konecRozmistovani < 2) {
                            setFigure(Settings.lastTypeType, Settings.activePlayer);
                            kontrolaKonecRozmistovani();
                        }
                    }
                }
            });
        }
        MarshalFinal.group.getChildren().add(rectangle);
        this.i = i;
        this.j = j;
    }

    private void kontrolaKonecRozmistovani() {
        for (int k = 0; k < Classes.ukazateleFigur.size(); k++) {
            Pocet pomoc = Classes.ukazateleFigur.get(k);
            if (pomoc.getPocet() != 0) {
                break;
            }
            if (k == Classes.ukazateleFigur.size() - 1 && ++Settings.hotovoTlacitkoInt < 3) {
                ImageView hotovo = new ImageView("images/hotovo.png");
                hotovo.setTranslateX(-240);
                hotovo.setTranslateY(600);
                MarshalFinal.group.getChildren().add(hotovo);
                hotovo.setOnMousePressed((MouseEvent event) -> {
                    hotovo.setVisible(false);
                    Controller.pretocit();
                    if (Settings.konecRozmistovani == 0) {
                        Classes.naplnUkazateleFigur();
                    }else {
                        Settings.figuryRozestaveny = true;
                    }
                    Settings.konecRozmistovani++;
                });
            }
        }
    }

    //pouziva se pri rozmistovani a kontroluje, jestli nedosla figura
    private boolean kontrola() {
        for (int k = 0; k < Classes.ukazateleFigur.size(); k++) {
            Pocet pomocny = Classes.ukazateleFigur.get(k);
            if (pomocny.getTyp() == Settings.lastTypeType) {
                if (pomocny.getPocet() < 1) {
                    return false;
                } else {
                    pomocny.odecti();
                }
            }
        }

        return true;
    }

    public Field(ImageView rectangle, int i, int j, Typ typ) {
        this.rectangle = rectangle;
        rectangle.setTranslateX(i * Settings.SIZE);
        rectangle.setTranslateY(j * Settings.SIZE);
        MarshalFinal.group.getChildren().add(rectangle);
        this.i = i;
        this.j = j;
        this.typ = typ;
    }

    public Player getPlayer() {
        return player;
    }

    public FieldView getFieldView() {
        return fieldView;
    }

    private void setFieldView(FieldView fieldView) {
        if (fieldView != null) {
            fieldView.setTranslateX(i * Settings.SIZE);
            fieldView.setTranslateY(j * Settings.SIZE);
            MarshalFinal.group.getChildren().add(fieldView);
        } else {
            this.fieldView.setVisible(false);
        }
        this.fieldView = fieldView;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setFigure(Typ typ) {//pokud se rozsazuje protoze player se nastavi na activePlayer
        if (getKryt() != null) {
            getKryt().setVisible(false);
            setKryt(null);
        }
        if (fieldView != null) {
            fieldView.setVisible(false);
        }
        this.typ = typ;
        if (typ != null) {
            this.player = Settings.activePlayer;
            setFieldView(new FieldView("units/" + typ.toString() + ".png", this));
            this.player = Settings.activePlayer;
        } else {
            if (fieldView != null) {
                fieldView.setVisible(false);
            }
            this.fieldView = null;
            this.player = null;
        }
    }

    public void setFigure(Typ typ, Player p) {//set figure s playerem
        this.typ = typ;
        this.player = p;
        if (typ != null) {
            setFieldView(new FieldView("units/" + typ.toString() + ".png", this));
        } else {
            setKryt(null);
            if (fieldView != null) {
                fieldView.setVisible(false);
            }
            this.fieldView = null;
        }
    }

    @Override
    public String toString() {
        return "i = " + i + ", j = " + j;
    }

    public ImageView getKryt() {
        return kryt;
    }

    public void setKryt(ImageView kryt) {
        if (this.kryt != null) {
            this.kryt.setVisible(false);
        }
        if (kryt != null) {
            kryt.setTranslateX(i * Settings.SIZE);
            kryt.setTranslateY(j * Settings.SIZE);
        }
        this.kryt = kryt;

    }

    public Moveable getMoveable() {
        return moveable;
    }

    public void setMoveable(Moveable moveable) {
        this.moveable = moveable;
        if (moveable != null) {
            moveable.setTranslateX(i * Settings.SIZE);
            moveable.setTranslateY(j * Settings.SIZE);
        }
    }

}
