package org.example.account;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AccountManagerTest {

    AccountManager accountManager = new AccountManagerImpl();

    @Test
    void withdrawShouldBeSuccessAndDecreaseBalance(){
        // arrange
        int amount = 50;
        Customer customer = new Customer();
        customer.setBalance(50);

        // act
        String result = accountManager.withdraw(customer, amount);

        // assertj

        assertThat(result).isEqualTo("success");
        assertThat(customer.getBalance()).isEqualTo(0);
    }

    @Test
    void withdrawShouldGiveInsufficientAccountBalanceIfCustomerIsCreditAllowedFalse(){
        // arrange
        Customer customer = new Customer();
        int amount = 50;
        customer.setBalance(40);
        customer.setCreditAllowed(false);

        // act
        String result = accountManager.withdraw(customer,amount);

        // assertj
        assertThat(result).isEqualTo("Insufficient Account Balance");
        assertThat(customer.getBalance()).isEqualTo(40);
    }

    @Test
    void withdrawShouldGiveMaximumCreditExceededIfCustomerIsNotVIPAndMaxCreditNotEnough(){
        // arrange
        Customer customer = new Customer();
        int amount = 5000;
        customer.setBalance(2000);
        customer.setCreditAllowed(true);
        customer.setMaxCredit(1000);
        customer.setVip(false);

        // act
        String result = accountManager.withdraw(customer,amount);

        // assertj
        assertThat(result).isEqualTo("maximum credit exceeded");
        assertThat(customer.getBalance()).isEqualTo(2000);
    }

    @Test
    void withdrawShouldSuccessIfCustomerIsNotVIPAndMaxCreditIsEnough(){
        // arrange
        Customer customer = new Customer();
        int amount = 5000;
        customer.setBalance(2000);
        customer.setCreditAllowed(true);
        customer.setMaxCredit(5000);
        customer.setVip(false);

        // act
        String result = accountManager.withdraw(customer,amount);

        // assertj
        assertThat(customer.getBalance()).isEqualTo(-3000);
        assertThat(result).isEqualTo("success");
    }

    @Test
    void withdrawShouldSuccessIfVIP(){
        // arrange
        Customer customer = new Customer();
        int amount = 5000;
        customer.setBalance(2000);
        customer.setCreditAllowed(true);
        customer.setVip(true);

        // act
        String result = accountManager.withdraw(customer,amount);

        // assert
        assertThat(result).isEqualTo("success");
    }

    @Test
    void depositShouldAlwaysSuccessAndIncreaseBalance(){
        // arrange
        int amount = 1000;
        Customer customer = new Customer();
        customer.setBalance(2000);
        // act
        accountManager.deposit(customer, amount);
        // assertj
        assertThat(customer.getBalance()).isEqualTo(3000);
    }

}
