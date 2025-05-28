package com.example.seatmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SeatingChart")
@Getter @Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatingChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="FLOOR_SEAT_SEQ")
    private Long floorSeatSeq;

//    @Column(name ="FLOOR_NO")
    private String floorNo;

//    @Column(name ="SEAT_NO")
    private String seatNo;

    private String empId;
}

