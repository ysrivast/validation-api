package org.opensource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    private Long srNo;
    private String course;
    private Date startDate;
    private Date endDate;
    private String status;
}
