package functions;

public class Functions
{
    // will return true if string is empty or null - returning false means its good
    static boolean isStringEmpty(String string)
    {
        boolean empty = true;
        String emptyString; // removed newString() - because compiler stated that it's reduntant
        String nullString = null;

        // if string contains white spaces
        if(string.trim().length() >= 0) {
         empty = true;
            System.out.println("String contains white spaces");
        }
            // if string is not null
            if(!string.equals(nullString))
            {
                empty = false;
                System.out.println("String is null");
            }
        return empty;
    }
}
