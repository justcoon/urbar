package sk.c.urbar.data;

import sk.c.urbar.data.entity.Rate;

import java.util.Collection;

/**
 * {@link sk.c.urbar.data.entity.Rate} utils
 *
 * @author coon
 */
public class RateUtils {

    private RateUtils() {
    }

    public static Integer getVotes(Integer part, Collection<Rate> rates) {
        Integer retVal = null;

        if (part != null && rates != null) {
            Rate rate = null;

            for (Rate r : rates) {

                if (part.intValue() == r.getPart().intValue()) {
                    rate = r;

                    break;
                }


                if (part.intValue() > r.getPart() && (rate == null || r.getPart() > rate.getPart())) {
                    rate = r;
                }
            }

            if (rate != null) {
                retVal = rate.getVotes();
            }
        }

        return retVal;
    }
}
