/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import javafx.scene.image.ImageView;

/**
 *
 * @author Pocitac
 *
 * Tato trida dedi od tridy ImageView a pridavam do ni vlastnost Field field,
 * aby o sobe navzajem vedely. Pouzivam pri pohybu a utoceni figur.
 */
public class Moveable extends ImageView {

    private Field field;

    public Moveable(String s) {
        super(s);
    }

    public Moveable(String s, Field f) {
        super(s);
        this.field = f;
    }

    public Moveable() {
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
    
    
}
