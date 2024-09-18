package org.swfias.utils;

import org.swfias.enums.SeverityType;
import org.swfias.enums.StatusType;

import java.sql.Date;

public final class CaseServiceHelper {

    public static void validateCaseParameters(long createdBy, String title, String description, Date createdDate, StatusType status, SeverityType severity) {
        // Check for null values
        if (title == null || description == null || createdDate == null || status == null || severity == null) {
            throw new IllegalArgumentException("Required parameters cannot be null");
        }

        // Check for empty strings
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Title and description cannot be empty");
        }

        // Check for valid IDs
        if (createdBy <= 0) {
            throw new IllegalArgumentException("CreatedBy and AssignedTo must be positive non-zero values");
        }

    }
}
