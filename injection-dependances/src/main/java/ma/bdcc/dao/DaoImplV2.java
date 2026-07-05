package ma.bdcc.dao;

public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 2: Capteurs");
        return 30;
    }
}
