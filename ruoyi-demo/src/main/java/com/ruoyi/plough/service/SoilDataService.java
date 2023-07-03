package com.ruoyi.plough.service;

import com.ruoyi.plough.domain.SoilData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.project.common.BaseResponse;

import java.util.List;

/**
* @author 26010
* @description 针对表【soil_data】的数据库操作Service
* @createDate 2023-07-01 13:45:54
*/
public interface SoilDataService extends IService<SoilData> {

    List<SoilData> listAllSoilData();

    List<SoilData> searchSoilList(String[] areaList, String[] typeList);

    List<SoilData> searchSoilByName(String soilName);
}
