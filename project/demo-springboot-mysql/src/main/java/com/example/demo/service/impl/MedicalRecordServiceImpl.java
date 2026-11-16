package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Medicine;
import com.example.demo.dto.MedicineRecordDTO;
import com.example.demo.mapper.MedicineMapper;
import com.example.demo.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl
        extends ServiceImpl<MedicineMapper, Medicine>
        implements MedicalRecordService {

    @Autowired
    private MedicineMapper medicineMapper;

    // 列表查询
    public List<MedicineRecordDTO> listMedicineRecords(String medicineName) {
        return medicineMapper.selectMedicineRecordList(medicineName);
    }
}