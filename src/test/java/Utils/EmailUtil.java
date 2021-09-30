package Utils;

import interfaces.IEmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil implements IEmailValidator{
    public Boolean validateEmail(String email) {
        Matcher matcher = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}(.[a-z]{2,3})+$|^$", Pattern.CASE_INSENSITIVE).matcher(email);
        if (!matcher.find())
            return false;
        return true;
    }
}
