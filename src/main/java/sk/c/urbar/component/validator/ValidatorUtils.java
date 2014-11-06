package sk.c.urbar.component.validator;

import java.util.*;

/**
 * @author coon on 12/30/13.
 */
public class ValidatorUtils {
    private ValidatorUtils() {

    }

    public static boolean containsStates(Collection<? extends IValidationResult> results, ValidatorState... states) {
        boolean retVal = false;

        if (results != null && !results.isEmpty() && states != null && states.length > 0) {
            Set<ValidatorState> ss = new HashSet<ValidatorState>(Arrays.asList(states));

            for (IValidationResult r : results) {
                if (ss.contains(r.getState())) {
                    retVal = true;

                    break;
                }
            }

        }

        return retVal;

    }

    public static String getMessage(Collection<? extends IValidationResult> results, String separator, ValidatorState... states) {
        String retVal = null;


        if (results != null && !results.isEmpty()) {
            Set<ValidatorState> ss = states != null && states.length > 0 ? new HashSet<ValidatorState>(Arrays.asList(states)) : null;
            IValidationResult r;
            Iterator<? extends IValidationResult> iterator = results.iterator();

            StringBuilder sb = new StringBuilder();

            boolean append = false;

            while (iterator.hasNext()) {
                r = iterator.next();

                if (ss == null || ss.contains(r.getState())) {


                    if (append && separator != null) {
                        sb.append(separator);
                    }

                    sb.append(r.getMessage());

                    append = true;

                }

            }
            if (sb.length() > 0) {
                retVal = sb.toString();
            }

        }

        return retVal;

    }
}
