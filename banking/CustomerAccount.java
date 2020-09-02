package banking;

import java.util.Random;

public class CustomerAccount {
    private String creditCardNumber = "";
    private int PIN;
    private Random random = new Random();

    public String generateCreditCardNumber() {
        String precursorToCCNumber = "400000";//all accounts will have the same Bank Identification Number
        int digit;

        for (int i = 0; i < 9; i++) {
            digit = random.nextInt(10);
            precursorToCCNumber += String.valueOf(digit);
        }
        int lastDigit = generateLastDigit(precursorToCCNumber);
        System.out.println("Last digit : " + lastDigit);
        precursorToCCNumber += String.valueOf(lastDigit);
        creditCardNumber = precursorToCCNumber;
        return precursorToCCNumber;
    }

    public int generateLastDigit(String precursorToCCNumber) {
        //apply Lughn algorithm to generate a valid last digit for a credit card number
        int sum = 0;
        int digit;
        String newCCNumberForLuhm = "";

        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                digit = Character.getNumericValue(precursorToCCNumber.charAt(i)) * 2;
                if (digit > 9)
                    digit -= 9;
                newCCNumberForLuhm += String.valueOf(digit);
            } else
                newCCNumberForLuhm += precursorToCCNumber.charAt(i);
            sum += Integer.parseInt(newCCNumberForLuhm.charAt(i) + "");
        }

        if (sum % 10 == 0)
            return 0;
        return 10 - (sum % 10);

    }

    public int generatePIN() {
        int digit;
        String precursorToPIN = "";

        for (int i = 0; i < 4; i++) {
            digit = random.nextInt(10);
            precursorToPIN += String.valueOf(digit);
        }
        this.PIN = Integer.parseInt(precursorToPIN);
        return Integer.parseInt(precursorToPIN);
    }

    public void setCreditCardNumber(String ccNumber) {
        this.creditCardNumber = ccNumber;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getPIN() {
        return PIN;
    }

}
