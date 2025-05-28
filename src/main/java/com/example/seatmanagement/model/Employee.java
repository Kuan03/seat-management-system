package com.example.seatmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.jar.Attributes;

@Entity
@Table(name = "Employee")
@Getter @Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "EMP_ID",length = 5)
    private String empId;

    @Column(name ="Name")
    private String name;

    @Column(name ="Email")
    private String email;

    @ManyToOne
//    @JoinColumn(name = "floor_seat_seq")
    @JoinColumn(name = "floor_seat_seq", referencedColumnName = "FLOOR_SEAT_SEQ")
    private SeatingChart seatingChart;
    private SeatingChart floorSeatSeq;
}
