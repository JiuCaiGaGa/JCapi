package com.jcgg.project.job;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.model.entity.UserInterfaceInfo;
import com.jcgg.project.mapper.UserInterfaceInfoMapper;
import com.jcgg.project.model.vo.InterfaceInfoVO;
import com.jcgg.project.service.InterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;



@Component
public class InterfaceStatisticsTask {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoService interfaceInfoService;

    // 用于存放 定时器查询的接口调用Top3的结果
    private static final Map<Long, InterfaceInfoVO> CACHEINTERFACEVOMAP = new ConcurrentHashMap<>();
    @Scheduled(cron = "0 0 */3 * * ?")// 每3小时执行一次 将TopInterfaceInfo 读入缓存
//    @Scheduled(cron = "*/10 * * * * ?")// 每10秒执行一次 将TopInterfaceInfo 读入缓存 用于测试
    public void calculateTopInvokeInterfaceInfo(){
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
                InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
                BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
                int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
                interfaceInfoVO.setTotalNum(totalNum);
                return interfaceInfoVO;
            }).collect(Collectors.toList());
            // 更新缓存
            CACHEINTERFACEVOMAP.clear();
            interfaceInfoVOList.forEach(vo -> CACHEINTERFACEVOMAP.put(vo.getId(),vo));
        }
    }
    public static Map<Long,InterfaceInfoVO> getCacheinterfacevomap(){
        return CACHEINTERFACEVOMAP;
    }
}
