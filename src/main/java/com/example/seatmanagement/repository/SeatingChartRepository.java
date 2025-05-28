package com.example.seatmanagement.repository;

import java.util.List;
import com.example.seatmanagement.model.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatingChartRepository extends JpaRepository<SeatingChart, Long> {

    List<SeatingChart> findByFloorNo(String floorNo);
    List<SeatingChart> findByFloorSeatSeq(String floorSeatSeq);
}
