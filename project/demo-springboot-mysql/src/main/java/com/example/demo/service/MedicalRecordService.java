package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.dto.MedicineRecordDTO;
import com.example.demo.entity.Medicine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MedicalRecordService extends IService<Medicine> {
    // 自定义业务方法写在这里
    List<MedicineRecordDTO> listMedicineRecords(String userId,String medicineName);
    boolean checkForSaveMedicineRecord(MedicineRecordDTO medicineRecordDTO);
    boolean makeSaveDataForMedicineRecord(MedicineRecordDTO medicineRecordDTO);
    boolean makeUpdateDataForMedicineRecord(MedicineRecordDTO medicineRecordDTO);
}