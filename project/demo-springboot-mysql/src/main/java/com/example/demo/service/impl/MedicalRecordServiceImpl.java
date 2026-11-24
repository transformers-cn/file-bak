package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dto.SearchMedicineRecordListDTO;
import com.example.demo.entity.Medicine;
import com.example.demo.dto.MedicineRecordDTO;
import com.example.demo.mapper.MedicineMapper;
import com.example.demo.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MedicalRecordServiceImpl
        extends ServiceImpl<MedicineMapper, Medicine>
        implements MedicalRecordService {

    @Autowired
    private MedicineMapper medicineMapper;

    // 列表查询
    public SearchMedicineRecordListDTO listMedicineRecords(String userId,String medicineName) {
        List<MedicineRecordDTO> tempListMedicineRecords = medicineMapper.selectMedicineRecordList(userId,medicineName);
        int dataCount = tempListMedicineRecords.size();
        SearchMedicineRecordListDTO searchMedicineRecordListDTO = new SearchMedicineRecordListDTO();
        searchMedicineRecordListDTO.setList(tempListMedicineRecords);
        searchMedicineRecordListDTO.setTotal(dataCount);
        return searchMedicineRecordListDTO;
    }

    public boolean checkForSaveMedicineRecord(MedicineRecordDTO medicineRecordDTO){
        // 0:add(insert),1:update
        if("0".equals(medicineRecordDTO.getAddOrUpdateFlag())){

        }else if("1".equals(medicineRecordDTO.getAddOrUpdateFlag())){

        }
        // 校验这个药品是否已经存在
        return true;
    }

    /**
     * 新增
     * @param medicineRecordDTO
     * @return
     */
    public boolean makeSaveDataForMedicineRecord(MedicineRecordDTO medicineRecordDTO){
        Medicine medicine = new Medicine();
//        private String user_id;  '用户ID（主键1）',
        medicine.setUserId(medicineRecordDTO.getUserId());
//        private String medicine_id; '药品ID（主键2）',
        medicine.setMedicineId(makeMedicineIdForInsertData(medicineRecordDTO.getUserId()));
//        private String medicine_name;'药品名称',
        medicine.setMedicineName(medicineRecordDTO.getMedicineName());
//        private String manufacturer;  '生产厂家',
        medicine.setManufacturer(medicineRecordDTO.getManufacturer());
//        private LocalDate production_date; '生产日期',
        medicine.setProductionDate(editStringToLocalDate(medicineRecordDTO.getProductionDate()));
//        private Integer shelf_life_months; '保质期（单位：月，画面上以“年”显示）',
        medicine.setShelfLifeMonths(Integer.parseInt(medicineRecordDTO.getShelfLifeMonths()));
//        private LocalDate expiry_date;  '失效日期（=生产日期+保质期，计算生成）',editStringToLocalDate(medicineRecordDTO.getExpiryDate())
        medicine.setExpiryDate(editStringToLocalDate(medicineRecordDTO.getExpiryDate()));
//        private Integer remaining_quantity;'新增（剩余）数量（盒），<=1 时画面提示购买',
        medicine.setRemainingQuantity(Integer.parseInt(medicineRecordDTO.getRemainingQuantity()));
//        private Integer tablets_per_box; '单盒药品数量（片）',
        medicine.setTabletsPerBox(Integer.parseInt(medicineRecordDTO.getTabletsPerBox()));
//        private String intake_time_code; '吃药时间区分（码表 code_id=001 下的区分ID）',
        medicine.setIntakeTimeCode(medicineRecordDTO.getIntakeTimeCode());
//        private Integer daily_times;   '每日吃药次数',
        medicine.setDailyTimes(Integer.parseInt(medicineRecordDTO.getDailyTimes()));
//        private Integer tablets_per_time; '每次吃药数量（片）',
        medicine.setTabletsPerTime(Integer.parseInt(medicineRecordDTO.getTabletsPerTime()));
//        private String remark;     '备注注意事项',
        medicine.setRemark(medicineRecordDTO.getRemark());
//        private Integer del_flag;    '删除标志：0-正常 1-已删除',
        medicine.setDelFlag(0);
//        private String create_user;  '创建人',
        medicine.setCreateUser(medicineRecordDTO.getUserId());
//        private LocalDateTime create_time;    '创建时间',
        medicine.setCreateTime(LocalDateTime.now());
//        private String update_user;  '更新人',
        medicine.setUpdateUser(medicineRecordDTO.getUserId());
//        private LocalDateTime update_time;  '更新时间',
        medicine.setUpdateTime(LocalDateTime.now());

        return save(medicine);
    }

    /**
     * 更新
     * @param medicineRecordDTO
     * @return
     */
    public boolean makeUpdateDataForMedicineRecord(MedicineRecordDTO medicineRecordDTO){
        LambdaQueryWrapper<Medicine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(medicineRecordDTO != null && medicineRecordDTO.getUserId() != null, Medicine::getUserId, medicineRecordDTO.getUserId())
                .eq(medicineRecordDTO != null && medicineRecordDTO.getMedicineId() != null, Medicine::getMedicineId, medicineRecordDTO.getMedicineId());

        Medicine medicine = medicineMapper.selectOne(queryWrapper);
        int inputQuantity = StringUtils.isEmpty(medicineRecordDTO.getRemainingQuantity())?0:Integer.parseInt(medicineRecordDTO.getRemainingQuantity());
//        private String medicine_name;'药品名称',
        medicine.setMedicineName(medicineRecordDTO.getMedicineName());
//        private String manufacturer;  '生产厂家',
        medicine.setManufacturer(medicineRecordDTO.getManufacturer());
//        private LocalDate production_date; '生产日期',
        medicine.setProductionDate(editStringToLocalDate(medicineRecordDTO.getProductionDate()));
//        private Integer shelf_life_months; '保质期（单位：月，画面上以“年”显示）',
        medicine.setShelfLifeMonths(Integer.parseInt(medicineRecordDTO.getShelfLifeMonths()));
//        private LocalDate expiry_date;  '失效日期（=生产日期+保质期，计算生成）',editStringToLocalDate(medicineRecordDTO.getExpiryDate())
        medicine.setExpiryDate(editStringToLocalDate(medicineRecordDTO.getExpiryDate()));
//        private Integer remaining_quantity;'新增（剩余）数量（盒），<=1 时画面提示购买',
        medicine.setRemainingQuantity(inputQuantity);
//        private Integer tablets_per_box; '单盒药品数量（片）',
        medicine.setTabletsPerBox(Integer.parseInt(medicineRecordDTO.getTabletsPerBox()));
//        private String intake_time_code; '吃药时间区分（码表 code_id=001 下的区分ID）',
        medicine.setIntakeTimeCode(medicineRecordDTO.getIntakeTimeCode());
//        private Integer daily_times;   '每日吃药次数',
        medicine.setDailyTimes(Integer.parseInt(medicineRecordDTO.getDailyTimes()));
//        private Integer tablets_per_time; '每次吃药数量（片）',
        medicine.setTabletsPerTime(Integer.parseInt(medicineRecordDTO.getTabletsPerTime()));
//        private String remark;     '备注注意事项',
        medicine.setRemark(medicineRecordDTO.getRemark());
//        private String update_user;  '更新人',
        medicine.setUpdateUser(medicineRecordDTO.getUserId());
//        private LocalDateTime update_time;  '更新时间',
        medicine.setUpdateTime(LocalDateTime.now());

        return update(medicine,queryWrapper);
    }
    public LocalDate editStringToLocalDate(String strLocalDate){
        String dateStr = strLocalDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date;
    }
    public String makeMedicineIdForInsertData(String userId){
        String strMaxMedicineId = medicineMapper.getMaxMedicineId(userId);
        int intMaxMedicineId= Integer.parseInt(strMaxMedicineId.replace("M",""))+1;
        return "M" + zeroPadding(String.valueOf(intMaxMedicineId),4);
    }
    /**
     * 指定桁数に達するまで前ゼロ編集します。
     * <pre>
     * 例）
     * StringUtil.zeroPadding(null,3)  = "000"
     * StringUtil.defaultString("1",3) = "001"
     * StringUtil.defaultString("1234",3) = "1234"
     * </pre>
     * @param argString     文字列

     * @param argWidth     指定桁数
     * @return 前ゼロ編集された文字列

     */
    public static String zeroPadding(String argString, int argWidth) {
        if (argString == null) argString = "";
        if (argString.length() < argWidth) {
            for (int i = argString.length(); i < argWidth; i++) {
                argString = "0" + argString;
            }
        }
        return argString;
    }
    /**
     * 更新每日服用药品数量
     * @param medicineRecordDTO
     * @return
     */
    public boolean updateMedicalRecordForPerday(MedicineRecordDTO medicineRecordDTO){

        LambdaQueryWrapper<Medicine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(medicineRecordDTO != null && medicineRecordDTO.getUserId() != null, Medicine::getUserId, medicineRecordDTO.getUserId());

        List<Medicine> medicineList = medicineMapper.selectList(queryWrapper);

        for(Medicine medicine:medicineList){
            // TODO:需要判断剩余数量够不够一天的服用量,根据剩余数量更新盒数
            // private Integer remaining_quantity;'新增（剩余）数量（盒）
            // private Integer tablets_per_box; '单盒药品数量（片）'
            int remaining_quantity = medicine.getRemainingQuantity();
            int tablets_per_box = medicine.getTabletsPerBox();
            // private Integer daily_times;   '每日吃药次数'
            // private Integer tablets_per_time; '每次吃药数量（片）'
            int daily_times = medicine.getDailyTimes();
            int tablets_per_time = medicine.getTabletsPerTime();
            // tablets_count_all INT NOT NULL DEFAULT 0 COMMENT '总剩余数量（片/粒）'
            int tablets_count_all = remaining_quantity*tablets_per_box - daily_times*tablets_per_time;
            medicine.setTabletsCountAll(tablets_count_all);
        }

        return saveOrUpdateBatch(medicineList);
    }
}