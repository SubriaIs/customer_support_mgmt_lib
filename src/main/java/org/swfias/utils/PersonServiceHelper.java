package org.swfias.utils;

import org.swfias.enums.PersonType;

public final class PersonServiceHelper {

    private PersonServiceHelper() {

    }

    public static void validatePersonParameters(PersonType type, String firstName, String lastName, String password, String email, String phoneNumber) {
        // Check for null values
        if (type == null) {
            throw new IllegalArgumentException("PersonType cannot be null");
        }
        if (firstName == null || lastName == null || password == null || email == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Check for empty strings
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameters cannot be empty");
        }

        // Email format validation (simple regex)
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Password strength validation (e.g., at least 8 characters, containing letters and digits)
        if (password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain both letters and digits");
        }

        // Phone number format validation
        if (!phoneNumber.matches("^[0-9]{7,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    public static void validatePersonLoginParameters(String userId, String password) {
        // Check for null values

        if (userId == null || password == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        // Check for empty strings
        if (userId.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameters cannot be empty");
        }

        // Password strength validation (e.g., at least 8 characters, containing letters and digits)
        if (password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain both letters and digits");
        }
    }
}
