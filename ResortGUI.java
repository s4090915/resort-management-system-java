package com.customerdeal;

import java.io.Serializable;

public class Lesson implements Pricable, Serializable {
    private int numberOfLessons;
    private SkiLevel level;

    public Lesson(int numberOfLessons, SkiLevel level) {
        this.numberOfLessons = numberOfLessons;
        this.level = level;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public SkiLevel getLevel() {
        return level;
    }

    @Override
    public double calculatePrice() {
        double pricePerLesson = 0;

        switch (level) {
            case BEGINNER:
                pricePerLesson = 25;
                break;
            case INTERMEDIATE:
                pricePerLesson = 20;
                break;
            case EXPERT:
                pricePerLesson = 15;
                break;
        }

        return numberOfLessons * pricePerLesson;
    }

    @Override
    public String toString() {
        return "Lessons: " + numberOfLessons +
                ", Level: " + level +
                ", Price: $" + calculatePrice();
    }
}