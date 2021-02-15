package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CashRegisterServiceTest {

    @Test
    public void showReturnsZero() {
        CashRegisterService service = new CashRegisterServiceImpl();
        assertEquals("$0 0 0 0 0 0", service.show());
    }

    @Test
    public void invalidQuantityOfBillsReturnsError() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.takeAndShow(new Integer[] {4, 3, 2, 1});
        assertEquals(CashRegisterService.INVALID_QUANTITY_OF_BILLS_PROVIDED, result);

        result = service.putAndShow(new Integer[] {4, 3, 2, 1});
        assertEquals(CashRegisterService.INVALID_QUANTITY_OF_BILLS_PROVIDED, result);

        result = service.putAndShow(new Integer[] {-4, 3, -2, 1});
        assertEquals(CashRegisterService.INVALID_QUANTITY_OF_BILLS_PROVIDED, result);

        result = service.change(-2);
        assertEquals(CashRegisterService.INVALID_AMOUNT, result);
    }

    @Test
    public void putAddsAmount() {
        CashRegisterService service = new CashRegisterServiceImpl();
        service.putAndShow(new Integer[] {5, 4, 3, 2, 1});
        // Total = 20*5=100 10*4=40 5*3=15 2*2=4 1*1=1
        assertEquals("$160 5 4 3 2 1", service.show());

        //adding more should increase amount
        service.putAndShow(new Integer[] {5, 4, 3, 2, 1});
        // Total = 20*10=200 10*8=80 5*6=30 2*4=8 1*2=2
        assertEquals("$320 10 8 6 4 2", service.show());
    }

    @Test
    public void putAndTakeAwayReturnsCorrectAmount() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.putAndShow(new Integer[] {5, 4, 3, 2, 1});
        // Total = 20*5=100 10*4=40 5*3=15 2*2=4 1*1=1
        assertEquals("$160 5 4 3 2 1", result);

        result = service.takeAndShow(new Integer[] {4, 3, 2, 1, 0});
        // Total = 20*1=20 10*1=10 5*1=5 2*1=2 1*1=1
        assertEquals("$38 1 1 1 1 1", result);

        result = service.takeAndShow(new Integer[] {1, 0, 1, 0, 0});
        // Total = 20*0=0 10*1=10 5*0=0 2*1=2 1*1=1
        assertEquals("$13 0 1 0 1 1", result);
    }

    @Test
    public void takeAwayFromEmptyRegisterReturnsError() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.takeAndShow(new Integer[] {4, 3, 2, 1, 0});
        // Total = 20*1=20 10*1=10 5*1=5 2*1=2 1*1=1
        assertEquals(CashRegisterService.NO_CASH_IN_REGISTER, result);
    }

    @Test
    public void takeAwayMoreThanAvailableReturnsError() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.putAndShow(new Integer[] {5, 4, 3, 2, 1});
        // Total = 20*5=100 10*4=40 5*3=15 2*2=4 1*1=1
        assertEquals("$160 5 4 3 2 1", result);

        result = service.takeAndShow(new Integer[] {6, 3, 2, 1, 0});
        // Total = 20*1=20 10*1=10 5*1=5 2*1=2 1*1=1
        assertEquals(CashRegisterService.NOT_ENOUGH_BILLS_AVAILABLE, result);
    }

    @Test
    public void changeReturnsCorrectAmount() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.putAndShow(new Integer[] {0, 4, 3, 2, 1});
        // Total = 20*0=0 10*4=40 5*3=15 2*2=4 1*1=1
        assertEquals("$60 0 4 3 2 1", result);

        result = service.change(50);
        assertEquals("0 4 2 0 0", result);

        assertEquals("$10 0 0 1 2 1", service.show());

        result = service.change(10);
        assertEquals("0 0 1 2 1", result);

        assertEquals("$0 0 0 0 0 0", service.show());

        result = service.change(10);
        assertEquals(CashRegisterService.SORRY_INSUFFICIENT_FUNDS, result);
    }

    @Test
    public void changeReturnsError() {
        CashRegisterService service = new CashRegisterServiceImpl();
        String result = service.putAndShow(new Integer[] {20, 0, 1, 2, 1});
        // Total = 20*20=400 10*0=0 5*1=5 2*2=4 1*1=1
        assertEquals("$410 20 0 1 2 1", result);

        result = service.change(19);
        assertEquals(CashRegisterService.NO_CHANGE_CAN_BE_MADE, result);

        //Same amount must be shown
        assertEquals("$410 20 0 1 2 1", service.show());
    }
}
