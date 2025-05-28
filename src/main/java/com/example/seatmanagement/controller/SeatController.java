package com.example.seatmanagement.controller;

import com.example.seatmanagement.model.Employee;
import com.example.seatmanagement.model.SeatingChart;
import com.example.seatmanagement.service.SeatService;
import com.example.seatmanagement.service.EmployeeService;
import com.example.seatmanagement.repository.SeatingChartRepository;
import com.example.seatmanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SeatingChartRepository seatingChartRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // 查詢所有樓層的座位資訊（含佔用與否）
    @GetMapping
    public List<SeatingChart> getAllSeats() {
        return seatService.getAllSeats();
    }

    // 依樓層編號查詢該樓層座位資訊
    @GetMapping("/floor/{floorNo}")
    public List<SeatingChart> getSeatsByFloor(@PathVariable String floorNo) {
        return seatService.getSeatsByFloor(floorNo);
    }

    // 指派座位（更新員工座位資訊）
//    @PostMapping("/assign")
//    public String assignSeat(@RequestParam String empId, @RequestParam Long floorSeatSeq) {
//        return seatService.assignSeat(empId, floorSeatSeq);
//    }

    @PostMapping("/employee/{id}/seat/{seatSeq}")
    public ResponseEntity<String> updateSeat(@PathVariable Long id, @PathVariable Long seatSeq) {
        employeeService.updateEmployeeSeat(id, seatSeq);
        return ResponseEntity.ok("座位已更新！");
    }

    // 清除座位（將員工與座位解除關聯）
    @PostMapping("/clear")
    public String clearSeat(@RequestParam Long floorSeatSeq) {
        return seatService.clearSeat(floorSeatSeq);
    }

    // 查詢所有員工（for 下拉選單）
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return seatService.getAllEmployees();
    }

    public String assignSeat(String empId, Long floorSeatSeq) {
        Optional<SeatingChart> seatOpt = seatingChartRepository.findById(floorSeatSeq);
        if (!seatOpt.isPresent()) {
            return "找不到該座位";
        }

        Optional<Employee> empOpt = employeeRepository.findById(empId);
        if (!empOpt.isPresent()) {
            return "找不到該員工";
        }

        SeatingChart seat = seatOpt.get();

        if (seat.getEmpId() != null && !seat.getEmpId().equals(empId)) {
            return "此座位已被其他人指派";
        }

        // 若此員工已被指派到其他座位，要先清掉
        seatingChartRepository.findAll().forEach(s -> {
            if (empId.equals(s.getEmpId()) && !s.getFloorSeatSeq().equals(floorSeatSeq)) {
                s.setEmpId(null);
                seatingChartRepository.save(s);
            }
        });

        seat.setEmpId(empId);
        seatingChartRepository.save(seat);
        return "指派成功";
    }

}
