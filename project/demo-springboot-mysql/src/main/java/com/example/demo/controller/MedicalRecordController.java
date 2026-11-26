package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.dto.MedicineRecordDTO;
import com.example.demo.dto.SearchMedicineRecordListDTO;
import com.example.demo.entity.Medicine;
import com.example.demo.service.MedicalRecordService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 用户 CRUD 示例接口，演示 MyBatis-Plus 常用 API。
 */
@RestController
@RequestMapping("/api/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/search-medical-record")
    public Result<SearchMedicineRecordListDTO> searchMedicalRecord(@RequestParam(required = false) String userId, @RequestParam(required = false) String medicineName) {
        return Result.ok(medicalRecordService.listMedicineRecords(userId,medicineName));
    }

    /** 新增：POST */
    @PostMapping("/save-medical-record")
    public Result<Boolean> save(@RequestBody MedicineRecordDTO medicineRecordDTO) {
        medicineRecordDTO.setAddOrUpdateFlag("0");
        if(medicalRecordService.checkForSaveMedicineRecord(medicineRecordDTO)){
            return Result.ok(medicalRecordService.makeSaveDataForMedicineRecord(medicineRecordDTO));
        }
        return Result.fail("insert data error!");
    }
    /** 按 id 删除：DELETE */
    @PostMapping("/delete-medical-record")
    public Result<Boolean> remove(@RequestBody MedicineRecordDTO medicineRecordDTO) {
        LambdaQueryWrapper<Medicine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(medicineRecordDTO != null && medicineRecordDTO.getUserId() != null, Medicine::getUserId, medicineRecordDTO.getUserId())
                .eq(medicineRecordDTO != null && medicineRecordDTO.getMedicineId() != null, Medicine::getMedicineId, medicineRecordDTO.getMedicineId());
        return Result.ok(medicalRecordService.remove(queryWrapper));
    }
    /** 更新：POST */
    @PostMapping("/update-medical-record")
    public Result<Boolean> update(@RequestBody MedicineRecordDTO medicineRecordDTO) {
        medicineRecordDTO.setAddOrUpdateFlag("1");
        if(medicalRecordService.checkForSaveMedicineRecord(medicineRecordDTO)){
            return Result.ok(medicalRecordService.makeUpdateDataForMedicineRecord(medicineRecordDTO));
        }
        return Result.fail("update data error!");
    }

    /** 更新每日服用药品数量：POST */
    @PostMapping("/update-medical-record-for-perday")
    public Result<Boolean> updateMedicalRecordForPerday(@RequestBody MedicineRecordDTO medicineRecordDTO) {
        String returnValue = medicalRecordService.updateMedicalRecordForPerdayCheck(medicineRecordDTO);
        if(!StringUtils.isEmpty(returnValue)){
            return Result.fail(returnValue);
        }else{
            return Result.ok(medicalRecordService.updateMedicalRecordForPerday(medicineRecordDTO));
        }
    }
}
