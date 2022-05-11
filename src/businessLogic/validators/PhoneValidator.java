package businessLogic.validators;
import model.Client;
import java.util.regex.Pattern;

/**
 * Validates phone number using regex and checks if it has between 5 and 10 digits.
 * @author ioana
 *
 *
 */

public class PhoneValidator implements Validator<Client>{
    private static final String phonePattern = "[0-9]+";
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(phonePattern);
        if(!pattern.matcher(client.getPhoneNb()).matches() || client.getPhoneNb().length()<5 ||
                client.getPhoneNb().length()>10){
            throw new IllegalArgumentException("Phone number is not valid");
        }
    }
}
