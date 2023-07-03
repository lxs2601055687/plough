package com.ruoyi.plough.controller;

import com.ruoyi.plough.domain.SoilData;
import com.ruoyi.plough.dto.SoilSearchByNameRequest;
import com.ruoyi.plough.dto.SoilSearchRequest;
import com.ruoyi.plough.service.SoilDataService;
import com.ruoyi.project.common.BaseResponse;
import com.ruoyi.project.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SoilController {
    @Autowired
    SoilDataService soilDataService;
    @GetMapping("/list/soil")
    public BaseResponse<List<SoilData>> soilDataList () {
        List<SoilData> soilDataList = soilDataService.listAllSoilData();
        soilDataList.stream().forEach(soilData -> soilData.setDescription(soilData.getDescription() + soilData.getProdPerformance()));
        return ResultUtils.success(soilDataList);
    }

    @PostMapping("/search/soil")
    public BaseResponse<List<SoilData>> searchSoilList (@RequestBody SoilSearchRequest soilSearchRequest) {
        String[] areaList = soilSearchRequest.getAreaList();
        String[] typeList = soilSearchRequest.getTypeList();
        List<SoilData> soilDataList = soilDataService.searchSoilList(areaList, typeList);
       return ResultUtils.success(soilDataList);
    }
    @PostMapping("/soil/searchByName")
    public BaseResponse<List<SoilData>> searchSoilList (@RequestBody SoilSearchByNameRequest soilSearchByNameRequest) {
        String soilName = soilSearchByNameRequest.getSoilName();
        List<SoilData> soilDataList = soilDataService.searchSoilByName(soilName);
        return ResultUtils.success(soilDataList);
    }
}
