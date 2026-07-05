package ma.bdcc.metier;

import ma.bdcc.dao.IDao;
import ma.bdcc.framework.annotation.Autowired;
import ma.bdcc.framework.annotation.Component;

@Component("metier")
public class MetierImpl implements IMetier {

    @Autowired
    private IDao dao;

    public MetierImpl() {}

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 10;
    }
}
