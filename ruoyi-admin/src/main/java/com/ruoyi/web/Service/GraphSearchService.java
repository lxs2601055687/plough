package com.ruoyi.web.Service;

import com.ruoyi.web.VO.NodeVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GraphSearchService {
    /**
     * 查询所有节点
     * @return
     */
  public  List<NodeVo>  selectRelatedNode(String name);


  public  List<NodeVo>  selectRelatedNodeBySop(String name);
}
