package com.coxAuto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coxAuto.dto.UserStockMapping;
import java.util.List;

@Repository
public interface CoxAutoRepository extends JpaRepository<UserStockMapping, String> {

//    List<Vehicle> findByVehicleDetails_model(String model);
//
//    List<Vehicle> findByVehicleDetails_VehiclePrice_finalPriceBetween(Double from, Double to);
//
//    List<Vehicle> findByVehicleDetails_VehicleFeature_Exterior_valueContainingIgnoreCaseAndVehicleDetails_VehicleFeature_Interior_valueContainingIgnoreCase(String exteriorValue, String interiorValue);
}
