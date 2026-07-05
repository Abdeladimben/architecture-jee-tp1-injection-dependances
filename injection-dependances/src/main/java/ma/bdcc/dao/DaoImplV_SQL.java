package ma.bdcc.dao;

public class DaoImplV_SQL implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 3: Web Service");
        return 40;
    }
}
