package com.ruoyi.plough.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.plough.domain.SoilData;
import com.ruoyi.plough.mapper.SoilDataMapper;
import com.ruoyi.plough.service.SoilDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 26010
* @description 针对表【soil_data】的数据库操作Service实现
* @createDate 2023-07-01 13:45:54
*/
@Service
public class SoilDataServiceImpl extends ServiceImpl<SoilDataMapper, SoilData>
    implements SoilDataService {
    @Autowired
    SoilDataMapper soilDataMapper;

    @Override
    public List<SoilData> listAllSoilData() {
        QueryWrapper<SoilData> wrapper = new QueryWrapper<>();
        return soilDataMapper.selectList(wrapper);
    }

    @Override
    public List<SoilData> searchSoilList(String[] areaList, String[] typeList) {
        int areaLength = areaList.length;
        int typeLength = typeList.length;

        LambdaQueryWrapper<SoilData> queryWrapper = new LambdaQueryWrapper<>();

        if (areaLength > 0) {
            for (int i = 0; i < areaLength - 1; i++) {
                queryWrapper.like(SoilData::getSoilLocation, areaList[i]).or();
            }
            queryWrapper.like(SoilData::getSoilLocation, areaList[areaLength - 1]);
        }

        if (typeLength > 0) {
            for (int i = 0; i < typeLength - 1; i++) {
                queryWrapper.like(SoilData::getSoilType, typeList[i]).or();
            }
            queryWrapper.like(SoilData::getSoilType, typeList[typeLength - 1]);
        }
        return soilDataMapper.selectList(queryWrapper);
    }

    @Override
    public List<SoilData> searchSoilByName(String soilName) {
       LambdaQueryWrapper<SoilData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SoilData::getSoilName, soilName);
        return soilDataMapper.selectList(queryWrapper);
    }
}




