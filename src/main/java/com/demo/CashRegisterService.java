package com.demo;

import com.demo.model.CashAmount;

/**
 * The cash register service.
 **/
public interface CashRegisterService {

    String SORRY_INSUFFICIENT_FUNDS = "Sorry, insufficient funds";
    String NO_CHANGE_CAN_BE_MADE = "no change can be made";
    String NO_CASH_IN_REGISTER = "No cash in register";
    String INVALID_QUANTITY_OF_BILLS_PROVIDED = "Invalid quantity of bills provided";
    String NOT_ENOUGH_BILLS_AVAILABLE = "not enough bills available";
    String INVALID_AMOUNT = "Invalid amount";

    /**
     * Show current amount in the cash register.
     */
    String show();

    /**
     * Put bills in each denomintation.
     *
     * @param numberOfBills array of bills in each denomination #$20 #$10 #$5 #$2 #$1
     */
    String putAndShow(Integer[] numberOfBills);

    /**
     * Take bills in each denomintation.
     *
     * @param numberOfBills array of bills in each denomination #$20 #$10 #$5 #$2 #$1
     */
    String takeAndShow(Integer[] numberOfBills);

    /**
     * Show requested change in each denomination.
     *
     * @param amount requested change
     */
    String change(Integer amount);

}
