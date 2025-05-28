package com.example.seatmanagement.service;

import com.example.seatmanagement.model.Employee;
import com.example.seatmanagement.model.SeatingChart;
import com.example.seatmanagement.repository.EmployeeRepository;
import com.example.seatmanagement.repository.SeatingChartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SeatingChartRepository seatingChartRepository;

    public void updateEmployeeSeat(Long empId, Long seatSeq) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("usp_UpdateEmployeeSeat")
                .registerStoredProcedureParameter("empId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("seatSeq", Long.class, ParameterMode.IN)
                .setParameter("empId", empId)
                .setParameter("seatSeq", seatSeq);

        query.execute();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String empId) {
        return employeeRepository.findById(empId);
    }

    public List<SeatingChart> getAllSeats() {
        return seatingChartRepository.findAll();
    }

    @Transactional
    public void assignSeat(String empId, Long floorSeatSeq) {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        Optional<SeatingChart> seatOpt = seatingChartRepository.findById(floorSeatSeq);

        if (employeeOpt.isPresent() && seatOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            SeatingChart seat = seatOpt.get();

            // 設定新座位
            employee.setSeatingChart(seat);
            employeeRepository.save(employee);
        }
    }

    @Transactional
    public void clearSeat(String empId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setSeatingChart(null);
            employeeRepository.save(employee);
        }
    }
}
