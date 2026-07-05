package ma.bdcc.presentation;

import ma.bdcc.dao.DaoImpl;
import ma.bdcc.metier.MetierImpl;

public class Pres1_InstanciationStatique {
    public static void main(String[] args) {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println("Résultat: " + metier.calcul());
    }
}
