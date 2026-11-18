package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.demo.common.Result;
import com.example.demo.dto.MedicineRecordDTO;
import com.example.demo.dto.SearchMedicineRecordListDTO;
import com.example.demo.entity.Medicine;
import com.example.demo.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    /** 新增：POST /api/users */
//    @PostMapping
//    public Result<Boolean> save(@RequestBody User user) {
//        return Result.ok(userService.save(user));
//    }
//
//    /** 按 id 删除：DELETE /api/users/1 */
//    @DeleteMapping("/{id}")
//    public Result<Boolean> remove(@PathVariable Long id) {
//        return Result.ok(userService.removeById(id));
//    }
//
//    /** 更新：PUT /api/users */
//    @PutMapping
//    public Result<Boolean> update(@RequestBody User user) {
//        return Result.ok(userService.updateById(user));
//    }
//
//    /** 按 id 查询：GET /api/users/1 */
//    @GetMapping("/{id}")
//    public Result<User> getById(@PathVariable Long id) {
//        return Result.ok(userService.getById(id));
//    }
//
//    /** 查询全部：GET /api/users */
//    @GetMapping
//    public Result<List<User>> list() {
//        return Result.ok(userService.list());
//    }
//
//    /** 条件查询：GET /api/users/search?name=张&age=20 */
//    @GetMapping("/search")
//    public Result<List<User>> search(@RequestParam(required = false) String name,
//                                     @RequestParam(required = false) Integer age) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.like(name != null && !name.isEmpty(), User::getName, name)
//               .eq(age != null, User::getAge, age);
//        return Result.ok(userService.list(wrapper));
//    }
//
//    /** 分页查询：GET /api/users/page?pageNum=1&pageSize=10 */
//    @GetMapping("/page")
//    public Result<Page<User>> page(@RequestParam(defaultValue = "1") long pageNum,
//                                   @RequestParam(defaultValue = "10") long pageSize) {
//        return Result.ok(userService.page(new Page<>(pageNum, pageSize)));
//    }
//
//    @PostMapping("/search-post")
//    public Result<List<User>> searchPost(@RequestBody User user) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(user != null && user.getAge() != null, User::getAge, user.getAge());
//        return Result.ok(userService.list(wrapper));
//    }
//    @PostMapping("/search-medical-record")
//    public Result<List<MedicineRecordDTO>> searchMedicalRecord(@RequestBody Medicine medicine) {
//        return Result.ok(medicalRecordService.listMedicineRecords(medicine.getUserId(),medicine.getMedicineName()));
//    }

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
    @DeleteMapping("/delete-medical-record")
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
}
