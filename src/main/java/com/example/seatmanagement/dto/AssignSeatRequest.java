package com.example.seatmanagement.dto;

public class AssignSeatRequest {
    private String empId;
    private Long floorSeatSeq;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Long getFloorSeatSeq() {
        return floorSeatSeq;
    }

    public void setFloorSeatSeq(Long floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }
}
