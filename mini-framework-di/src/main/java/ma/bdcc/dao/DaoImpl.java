package ma.bdcc.dao;

import ma.bdcc.framework.annotation.Component;

@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 1: Base de données");
        return 25;
    }
}
