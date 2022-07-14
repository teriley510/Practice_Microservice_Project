package com.teriley510.departmentservice2.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Container(containerName = "departmentContainer")
public class Department {

    @Id
    private String departmentId;
    private String departmentName;
    private String departmentAddress;
    @PartitionKey
    private String departmentCode;
}

