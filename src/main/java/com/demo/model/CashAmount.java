package com.demo.model;

import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Amouunt of cash, with number of bills.
 */
@AllArgsConstructor
public class CashAmount {

    /**
     * The total amount of cash.
     */
    @Setter
    private Integer totalAmount;
    /**
     * The quantity of bill denominations.
     */
    @Getter
    private Integer[] numberOfBillsArray;

    /**
     * Returns the total amount of cash.
     *
     * @return totalAmount
     */
    public Integer getTotalAmount() {
        totalAmount = IntStream.range(0, Bills.values().length)
                .map(i -> Bills.values()[i].getValue() * numberOfBillsArray[i])
                .sum();
        return totalAmount;
    }

    /**
     * Set the available quantity of each bill denomination.
     * Recalculates the total amount.
     *
     * @param newNumberOfBills quantity of bill denominations
     */
    public void setNumberOfBills(Integer[] newNumberOfBills) {
        numberOfBillsArray = newNumberOfBills;
        getTotalAmount();
    }

    @Override
    public String toString() {
        return String.format("$%s %s %s %s %s %s", getTotalAmount(),
                numberOfBillsArray[0],
                numberOfBillsArray[1],
                numberOfBillsArray[2],
                numberOfBillsArray[3],
                numberOfBillsArray[4]
        );

    }

    /**
     * Represents the bills available and their corresponding values.
     */
    public enum Bills {
        TWENTY(20),
        TEN(10),
        FIVE(5),
        TWO(2),
        ONE(1);

        @Getter
        private final Integer value;

        Bills(Integer value) {
            this.value = value;
        }
    }
}
