package banking;

import  org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountsDB {
    Connection connection;
    String url;
    SQLiteDataSource dataSource;
    Statement statement;

    public void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void createCardTable(){
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(\n"
                    + "     id INTEGER PRIMARY KEY,\n"
                    + "     number TEXT,\n"
                    + "     pin TEXT,\n"
                    + "     balance INTEGER DEFAULT 0"
                    + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccountInDB(String cardNumber,String PIN){
        try {
            statement.executeUpdate("INSERT INTO card(number,pin) VALUES"
                    +"('"+cardNumber+"','"+String.valueOf(PIN)+"')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkLogInCredentialsFromDB(String cardNumber,String PIN) {
        try (ResultSet allCards = statement.executeQuery("SELECT * FROM card")) {
            while (allCards.next()) {
                // Retrieve column values

                String card = allCards.getString("number");
                String pin = allCards.getString("pin");

                if (card.equals(cardNumber) && PIN.equals(pin))
                    return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean ccNumberInDB(String cardNumber){
        try (ResultSet allCards = statement.executeQuery("SELECT * FROM card")) {
            while (allCards.next()) {
                // Retrieve column values

                String card = allCards.getString("number");
                if(card.equals(cardNumber))
                   return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void addBalanceInDB(int sumOfMoney,String cardNumber){
        try {
            statement.executeUpdate("UPDATE card SET balance=balance + " + sumOfMoney +"\n"
                    +" WHERE number=" + cardNumber + ";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void subtractBalanceInDB(int sumOfMoney,String cardNumber){
        try {
            statement.executeUpdate("UPDATE card SET balance=balance - " + sumOfMoney +"\n"
                    +" WHERE number=" + cardNumber + ";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getBalance(String cardNumber){
        try (ResultSet currentAccount = statement.executeQuery("SELECT balance FROM card WHERE number=" + cardNumber + ";")) {
            if(currentAccount.next()) {
                // Retrieve column values
                return currentAccount.getInt("balance");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void deleteAccountFromDB(String cardNumber){
        try {
            statement.executeUpdate("DELETE FROM card WHERE number=" + cardNumber + ";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeAll(){
        if(statement!=null || connection!=null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
