package org.example.store;

import org.example.account.AccountManager;
import org.example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class StoreTest {
    AccountManager accountManager = mock(AccountManager.class);
    Customer customer = new Customer();

    @Test
    void buyProductShouldWithdrawAmountFromCustomerAndDecreaseStock(){
        // Arrange
        Product product = new Product();
        product.setQuantity(4);
        product.setPrice(50);

        when(accountManager.withdraw(eq(customer), anyInt()))
                .thenReturn("success");


        Store store = new StoreImpl(accountManager);

        // Act
        store.buy(product, customer);

        // Assertion -assertj
        assertThat(product.getQuantity()).isEqualTo(3);
    }

    @Test
    void buyProductShouldGivePaymentError(){
        // Arrange
        Product product = new Product();
        product.setQuantity(4);
        product.setPrice(50);

        when(accountManager.withdraw(eq(customer), anyInt()))
                .thenReturn("failed");

        Store store = new StoreImpl(accountManager);

        // Act
        try{
            store.buy(product, customer);
            Assertions.fail("Should throw exception");
        }catch (RuntimeException ex){
            Assertions.assertEquals("Payment Failed: failed", ex.getMessage());
        }

        // Assertion
        assertThat(product.getQuantity()).isEqualTo(4);
    }

    @Test
    void buyProductShouldGiveOutOfStockError(){

        // arrange
        Product product = new Product();
        product.setQuantity(0);
        product.setPrice(50);

        when(accountManager.withdraw(eq(customer), anyInt()))
                .thenReturn("failed");

        Store store = new StoreImpl(accountManager);

        // act
        try{
            store.buy(product, customer);
            Assertions.fail("Should throw exception");
        }catch (RuntimeException ex){
            Assertions.assertEquals("Product out of stock", ex.getMessage());
        }

        // assert
        assertThat(product.getQuantity()).isEqualTo(0);
    }

}
