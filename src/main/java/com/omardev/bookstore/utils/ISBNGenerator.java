package com.omardev.bookstore.utils;

import java.util.Random;

public class ISBNGenerator {

    private static final String ISBN_PREFIX = "978";  // Common ISBN-13 prefix

    public static String generateISBN() {
        // return ISBN_PREFIX + "-" + generateRandomDigits(9);
        return String.format("%s-%s", ISBN_PREFIX, generateRandomDigits(10));  // 10 digits for the rest

    }

    /**
     * Generates a random sequence of digits.
     * @param length the number of digits to generate.
     * @return a string of random digits.
     */
    private static String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder digits = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            digits.append(random.nextInt(10));  // Append a random digit (0-9)
        }
        return digits.toString();
    }
}
