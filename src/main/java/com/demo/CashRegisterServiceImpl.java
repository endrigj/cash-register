package com.demo;

import com.demo.model.CashAmount;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Cash Register Service implementation.
 */
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashAmount cashAmountOnHand = new CashAmount(0, new Integer[] {0, 0, 0, 0, 0});

    /**
     * {@inheritDoc}
     */
    @Override
    public String show() {
        return cashAmountOnHand.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String putAndShow(Integer[] numberOfBills) {
        try {
            validate(numberOfBills);
            if (cashAmountOnHand.getTotalAmount() == 0) {
                cashAmountOnHand.setNumberOfBills(numberOfBills);
            } else {

                cashAmountOnHand.setNumberOfBills(this.add(numberOfBills));

            }
            return cashAmountOnHand.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String takeAndShow(Integer[] numberOfBills) {
        try {
            validate(numberOfBills);
            if (cashAmountOnHand.getTotalAmount() == 0) {
                return NO_CASH_IN_REGISTER;
            } else {
                cashAmountOnHand.setNumberOfBills(this.takeAway(numberOfBills));
            }
            return cashAmountOnHand.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String change(Integer requestedAmount) {
        Integer totalAmountAvailable = cashAmountOnHand.getTotalAmount();
        if (requestedAmount > totalAmountAvailable) {
            return SORRY_INSUFFICIENT_FUNDS;
        }
        if (requestedAmount < 0) {
            return INVALID_AMOUNT;
        }

        Integer[] numberOfBillsArr = cashAmountOnHand.getNumberOfBillsArray();
        Integer[] quantityOfChange = new Integer[CashAmount.Bills.values().length];
        int i = 0; //iteration number
        for (CashAmount.Bills bill : CashAmount.Bills.values()) {
            int quantity = 0;
            Integer billValue = bill.getValue();
            if (requestedAmount != 0 && requestedAmount >= billValue) {
                quantity = requestedAmount / billValue;
                if (numberOfBillsArr[i] < quantity) {
                    quantity = numberOfBillsArr[i];
                }
            }
            quantityOfChange[i] = quantity;
            requestedAmount = requestedAmount - (billValue * quantity);
            i++;
        }

        if (requestedAmount > 0) {
            return NO_CHANGE_CAN_BE_MADE;
        } else {
            //update the available quantity of bills
            try {
                cashAmountOnHand.setNumberOfBills(takeAway(quantityOfChange));
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }

            return toString(quantityOfChange);
        }
    }

    private String toString(Integer[] arr) {
        StringBuilder builder = new StringBuilder();
        for (Integer r : arr) {
            builder.append(r);
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    private void validate(Integer[] numberOfBills) {
        if (numberOfBills.length != CashAmount.Bills.values().length
                || !Arrays.stream(numberOfBills).sequential().allMatch((quantity -> quantity >= 0))) {
            throw new IllegalArgumentException(INVALID_QUANTITY_OF_BILLS_PROVIDED);
        }
    }

    private Integer[] add(Integer[] numberOfBills) {
        return IntStream.range(0, numberOfBills.length)
                .map(i -> cashAmountOnHand.getNumberOfBillsArray()[i] + numberOfBills[i])
                .boxed().toArray(value -> new Integer[numberOfBills.length]);
    }

    private Integer[] takeAway(Integer[] numberOfBills) {
        Integer[] numberOfBillsLeft = IntStream.range(0, numberOfBills.length)
                .map(i -> cashAmountOnHand.getNumberOfBillsArray()[i] - numberOfBills[i])
                .boxed().toArray(value -> new Integer[numberOfBills.length]);

        //check if not taking away more than what's available
        if (!Arrays.stream(numberOfBillsLeft).allMatch(quantity -> quantity >= 0)) {
            throw new IllegalArgumentException(NOT_ENOUGH_BILLS_AVAILABLE);
        }
        return numberOfBillsLeft;
    }
}
