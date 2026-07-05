package ma.bdcc.presentation;

import ma.bdcc.dao.IDao;
import ma.bdcc.metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Pres2_InstanciationDynamique {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            String daoClassName = scanner.nextLine();
            String metierClassName = scanner.nextLine();
            scanner.close();

            Class<?> daoClass = Class.forName(daoClassName);
            IDao dao = (IDao) daoClass.getDeclaredConstructor().newInstance();

            Class<?> metierClass = Class.forName(metierClassName);
            IMetier metier = (IMetier) metierClass.getDeclaredConstructor().newInstance();

            Method setter = metierClass.getMethod("setDao", IDao.class);
            setter.invoke(metier, dao);

            System.out.println("Résultat: " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
