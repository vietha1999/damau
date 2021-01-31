/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helPer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author H2
 */
public class DateHP {
    static final SimpleDateFormat dateFM = new SimpleDateFormat("dd/mm/yyyy");
        public static Date now() {
        return new Date();
    }
    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
        public static Date add(int days) {
        Date now = DateHP.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }
    // chuyển từ String qua date
        public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                dateFM.applyPattern(pattern[0]);
            }
            if (date == null) {
                return DateHP.now();
            }
            return dateFM.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
        // chuyển từ date qua String
        public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            dateFM.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = DateHP.now();
        }
        return dateFM.format(date);
    }
        //
}
