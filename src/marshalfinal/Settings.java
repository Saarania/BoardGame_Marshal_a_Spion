/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marshalfinal;

/**
 *
 * @author Pocitac
 */
public class Settings {
    //KONSTANTY
    public static final int HEIGHT = 11;
    public static final int WIDTH = 10;
    public static final int SIZE = 60;
    
    public static boolean figuryRozestaveny = false; //nastavi se na true pote, co se figury rozestavi a pak se nebude moct rozdavat
    
    public static final String[] VSECHNY_TYPY_STRING = {"BOMB", "CADET", "CAPRAL","CAPTAIN","FLAG", "GENERAL", "MARSHAL", "MINER", "RIFLEMAN", "SCOUT", "SPY"};
    public static final Typ [] VSECHNY_TYPY = {Typ.BOMB, Typ.CADET, Typ.CAPRAL ,Typ.CAPTAIN, Typ.FLAG, Typ.GENERAL, Typ.MARSHAL, Typ.MINER, Typ.RIFLEMAN, Typ.SCOUT, Typ.SPY};
    public static final int[] POCET_TYPU = {8,3,4,2,1,1,1,6,5,8,1};
    //8,3,4,2,1,1,1,6,5,8,1
    public static int [] redArmy = {8,3,4,2,1,1,1,6,5,8,1}/*{8,3,4,2,1,1,1,6,5,8,1}*/;
    public static int [] blueArmy = {8,3,4,2,1,1,1,6,5,8,1}/*{8,3,4,2,1,1,1,6,5,8,1}*/;
    
    public static Player activePlayer = Player.RED;
    public static Player opositePlayer(Player p) {
        if (p == Player.BLUE) {
            return Player.RED;
        }
        return Player.BLUE;
    }
    
    public static String lastType;
    public static Typ lastTypeType;
    public static int konecRozmistovani = 0;
    
    
    static int hotovoTlacitkoInt = 0;
}
