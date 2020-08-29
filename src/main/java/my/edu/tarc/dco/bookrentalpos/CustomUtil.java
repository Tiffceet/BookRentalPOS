package my.edu.tarc.dco.bookrentalpos;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains all the custom utilities function
 *
 * @author Looz
 * @version 1.0
 */
public class CustomUtil {

    /**
     * Convert String to its md5 hash using MessageDigest
     *
     * @param st String to be converted to hash
     * @return hashed String
     */
    public static String md5Hash(String st) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(st.getBytes());
            String myHash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
            return myHash;
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Checks if NRIC fits the Malaysia's IC Standard<br>
     * Note that icNo given must not contain '-'
     *
     * @param icNo NRIC in String
     * @return True if the NRIC is valid, false if otherwise
     */
    public static boolean checkIC(String icNo) {
        if (icNo == null || icNo.length() != 12) {
            return false;
        }
        String format = "^[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|[3][0-1])(?:0[1-9]|1[0-6]|2[1-9]|[3-5][0-9]|6[0-8]|7[1-2]|7[4-9]|8[2-9]|9[0-3]|9[8-9])[0-9]{4}$";
        Pattern ic = Pattern.compile(format);
        Matcher m = ic.matcher(icNo);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if phone number is valid. <br>
     * will check if the number starts with 0
     *
     * @param phoneNo - phone number in String
     * @return return true if phone is valid, false if otherwise
     */
    public static boolean checkPhoneNo(String phoneNo) {
        if (phoneNo == null || (phoneNo.length() != 10 && phoneNo.length() != 11)) {
            return false;
        }
        String format = "^01[0-9]+$";
        Pattern phone = Pattern.compile(format);
        Matcher m = phone.matcher(phoneNo);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])([a-zA-Z0-9]+){8,}$");
    }

    /**
     * Check if email is valid
     *
     * @param email email adress in String
     * @return true if email is valid, false if otherwise
     */
    public static boolean checkEmail(String email) {
        if (email == null)
            return false;
        String format = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(format);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert date time generated in sqlite database to java date object
     *
     * @param date - date in String from database
     * @return date object if parsed successfully, null if ParseException was thrown
     */
    public static Date stringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate the date difference between 2 java date object
     *
     * @param date1 java.util.Date object
     * @param date2 java.util.Date object
     * @return days difference in long, throw NullPointerException if date1 or date2 is null
     */
    public static long daysDifference(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException();
        }
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    /**
     * This method will increment the provided localdate object by specified days
     *
     * @param date      Localdate object
     * @param increment days to increment
     * @return LocalDate of the incremented date
     */
    public static LocalDate daysIncrement(LocalDate date, int increment) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, increment);
        return c.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
