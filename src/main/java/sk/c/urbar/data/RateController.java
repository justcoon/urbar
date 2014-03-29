package sk.c.urbar.data;

import sk.c.urbar.Main;
import sk.c.urbar.data.entity.Rate;

import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.Vector;


/**
 * {@link IController} for {@link sk.c.urbar.data.entity.Rate}
 *
 * @author coon
 */
public class RateController implements IController<Rate> {

    private static final RateController INSTANCE = new RateController();
    List<Rate> values = null;

    private RateController() {
        values = new Vector<Rate>();

    }

    public static RateController getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized void load(List<Rate> v) {
        if (v != null) {
            values.clear();
            values.addAll(v);


            Main.getInstance().getEventBus().post(new ChangeEvent(RateController.class));
        }
    }

    @Override
    public List<Rate> getValues() {
        return values;
    }


}
