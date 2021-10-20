package io.shulie.takin.web.biz.service.dsManage.impl.v2;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.pamirs.attach.plugin.dynamic.Converter;
import io.shulie.takin.web.biz.convert.db.parser.RedisTemplateParser;
import io.shulie.takin.web.biz.convert.db.parser.TemplateParser;
import io.shulie.takin.web.biz.pojo.input.application.ApplicationDsCreateInputV2;
import io.shulie.takin.web.biz.pojo.input.application.ApplicationDsUpdateInputV2;
import io.shulie.takin.web.biz.service.dsManage.AbstractShaDowManageService;
import io.shulie.takin.web.data.dao.application.ApplicationDsCacheManageDAO;
import io.shulie.takin.web.data.model.mysql.ApplicationDsCacheManageEntity;
import io.shulie.takin.web.ext.util.WebPluginUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 南风
 * @Date: 2021/9/2 5:51 下午
 */
@Service
public class ShaDowCacheServiceImpl extends AbstractShaDowManageService {

    @Autowired
    private ApplicationDsCacheManageDAO cacheManageDAO;

    @Autowired
    private RedisTemplateParser templateParser;

    /**
     * 更新影子配置方案
     *
     * @param inputV2
     */
    @Override
    public void updateShadowProgramme(ApplicationDsUpdateInputV2 inputV2) {
        ApplicationDsCacheManageEntity entity = this.buildEntity(inputV2,false);
        cacheManageDAO.updateById(inputV2.getId(),entity);
    }

    /**
     * 创建影子配置方案
     *
     * @param inputV2
     */
    @Override
    public void createShadowProgramme(ApplicationDsCreateInputV2 inputV2) {

        ApplicationDsCacheManageEntity entity = this.buildEntity(inputV2,true);
        entity.setId(null);//去除原id
        cacheManageDAO.saveOne(entity);
    }

    private ApplicationDsCacheManageEntity buildEntity(ApplicationDsCreateInputV2 inputV2,Boolean isCreate){
        if(!JSONUtil.isJson(inputV2.getExtInfo())){
            throw new IllegalArgumentException("影子配置数据必须是Json!");
        }
        WebPluginUtils.fillUserData(inputV2);
        ApplicationDsCacheManageEntity entity = new  ApplicationDsCacheManageEntity();
        entity.setDsType(inputV2.getDsType());
        entity.setShaDowFileExtedn(inputV2.getExtInfo());
        entity.setCustomerId(inputV2.getCustomerId());
        entity.setUserId(inputV2.getUserId());
        if(isCreate){
            entity.setApplicationId(inputV2.getApplicationId());
            entity.setApplicationName(inputV2.getApplicationName());
            entity.setCacheName(inputV2.getConnectionPool());
            entity.setColony(inputV2.getUrl());
            entity.setUserName(inputV2.getUsername());
            entity.setPwd("");
            entity.setType(inputV2.getCacheType());
            entity.setDsType(inputV2.getDsType());
            entity.setFileExtedn("");
            entity.setConfigJson("");
            entity.setSource(1);
            Converter.TemplateConverter.TemplateEnum templateEnum = templateParser.convert(entity.getCacheName());
            entity.setAgentSourceType(templateEnum.getKey());
        }
        return entity;
    }

}
