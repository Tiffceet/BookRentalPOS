package my.edu.tarc.dco.bookrentalpos;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/**
 * Contains all the custom utilities function
 * @author Looz
 * @version 1.0
 */
public class CustomUtil {

    /**
     * Convert String to its md5 hash using MessageDigest
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

}
