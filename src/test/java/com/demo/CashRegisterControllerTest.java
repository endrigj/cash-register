package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CashRegisterControllerTest {
    CashRegisterController controller = new CashRegisterController();

    @Test
    public void showReturnsZero() {
        assertEquals("$0 0 0 0 0 0", controller.show());
    }

    @Test
    public void putAndTake() {
        assertEquals("$38 1 1 1 1 1", controller.putAndShow("put 1 1 1 1 1"));
        assertEquals("$0 0 0 0 0 0", controller.takeAndShow("take 1 1 1 1 1"));
    }

    @Test
    public void change() {
        CashRegisterController controller = new CashRegisterController();
        assertEquals(CashRegisterController.TOO_MANY_OR_TOO_FEW_ARGUMENTS,
                controller.change("change 1 1 1 1 1"));
    }

}
