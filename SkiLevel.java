package com.customerdeal;

import java.io.Serializable;

public class LiftPass implements Pricable, Serializable {
    private int days;
    private boolean seasonPass;

    public LiftPass(int days, boolean seasonPass) {
        this.days = days;
        this.seasonPass = seasonPass;
    }

    public int getDays() {
        return days;
    }

    public boolean isSeasonPass() {
        return seasonPass;
    }

    @Override
    public double calculatePrice() {
        if (seasonPass) {
            return 200;
        }

        double total = days * 26;

        if (days >= 5) {
            total = total * 0.9;
        }

        return total;
    }

    @Override
    public String toString() {
        if (seasonPass) {
            return "Season Lift Pass - Price: $200";
        }
        return "Lift Pass for " + days + " day(s) - Price: $" + calculatePrice();
    }
}