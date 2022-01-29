package et.addis.home_cakes.orders.dao;

import et.addis.home_cakes.integration.response.ExecResult;
import et.addis.home_cakes.orders.model.Pastry;

/**
 * Created by Fassil on 27/01/22.
 */
public interface PastryDAO {
    Pastry findPastryByName(String name);

    ExecResult savePastry(Pastry pastry);
}
