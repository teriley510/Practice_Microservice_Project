package com.teriley510.userservice.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Container(containerName = "usercontainer")
public class User  {
    @Id
    private String id;
    private String firstName;
    @PartitionKey
    private String lastName;
    private String departmentId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setAddress(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s", firstName, lastName, departmentId);
    }

}
