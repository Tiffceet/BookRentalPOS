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
	    private String email;
    private String icNo;
    private String phoneNo;

    public String getIcNo() {
        return this.icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean checkIC(String icNo){
        char ch2 = icNo.charAt(2);
        char ch3 = icNo.charAt(3);
        char ch4 = icNo.charAt(4);
        char ch5 = icNo.charAt(5);
        char ch6 = icNo.charAt(6);
        char ch7 = icNo.charAt(7);

        if(icNo == null || icNo.length() != 12 || (ch2 == '0' && ch3 == '0') || (ch4 == '0' && ch5 == '0') || (ch6 == '0' && ch7 == '0')){
            return false;
        }
        String format = "^[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|[3][0-1])(?:0[1-9]|1[0-6]|2[1-9]|[3-5][0-9]|6[0-8]|7[1-2]|7[4-9]|8[2-9]|9[0-3]|9[8-9])[0-9]{4}$";
        Pattern ic = Pattern.compile(format);
        Matcher m = ic.matcher(icNo);
        if(m.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkPhoneNo(String phoneNo){
        if (phoneNo == null || (phoneNo.length() != 10 && phoneNo.length() != 11)){
            return false;
        }
        String format = "^01[0-9]+$";
        Pattern phone = Pattern.compile(format);
        Matcher m = phone.matcher(phoneNo);
        if(m.matches()){
            return true;
        }
        else{
            return false;   
        }
    }
    
    public static boolean checkEmail(String email) {
		if (email == null)
			return false;
		String format = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(format);
		Matcher m = p.matcher(email);
		if (m.matches()){
            return true;
        }
		else{
            return false;
        }
	}

}
