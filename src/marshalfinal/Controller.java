/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Pocitac
 */
public class Controller {

    private static Field fields[][] = new Field[Settings.WIDTH][Settings.HEIGHT];
    private static ArrayList<Typ[]> slabiny = new ArrayList<>();
    private static Group root = MarshalFinal.group;

    static {
        //vytvoreni ctvercu
        for (int i = 0; i < Settings.WIDTH; i++) {
            for (int j = 0; j < Settings.HEIGHT; j++) {
                if ((i == 3 && j > 3 && j < 7) || (i == 6 && j > 3 && j < 7)) {
                    fields[i][j] = new Field(new ImageView("images/barricade.png"), i, j, Typ.BARRICADE);
                } else {
                    fields[i][j] = new Field(new ImageView("images/fieldImage.png"), i, j);
                }
            }
        }
        //naplneni slabin rucne
        //bomba
        Typ[] slabinyBomby = {Typ.MINER};
        slabiny.add(slabinyBomby);
        //cadet
        Typ[] slabinyCadeta = {Typ.CAPRAL, Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.BOMB};
        slabiny.add(slabinyCadeta);
        //capral
        Typ[] slabinyCaprala = {Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.BOMB};
        slabiny.add(slabinyCaprala);
        //captain
        Typ[] slabinyCaptaina = {Typ.GENERAL, Typ.MARSHAL, Typ.BOMB};
        slabiny.add(slabinyCaptaina);
        //flag
        slabiny.add(Settings.VSECHNY_TYPY);
        //general
        Typ[] slabinyGenerala = {Typ.MARSHAL, Typ.BOMB};
        slabiny.add(slabinyGenerala);
        //marshal
        Typ[] slabinyMarshala = {Typ.SPY, Typ.BOMB};
        slabiny.add(slabinyMarshala);
        //miner
        Typ[] slabinyMinera = {Typ.CADET, Typ.CAPRAL, Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.SCOUT, Typ.RIFLEMAN};
        slabiny.add(slabinyMinera);
        //rifleman
        Typ[] slabinyRiflemana = {Typ.CADET, Typ.CAPRAL, Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.SCOUT, Typ.BOMB};
        slabiny.add(slabinyRiflemana);
        //scout
        Typ[] slabinyScouta = {Typ.CADET, Typ.CAPRAL, Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.BOMB};
        slabiny.add(slabinyScouta);
        //spy
        Typ[] slabinySpye = {Typ.CADET, Typ.CAPRAL, Typ.CAPTAIN, Typ.GENERAL, Typ.MARSHAL, Typ.SCOUT, Typ.RIFLEMAN, Typ.MINER, Typ.BOMB};
        slabiny.add(slabinySpye);
    }

