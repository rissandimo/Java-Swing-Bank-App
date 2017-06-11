package functions;

import javax.swing.*;

public class Functions
{

    public static boolean checkClientInfo(String clientInfo)
    {
        boolean informationCorrect;

        if(isStringEmpty(clientInfo))
        {
            informationCorrect = false;
        }
        else
        {
            informationCorrect = true;
        }

        return  informationCorrect;
    }


    // will return true if string is empty or null - returning false means its good
    public static boolean isStringEmpty(String string)
    {
        boolean empty;
        String emptyString; // removed newString() - because compiler stated that it's reduntant
        String nullString = null;


        if(string.length() == 0)
        { // empty string
         empty = true;
        }
        else if(string.trim().length() == 0) // checks for white spaces
        {
            empty = true;
        }
        else
        {
            empty = false;
        }
        return empty;
    }

    public static boolean isEmailCorrect(String email)
    {
        int numOfSigns = 0;
        boolean correct = false;

        for(int i=0; i < email.length(); i++)
        {
            if(email.charAt(i) == '@')
            {
                numOfSigns++;
            }
        }

        if(numOfSigns == 1)
        {
          correct = true;
        }

        return correct;
    }
}
