package my.edu.tarc.dco.bookrentalpos;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
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

    /**
     * Check if email is valid
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

}