    private static int pocetFigurVeHre() {
        int pocet = 0;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].getFieldView() != null) {
                    pocet++;
                }
            }
        }
        return pocet;
    }

    public static void pretocit() { //pretoci plochu zrcadlove
        //uplne nejprve prohodime aktivniho hrace
        Settings.activePlayer = Settings.opositePlayer(Settings.activePlayer);
        //naplni ukazatele, kolik jednotek jeste mas
        if (Settings.activePlayer == Player.BLUE) {
            Classes.naplnUkazateleFigur(Settings.blueArmy);
        } else {
            Classes.naplnUkazateleFigur(Settings.redArmy);
        }
        //kontrola patu 
        kontrolaPatu();

        ArrayList<Field> pouzite = new ArrayList<>();
        for (int i = 0; i < Settings.WIDTH; i++) {
            for (int j = 0; j < Settings.HEIGHT; j++) {
                if (fields[i][j].getTyp() != null && fields[i][j].getTyp() != Typ.BARRICADE) {
                    //podminka aby se znova neotacely
                    boolean otocena = false;
                    for (int k = 0; k < pouzite.size(); k++) {
                        if (pouzite.get(k) == fields[i][j]) {
                            otocena = true;
                            break;
                        }
                    }
                    smazatVsechnyKryty(fields[i][j]);
                    if (!otocena) {
                        //vytvorime si pomocne pole, Pozor na to -1
                        Field pomocne = fields[fields[i][j].i][Settings.HEIGHT - j - 1];
                        //podminky pokud se prohazuje nebo jen otaci
                        if (pomocne.getTyp() == null) {
                            pomocne.setFigure(fields[i][j].getTyp(), fields[i][j].getPlayer());
                            fields[i][j].setFigure(null);
                        } else {
                            Typ typ = pomocne.getTyp();
                            Player player = pomocne.getPlayer();
                            pomocne.setFigure(null);
                            pomocne.setFigure(fields[i][j].getTyp(), fields[i][j].getPlayer());
                            fields[i][j].setFigure(null);
                            fields[i][j].setFigure(typ, player);
                        }
                        pouzite.add(pomocne);
                    }
                }
            }
        }
        schovatOdkrytNastavHandler();
    }

    private static void schovatOdkrytNastavHandler() {
        for (int i = 0; i < Settings.WIDTH; i++) {
            for (int j = 0; j < Settings.HEIGHT; j++) {
                if (fields[i][j].getPlayer() != null) {
                    if (fields[i][j].getPlayer() != Settings.activePlayer) {
                        if (fields[i][j].getKryt() == null) {
                            fields[i][j].setKryt(new ImageView("images/" + fields[i][j].getPlayer() + "Hiden.png"));
                            fields[i][j].getKryt().setTranslateX(i * Settings.SIZE);
                            fields[i][j].getKryt().setTranslateY(j * Settings.SIZE);
                            MarshalFinal.group.getChildren().add(fields[i][j].getKryt());
                        }
                    }
                }
                //nastavovani handlaru --------------------------
                final int iFinal = i;
                final int jFinal = j;
                if (fields[i][j].getFieldView() != null) {
                    fields[i][j].getFieldView().setOnMouseClicked((MouseEvent event) -> {
                        destroyAllMoveable();
                        atack(fields[iFinal][jFinal], iFinal, jFinal);
                    });
                }
            }
        }
    }

    //rozmistovani moveable obrazku podle toho, kam muzou jednotky se hybat, utocit
    public static void atack(Field field, int i, int j) {
        ArrayList<Field> attackedFields = new ArrayList<>();
        if (field.getTyp() == Typ.CADET || field.getTyp() == Typ.CAPRAL || field.getTyp() == Typ.CAPTAIN || field.getTyp() == Typ.RIFLEMAN || field.getTyp() == Typ.SCOUT
                || field.getTyp() == Typ.GENERAL || field.getTyp() == Typ.MARSHAL || field.getTyp() == Typ.MINER || field.getTyp() == Typ.SPY) {

            if (i != 0 && atackColision(field, fields[i - 1][j])) {
                attackedFields.add(fields[i - 1][j]);
            }
            if (i != 0 && j != 0 && atackColision(field, fields[i - 1][j - 1])) {
                attackedFields.add(fields[i - 1][j - 1]);
            }
            if (i != 0 && j != Settings.HEIGHT - 1 && atackColision(field, fields[i - 1][j + 1])) {
                attackedFields.add(fields[i - 1][j + 1]);
            }
            if (j != 0 && atackColision(field, fields[i][j - 1])) {
                attackedFields.add(fields[i][j - 1]);
            }
            if (j != Settings.HEIGHT - 1 && atackColision(field, fields[i][j + 1])) {
                attackedFields.add(fields[i][j + 1]);
            }
            if (i != Settings.WIDTH - 1 && atackColision(field, fields[i + 1][j])) {
                attackedFields.add(fields[i + 1][j]);
            }
            if (i != Settings.WIDTH - 1 && j != 0 && atackColision(field, fields[i + 1][j - 1])) {
                attackedFields.add(fields[i + 1][j - 1]);
            }
            if (i != Settings.WIDTH - 1 && j != Settings.HEIGHT - 1 && atackColision(field, fields[i + 1][j + 1])) {
                attackedFields.add(fields[i + 1][j + 1]);
            }
        }
        if (field.getTyp() == Typ.RIFLEMAN) {
            for (int k = field.j; k > 0; k--) {
                if (fields[field.i][k - 1].getTyp() == Typ.BARRICADE || fields[field.i][k - 1].getPlayer() == field.getPlayer()) {
                    break;
                }
                if (fields[field.i][k - 1].getTyp() != null && field.getPlayer() != fields[field.i][k - 1].getPlayer() && field.j != k) {
                    attackedFields.add(fields[field.i][k - 1]);
                    break;
                }
            }
            for (int k = field.j; k < Settings.HEIGHT - 1; k++) {
                if (fields[field.i][k + 1].getTyp() == Typ.BARRICADE || fields[field.i][k + 1].getPlayer() == field.getPlayer()) {
                    break;
                }
                if (fields[field.i][k + 1].getTyp() != null && field.getPlayer() != fields[field.i][k + 1].getPlayer() && field.j != k) {
                    attackedFields.add(fields[field.i][k + 1]);
                    break;
                }
            }
            for (int k = field.i; k > 0; k--) {
                if (fields[k - 1][field.j].getTyp() == Typ.BARRICADE || fields[k - 1][field.j].getPlayer() == field.getPlayer()) {
                    break;
                }
                if (fields[k - 1][field.j].getTyp() != null && field.getPlayer() != fields[k - 1][field.j].getPlayer() && field.i != k) {
                    attackedFields.add(fields[k - 1][field.j]);
                    break;
                }
            }
            for (int k = field.i; k < Settings.WIDTH - 1; k++) {
                if (fields[k + 1][field.j].getTyp() == Typ.BARRICADE || fields[k + 1][field.j].getPlayer() == field.getPlayer()) {
                    break;
                }
                if (fields[k + 1][field.j].getTyp() != null && field.getPlayer() != fields[k + 1][field.j].getPlayer() && field.i != k) {
                    attackedFields.add(fields[k + 1][field.j]);
                    break;
                }
            }
        }
        if (field.getTyp() == Typ.SCOUT) {
            for (int k = field.i; k > 0; k--) {
                if (fields[k - 1][field.j].getTyp() == null) {
                    if (k != field.i) {
                        attackedFields.add(fields[k - 1][field.j]);
                    }
                }else {
                    break;
                }
            }
            for (int k = field.i; k < Settings.WIDTH - 1; k++) {
                if (fields[k + 1][field.j].getTyp() == null) {
                    if (k != field.i) {
                        attackedFields.add(fields[k + 1][field.j]);
                    }
                }else {
                    break;
                }
            }
            for (int k = field.j; k > 0; k--) {
                if (fields[field.i][k - 1].getTyp() == null) {
                    if (k != field.j) {
                        attackedFields.add(fields[field.i][k - 1]);
                    }
                }else {
                    break;
                }
            }
            for (int k = field.j; k < Settings.HEIGHT - 1; k++) {
                if (fields[field.i][k + 1].getTyp() == null) {
                    if (k != field.j) {
                        attackedFields.add(fields[field.i][k+1]);
                    }
                }else {
                    break;
                }
            }
        }
        createMoveable(attackedFields, field);
    }

    //pro kratsi podminky
    private static boolean atackColision(Field field, Field fieldtwo) {
        return field.getPlayer() != fieldtwo.getPlayer() && fieldtwo.getTyp() != Typ.BARRICADE;
    }

    //prvni parametr je napadnute pole a druhhy symbolizuje pole utocici
    private static void createMoveable(ArrayList<Field> attackedFields, Field field) {
        for (int i = 0; i < attackedFields.size(); i++) {
            attackedFields.get(i).setMoveable(new Moveable("images/moveable.png"));
            attackedFields.get(i).getMoveable().setField(attackedFields.get(i));
            root.getChildren().add(attackedFields.get(i).getMoveable());
            int iFinal = i;
            attackedFields.get(i).getMoveable().setOnMouseClicked((Event event) -> {
                Field napadnute = attackedFields.get(iFinal).getMoveable().getField();
                //pro pohyb bez boje
                if (napadnute.getPlayer() == null) {
                    napadnute.setFigure(field.getTyp(), field.getPlayer());
                    field.setFigure(null);
                } else { //primo pro bojovani zacina byt slozite vsechno zavolame v nasledujicich metodach
                    porovnavaniSouboje(field, napadnute);
                }
                //nakonci znici vsechny zlute znaky a otoci
                destroyAllMoveable();
                pretocit();
            });
        }
    }

    // metoda slouzi pro porovnavani figur ktera jakou porazi
    private static void porovnavaniSouboje(Field field, Field napadnute) {
        //podminka pokud jsou stejne
        if (field.getTyp() == napadnute.getTyp()) {
            if (field.getPlayer() == Player.RED) {
                Settings.redArmy[zjisteniIndexu(field.getTyp())]--;
                Settings.blueArmy[zjisteniIndexu(napadnute.getTyp())]--;
            } else {
                Settings.redArmy[zjisteniIndexu(napadnute.getTyp())]--;
                Settings.blueArmy[zjisteniIndexu(field.getTyp())]--;
            }

            field.setFigure(null);
            napadnute.setFigure(null);
        } else {
            for (int i = 0; i < Settings.VSECHNY_TYPY.length; i++) {
                if (field.getTyp() == Settings.VSECHNY_TYPY[i]) {
                    if (pomocnePorovnavani(i, napadnute)) { //vraci true pokud utocici prohaje
                        //nastavovani odecitani zbyvajicich jednotek pro field
                        if (field.getPlayer() == Player.BLUE) {
                            Settings.blueArmy[zjisteniIndexu(field.getTyp())]--;
                        } else {
                            Settings.redArmy[zjisteniIndexu(field.getTyp())]--;
                        }
                        field.setFigure(null);
                    } else { //prohraje napadeny
                        if (napadnute.getTyp() == Typ.FLAG) {
                            //konec hry
                            ImageView rozhodnuti = new ImageView("images/win" + Settings.opositePlayer(napadnute.getPlayer()) + ".png");
                            root.getChildren().add(rozhodnuti);
                            rozhodnuti.setTranslateX(170);
                            rozhodnuti.setTranslateY(250);
                            rozhodnuti.setOnMouseClicked((MouseEvent event) -> {
                                MarshalFinal.primaryStage.close();
                            });
                        }
                        //nastavovani odecitani zbyvajicich jednotek pro napadnute
                        if (napadnute.getPlayer() == Player.BLUE) {
                            Settings.blueArmy[zjisteniIndexu(napadnute.getTyp())]--;
                        } else {
                            Settings.redArmy[zjisteniIndexu(napadnute.getTyp())]--;
                        }
                        napadnute.setFigure(null);
                    }
                    break;
                }
            }
        }
    }

    public static void kontrolaPatu() {
        Player[] pomoc = new Player[2];
        int x = 0;
        for (int i = 0; i < Settings.WIDTH; i++) {
            for (int j = 0; j < Settings.HEIGHT; j++) {
                if (fields[i][j].getTyp() != Typ.BARRICADE && fields[i][j].getTyp() != Typ.BOMB && fields[i][j].getTyp() != Typ.FLAG) {
                    for (int k = 0; k < pomoc.length; k++) {
                        if (pomoc[k] != fields[i][j].getPlayer() && pomoc[1] == null) {
                            pomoc[x++] = fields[i][j].getPlayer();
                        }
                    }
                }
            }
        }
        if (pomoc[1] == null || pomoc[0] == null) {
            //nastaveni remizy
            ImageView remiza = new ImageView("images/draw.png");
            root.getChildren().add(remiza);
            remiza.setTranslateX(120);
            remiza.setTranslateY(250);
            remiza.setOnMouseClicked((MouseEvent event) -> {
                MarshalFinal.primaryStage.close();
            });
        }
    }

    //metoda na zjisteni indexu v poli urciteho typu
    public static int zjisteniIndexu(Typ typ) {
        for (int i = 0; i < Settings.VSECHNY_TYPY.length; i++) {
            System.out.println(typ + " = typ, " + Settings.VSECHNY_TYPY[i] + "= vsechny typy");
            if (typ == Settings.VSECHNY_TYPY[i]) {
                return i;
            }
        }
        return -1; //neco jako by vratilo null pokud to vrati je neco spatne treba typ == null
    }

    private static boolean pomocnePorovnavani(int i, Field napadnute) {
        for (int j = 0; j < slabiny.get(i).length; j++) {
            if (napadnute.getTyp() == slabiny.get(i)[j]) {
                return true;
            }
        }
        return false;
    }

    public static void smazatVsechnyKryty(Field field) {
        if (field.getKryt() != null) {
            field.getKryt().setVisible(false);
            field.setKryt(null);
        }
    }

    public static void destroyAllMoveable() {
        for (int i = 0; i < Settings.WIDTH; i++) {
            for (int j = 0; j < Settings.HEIGHT; j++) {
                if (fields[i][j].getMoveable() != null) {
                    fields[i][j].getMoveable().setVisible(false);
                    fields[i][j].setMoveable(null);
                }
            }

        }
    }

    public static Field[][] getFields() {
        return fields;
    }
}
