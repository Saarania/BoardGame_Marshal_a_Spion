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
 */
public class FieldView extends ImageView {
    private Field field;
    
    public FieldView(String url ,Field field) {
        super(url);
        this.field = field;
    }
}
