package model;

/**
 * This creates a data model for the client - might delete later
 */
public class Client
{
    private String firstName;
    private String lastName;
    private String social;
    private String accountNumber;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getSocial()
    {
        return social;
    }

    public void setSocial(String social)
    {
        this.social = social;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
}
