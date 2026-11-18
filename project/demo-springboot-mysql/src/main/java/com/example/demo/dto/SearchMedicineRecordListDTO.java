package com.example.demo.dto;
import lombok.Data;

import java.util.List;

@Data
public class SearchMedicineRecordListDTO {

    private List<MedicineRecordDTO> list;
    private int total;
}
