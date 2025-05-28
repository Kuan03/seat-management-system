package com.example.seatmanagement.service;

import com.example.seatmanagement.model.Employee;
import com.example.seatmanagement.model.SeatingChart;
import com.example.seatmanagement.repository.EmployeeRepository;
import com.example.seatmanagement.repository.SeatingChartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SeatingChartRepository seatingChartRepository;

    /**
     * 查詢所有座位（含員工佔用資訊）
     */
    public List<SeatingChart> getAllSeats() {
        return seatingChartRepository.findAll();
    }

    /**
     * 依照樓層編號查詢該樓層的所有座位
     */
    public List<SeatingChart> getSeatsByFloor(String floorNo) {
        return seatingChartRepository.findByFloorNo(floorNo);
    }

    /**
     * 指派座位給員工（每人只能有一個座位）
     */
    @Transactional
    public String assignSeat(String empId, Long floorSeatSeq) {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        Optional<SeatingChart> seatOpt = seatingChartRepository.findById(floorSeatSeq);

        if (employeeOpt.isEmpty() || seatOpt.isEmpty()) {
            return "找不到員工或座位";
        }

        // 移除該員工原本的座位
        List<Employee> allEmployees = employeeRepository.findAll();
        for (Employee emp : allEmployees) {
            if (emp.getEmpId().equals(empId)) {
                SeatingChart seat = seatOpt.get();
                emp.setFloorSeatSeq(seat);
                employeeRepository.save(emp);
            } else if (emp.getFloorSeatSeq() != null && emp.getFloorSeatSeq().equals(floorSeatSeq)) {
                return "此座位已被其他人佔用";
            }
        }

        return "座位指派完成";
    }

    /**
     * 清除某座位的佔用資訊（變回空位）
     */
    @Transactional
    public String clearSeat(Long floorSeatSeq) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee emp : employees) {
            if (emp.getFloorSeatSeq() != null && emp.getFloorSeatSeq().equals(floorSeatSeq)) {
                emp.setFloorSeatSeq(null);
                employeeRepository.save(emp);
                return "座位清除完成";
            }
        }
        return "該座位目前未被佔用";
    }

    /**
     * 查詢所有員工（for 下拉選單）
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
