package org.swfias.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.swfias.enums.SeverityType;
import org.swfias.enums.StatusType;

import java.sql.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CaseDto {
    private long id;
    private long createdBy;
    private String title;
    private String description;
    private Date createdDate;
    private StatusType status;
    private SeverityType severity;
    private long assignedTo;
    private String resolutionDetails;
    private Date resolvedDate;

    public CaseDto(long createdBy, String title, String description, Date createdDate, StatusType status, SeverityType severity, long assignedTo, String resolutionDetails, Date resolvedDate) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.status = status;
        this.severity = severity;
        this.assignedTo = assignedTo;
        this.resolutionDetails = resolutionDetails;
        this.resolvedDate = resolvedDate;
    }

}
