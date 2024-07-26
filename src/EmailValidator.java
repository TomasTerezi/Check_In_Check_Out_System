public class EmailValidator {
	// Regex to match email addresses ending with .gmail or .yahoo
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@(gmail\\.com|yahoo\\.com|hotmail\\.com)$";

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

}
