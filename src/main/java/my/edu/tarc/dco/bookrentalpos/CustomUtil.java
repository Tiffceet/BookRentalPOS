package my.edu.tarc.dco.bookrentalpos;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Looz
 */
public class CustomUtil {

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
