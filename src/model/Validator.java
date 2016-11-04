package model;

import exception.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author      :   Sandro Guerotto
 * @version     :   2.0
 * @created     :   04.10.2016
 * @Project     :   cloud
 * @Package     :   controller
 * @LastUpdated :
 * @Description :   Validator class that holds all methods to validate user input
 */
public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * checks email with a regex pattern for email
     * @param email String Email address to validate
     * @throws InvalidEmailException
     */
    public static void validate(String email) throws InvalidEmailException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find()) throw new InvalidEmailException();

    }
}
