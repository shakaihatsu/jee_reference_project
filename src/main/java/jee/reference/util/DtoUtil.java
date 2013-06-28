package jee.reference.util;

import java.lang.reflect.Field;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

@ApplicationScoped
public class DtoUtil {
    @Inject
    private Logger logger;

    public Date immute(Date date) {
        Date immutableDate;

        if (date == null) {
            immutableDate = null;
        } else {
            immutableDate = (Date) date.clone();
        }

        return immutableDate;
    }

    public boolean areFieldsEqual(Object entity1, Object entity2) {
        if (entity1 == null)
            return false;

        if (entity2 == null)
            return false;

        // Check if classes are the same
        Class<?> clazz = entity1.getClass();
        if (!clazz.equals(entity2.getClass())) {
            return false;
        }

        // Iterate through all the fields looking for differences
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            // Setting accessible to true allows reflection to have access to private members
            fields[i].setAccessible(true);
            try {
                Object value1 = fields[i].get(entity1);
                Object value2 = fields[i].get(entity2);

                if ((value1 == null && value2 != null) || (value1 != null && value2 == null)) {
                    return false;
                }

                if (value1 != null && value2 != null && !value1.equals(value2)) {
                    return false;
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Error occured while trying to check equality!", e);
            } catch (IllegalAccessException e) {
                logger.warn("Error occured while trying to check equality!", e);
            }
        }

        return true;
    }
}
