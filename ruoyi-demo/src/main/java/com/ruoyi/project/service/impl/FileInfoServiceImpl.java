package com.ruoyi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.model.entity.FileInfo;
import com.ruoyi.project.service.FileInfoService;
import com.ruoyi.project.mapper.FileInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 26010
* @description 针对表【file_info】的数据库操作Service实现
* @createDate 2023-05-06 20:39:25
*/
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo>
    implements FileInfoService{
    @Resource
    FileInfoMapper fileInfoMapper;
    @Override
    public Boolean addActivityUrl(String activityId, String url, Long ossId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setActivityId(activityId);
        fileInfo.setUrl(url);
        fileInfo.setOssId(ossId);
        int insert = fileInfoMapper.insert(fileInfo);
        if(insert==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<FileInfo> searchActivityUrl(String activityId) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id" ,activityId);
        return fileInfoMapper.selectList(queryWrapper);

    }

    @Override
    public Boolean addProjectUrl(String projectId, String url, Long ossId) {
        FileInfo fileInfo = new FileInfo();
       fileInfo.setTeamId(projectId);
        fileInfo.setUrl(url);
        fileInfo.setOssId(ossId);
        int insert = fileInfoMapper.insert(fileInfo);
        if(insert==1){
            return true;
        }else{
            return false;
        }
    }
}




