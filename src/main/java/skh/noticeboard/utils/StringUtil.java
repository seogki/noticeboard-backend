package skh.noticeboard.utils;

public class StringUtil {
    

    public static String generateRandomString(int l) {
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        StringBuilder s = new StringBuilder(l);
        int i;

        for ( i=0; i<l; i++) {
            int ch = (int)(AlphaNumericStr.length() * Math.random());
            s.append(AlphaNumericStr.charAt(ch));
        }
 
        return "";
    } 
}
