package ma.bdcc.presentation;

import ma.bdcc.framework.Context;
import ma.bdcc.metier.IMetier;

public class FrameworkXmlTest {
    public static void main(String[] args) throws Exception {
        Context context = new Context("config.xml");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Résultat (Framework XML): " + metier.calcul());
    }
}
