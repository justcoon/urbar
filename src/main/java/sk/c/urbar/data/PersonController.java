package sk.c.urbar.data;

import sk.c.urbar.Main;
import sk.c.urbar.data.entity.Person;

import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.Vector;


/**
 * {@link sk.c.urbar.data.IController} for {@link sk.c.urbar.data.entity.Person}
 *
 * @author coon
 */
public class PersonController implements IController<Person> {

    private static final PersonController INSTANCE = new PersonController();
    List<Person> values = null;

    private PersonController() {
        values = new Vector<Person>();

    }

    public static PersonController getInstance() {
        return INSTANCE;
    }

    @Override
    public void load(List<Person> v) {
        if (v != null) {
            values.clear();
            values.addAll(v);

            Main.getInstance().getEventBus().post(new ChangeEvent(PersonController.class));
        }
    }

    @Override
    public List<Person> getValues() {
        return values;
    }


}
