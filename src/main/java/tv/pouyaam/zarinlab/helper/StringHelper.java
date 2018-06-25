package tv.pouyaam.zarinlab.helper;

import tv.pouyaam.zarinlab.model.constants.Constant;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static boolean isItEmail(String string) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(string);
        return mat.matches();
    }

    public static boolean isValidISOLatin1(String s) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s);
    }

    public static HashMap<String, String> extractDataFromZarinpalDesc(String description) {
        description = description.trim();
        HashMap<String, String> extractedParts = new HashMap<>();

        if (description != null && description.length() > 0) {
            String[] descriptionParts = description.replace("\n", ";").split(";");

            if (descriptionParts.length == 2) {

                for (String desc : descriptionParts) {
                    String[] str = desc.split(":");
                    if (str.length == 2) {
                        String key;
                        str[1] = str[1].trim();
                        if (str[0].contains("پرداخت کننده")) {
                            key = Constant.PAYER_KEY;
                            if (!isValidISOLatin1(str[1]) || str[1].length() == 0)
                                str[1] = "Guest";

                        } else {
                            key = Constant.DESC_KEY;
                        }

                        extractedParts.put(key, str[1]);
                    }
                }

            }
        }

        return extractedParts;
    }

}
