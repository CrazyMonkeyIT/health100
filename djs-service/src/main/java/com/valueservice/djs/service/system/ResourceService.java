package com.valueservice.djs.service.system;

import com.valueservice.djs.db.dao.system.ResourcesDOMapper;
import com.valueservice.djs.db.entity.system.ResourcesDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ResourceService {

    @Resource
    private ResourcesDOMapper resourcesDOMapper;

    public List<ResourcesDO> selectByUserId(Long userId){
        return resourcesDOMapper.selectByUserId(userId);
    }

    public List<ResourcesDO> selectAll(){
        return resourcesDOMapper.selectAllActive();
    }

    public ResourcesDO selectByPrimaryKey(Long resourceId){
      return resourcesDOMapper.selectByPrimaryKey(resourceId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteResource(Long resourceId) {
        ResourcesDO record = new ResourcesDO();
        record.setResourceId(resourceId);
        record.setActive(0);
        resourcesDOMapper.updateByPrimaryKeySelective(record);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrUpdate(ResourcesDO record) {
        if(record.getResourceId() == null){
            record.setOrderNo(selectCurrOrderNo(record.getParentId()));
            record.setActive(1);
            record.setCreateTime(new Timestamp(System.currentTimeMillis()));
            resourcesDOMapper.insertSelective(record);
        }else{
            if(record.getParentId().equals(record.getResourceId())){
                record.setParentId(null);
            }
            record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            resourcesDOMapper.updateByPrimaryKeySelective(record);
        }
        return true;
    }

    /**
     * 查询当前资源节点序号
     * @param parentId
     * @return
     */
    public int selectCurrOrderNo(Long parentId) {
        Integer maxOrderNo = resourcesDOMapper.selectLasrOrderNo(parentId);
        //如果没有子菜单，就设置初始值1
        maxOrderNo = maxOrderNo == null?0:maxOrderNo;
        return (maxOrderNo + 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateResourcePosition(ResourcesDO resource, Long upId,Integer upOrder) {
        Integer order = resource.getOrderNo();
        resource.setOrderNo(upOrder);
        ResourcesDO r = new ResourcesDO();
        r.setOrderNo(order);
        r.setResourceId(upId);
        int updateSed = resourcesDOMapper.updateByPrimaryKeySelective(resource);
        int updateOne = resourcesDOMapper.updateByPrimaryKeySelective(r);
        if(updateSed > 0 && updateOne > 0){
            return true;
        }
        return false;
    }
}
