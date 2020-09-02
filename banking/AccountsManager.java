package banking;


public class AccountsManager {

    private CustomerAccount currentAccount = new CustomerAccount();
    AccountsDB accountsDB;

    public AccountsManager(String DBName) {
        this.accountsDB = new AccountsDB();
        accountsDB.createNewDatabase(DBName);
        accountsDB.createCardTable();
    }

    public boolean LogIn(String cardNumber, int PIN) {
        if (accountsDB.checkLogInCredentialsFromDB(cardNumber, String.valueOf(PIN))) {
            currentAccount.setPIN(PIN);
            currentAccount.setCreditCardNumber(cardNumber);
            return true;
        }
        return false;
    }

    public void addAccount() {
        CustomerAccount newAccount = new CustomerAccount();
        String cardNumber = "";
        //generate unique credit card number
        do {
            cardNumber = newAccount.generateCreditCardNumber();
        } while (accountsDB.ccNumberInDB(cardNumber));

        int PIN = newAccount.generatePIN();
        accountsDB.addAccountInDB(cardNumber, String.valueOf(PIN));

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(PIN);

    }

    public void logOut() {
        currentAccount.setPIN(0);
        currentAccount.setCreditCardNumber("not assigned");
        System.out.println("");
        System.out.println("You have successfully logged out!");
    }

    public boolean validReceiver(String receiver) {
        if (!passesLuhn(receiver)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return false;
        } else {
            if (!accountsDB.ccNumberInDB(receiver)) {
                System.out.println("Such a card does not exist.");
                return false;
            }
        }
        return true;
    }

    public boolean passesLuhn(String cardNumber) {
        //checking if the digit returned by the generateLastDigit function is the same as the last digit in the cardNumber
        //If the condition is true then the cardNumber is a valid one,passing the Luhn algorithm.
        String cardNumberWithoutLastD = cardNumber.substring(0, cardNumber.length() - 1);
        if (new CustomerAccount().generateLastDigit(cardNumberWithoutLastD) == Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1)))
            return true;
        else
            return false;
    }

    public void doTransfer(int sumOfMoney, String receiver) {
        if (accountsDB.getBalance(currentAccount.getCreditCardNumber()) < sumOfMoney)
            System.out.println("Not enough money!");
        else if (receiver.equals(currentAccount.getCreditCardNumber())) {
            System.out.println("You can't transfer money to the same account");
        } else {
            accountsDB.subtractBalanceInDB(sumOfMoney, currentAccount.getCreditCardNumber());
            accountsDB.addBalanceInDB(sumOfMoney, receiver);
        }
    }

    public void closeCurrentAccount() {
        accountsDB.deleteAccountFromDB(currentAccount.getCreditCardNumber());
        System.out.println("The account has been closed!");
    }

    public void closeDB() {
        accountsDB.closeAll();
    }

    public void addBalance(int sumOfMoney) {
        accountsDB.addBalanceInDB(sumOfMoney, currentAccount.getCreditCardNumber());
        System.out.println("Income was added!");
    }

    public void getCurrentAccBalance() {
        int balance = accountsDB.getBalance(currentAccount.getCreditCardNumber());
        System.out.println("Balance: " + balance);
    }
}
