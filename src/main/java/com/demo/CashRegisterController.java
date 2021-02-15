package com.demo;

import com.demo.model.CashAmount;
import java.util.stream.IntStream;


/**
 * Controller class for processing commands.
 */
public class CashRegisterController {
    public static final String TOO_MANY_OR_TOO_FEW_ARGUMENTS = "Too many or too few arguments";
    private CashRegisterService service = new CashRegisterServiceImpl();

    /**
     * Returns the available amount and quantity of bills in the cash register.
     *
     * @return total amount and quantity of bills in this format: $Total #$20 #$10 #$5 #$2 #$1
     */
    public String show() {
        return service.show();
    }

    /**
     * Add bills in each denomination and show current state.
     *
     * @param command the quantity of bills to add
     * @return total amount and quantity of bills in this format: $Total #$20 #$10 #$5 #$2 #$1
     */
    public String putAndShow(String command) {
        try {
            Integer[] numberOfBills = getNumberOfBills(command);
            return service.putAndShow(numberOfBills);
        } catch (IllegalArgumentException e) {
            return String.format("Input error %s", e.getMessage());
        }
    }

    /**
     * Take away bills in each denomination and show current state.
     *
     * @param command the quantity of bills to take away
     * @return total amount and quantity of bills in this format: $Total #$20 #$10 #$5 #$2 #$1
     */
    public String takeAndShow(String command) {
        try {
            Integer[] numberOfBills = getNumberOfBills(command);
            return service.takeAndShow(numberOfBills);
        } catch (IllegalArgumentException e) {
            return String.format("Input error, %s", e.getMessage());
        }
    }

    /**
     * Shows the requested change.
     *
     * @param amount amount of requested change
     * @return quantity of bills in this format: #$20 #$10 #$5 #$2 #$1
     */
    public String change(String amount) {
        String[] args = amount.split(" ");
        if (args.length != 2) {
            return TOO_MANY_OR_TOO_FEW_ARGUMENTS;
        }
        return service.change(Integer.valueOf(args[1]));
    }

    private Integer[] getNumberOfBills(String command) {
        String[] args = command.split(" ");
        if (args.length != 6) {
            throw new IllegalArgumentException(TOO_MANY_OR_TOO_FEW_ARGUMENTS);
        }
        return IntStream.range(1, args.length)
                .map(i -> Integer.parseInt(args[i]))
                .boxed().toArray(value -> new Integer[CashAmount.Bills.values().length]);
    }
}
