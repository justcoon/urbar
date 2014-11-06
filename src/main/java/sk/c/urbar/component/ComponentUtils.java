package sk.c.urbar.component;

//import javafx.scene.control.DatePicker;

//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;

/**
 * @author coon
 */
public final class ComponentUtils {
    private ComponentUtils() {

    }

//    public static void setDateToPicker(DatePicker picker, Object value, String property) {
//        if (picker != null && value != null && property != null) {
//            Date v = null;
//
//            try {
//                v = (Date) PropertyUtils.getProperty(value, property);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//            if (v != null) {
//
//                GregorianCalendar gc = new GregorianCalendar();
//                gc.setTime(v);
//                LocalDate ld = gc.toZonedDateTime().toLocalDate();
//                picker.setValue(ld);
//
//            } else {
//                picker.setValue(null);
//            }
//        }
//    }
//
//    public static void setDateToValue(DatePicker picker, Object value, String property) {
//        if (picker != null && value != null && property != null) {
//            Date v = null;
//
//            if (picker.getValue() != null) {
//                v = GregorianCalendar.from(ZonedDateTime.of(picker.getValue(), LocalTime.of(0, 0), ZoneId.systemDefault())).getTime();
//            }
//            try {
//                PropertyUtils.setProperty(value, property, v);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//
//        }
//    }
//
//    public static void setConverter(final DatePicker picker, final String format) {
//
//        if (picker != null && format != null) {
//            picker.setConverter(new StringConverter<LocalDate>() {
//
//                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
//
//                {
//                    picker.setPromptText(format.toLowerCase());
//                }
//
//                @Override
//                public String toString(LocalDate date) {
//                    if (date != null) {
//                        return dateFormatter.format(date);
//                    } else {
//                        return "";
//                    }
//                }
//
//                @Override
//                public LocalDate fromString(String string) {
//                    if (string != null && !string.isEmpty()) {
//                        return LocalDate.parse(string, dateFormatter);
//                    } else {
//                        return null;
//                    }
//                }
//            });
//        }
//
//    }
}
