package com.teriley510.userservice.valueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private String departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
