package ma.bdcc.presentation;

import ma.bdcc.framework.Context;
import ma.bdcc.metier.IMetier;

public class FrameworkAnnotationTest {
    public static void main(String[] args) throws Exception {
        Context context = new Context("ma.bdcc", true);
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Résultat (Framework Annotations): " + metier.calcul());
    }
}
