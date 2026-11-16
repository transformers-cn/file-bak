package com.example.demo.dto;
import lombok.Data;

@Data
public class MedicineRecordDTO {

    private String userId;  // : "U0001",
    private String medicineId;  // : "M0001",
    private String medicineName;  // : "阿司匹林",
    private String manufacturer;  // : "XXX制药厂",
    private String productionDate;  // : "2026-01-01",
    private String shelfLifeMonths;  // : 24,
    private String expiryDate;  // : "2028-01-01",
    private String remainingQuantity;  // : 3,
    private String tabletsPerBox;  // : 7,
    private String intakeTimeCode;  // : "002",
    private String intakeTimeCodeName;
    private String dailyTimes;  // : 1,
    private String tabletsPerTime;  // : 1,
    private String remark;  // : ""
}
