package ma.bdcc.dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 1: Base de données");
        return 25;
    }
}
