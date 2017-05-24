package functions;

public class Functions
{
    // will return true if string is empty or null - returning false means its good
    public static boolean isStringEmpty(String string)
    {
        boolean empty;
        String emptyString; // removed newString() - because compiler stated that it's reduntant
        String nullString = null;
        System.out.println("String length: " + string.length());

        if(string.length() == 0) { // empty string
         empty = true;
        }
        else if(string.trim().length() == 0) // checks for white spaces
        {
            System.out.println("0 after trim length");
            empty = true;
        }
        else
        {
            empty = false;
        }
        return empty;
    }
}
