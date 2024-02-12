package org.example.store;

import org.example.account.AccountManager;
import org.example.account.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.List;

public class MockTest {

    @Test
    void  test2(){
        AccountManager accountManager = mock(AccountManager.class);

        Customer customer = new Customer();

//        when(accountManager.withdraw(eq(customer), anyInt()))
//                .thenReturn("success");

//        when(accountManager.withdraw(customer, 11))
//                .thenReturn("failed");

        // return dynamic
        when(accountManager.withdraw(eq(customer), anyInt()))
                .then(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return "success" + invocationOnMock.getArgument(1);
                    }
                });


        System.out.println(accountManager.withdraw(customer, 10));
        System.out.println(accountManager.withdraw(customer, 11));


        verify(accountManager).withdraw(customer, 10);
        verify(accountManager).withdraw(customer, 11);
//        verify(accountManager).withdraw(customer, 12);
    }

    @Test
    void test(){
        List list= mock(List.class);

        list.add("red");
        list.add("green");

        verify(list).add("red");
        verify(list).add("green");

    }

}
