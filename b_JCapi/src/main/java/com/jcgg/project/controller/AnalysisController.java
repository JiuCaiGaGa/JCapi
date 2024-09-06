package com.jcgg.project.controller;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jcgg.model.entity.InterfaceInfo;
import com.jcgg.model.entity.UserInterfaceInfo;
import com.jcgg.project.annotation.AuthCheck;
import com.jcgg.project.common.BaseResponse;
import com.jcgg.project.common.ErrorCode;
import com.jcgg.project.common.ResultUtils;
import com.jcgg.project.exception.BusinessException;
import com.jcgg.project.job.InterfaceStatisticsTask;
import com.jcgg.project.mapper.UserInterfaceInfoMapper;
import com.jcgg.project.model.vo.InterfaceInfoVO;
import com.jcgg.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {

        // 先拿缓存的结果
        Map<Long, InterfaceInfoVO> cacheMap = InterfaceStatisticsTask.getCacheinterfacevomap();
        if(cacheMap.isEmpty()){
            List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
            Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                    .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

            QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
            List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
                InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
                BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
                int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
                interfaceInfoVO.setTotalNum(totalNum);
                return interfaceInfoVO;
            }).collect(Collectors.toList());
            return ResultUtils.success(interfaceInfoVOList);
        }
        List<InterfaceInfoVO> interfaceInfoVOList = new ArrayList<>(cacheMap.values());
        // 可以再次对书记进行处理
        // 演示根据 totalNum 降序排序
//        interfaceInfoVOList.sort(Comparator.comparingInt(InterfaceInfoVO::getTotalNum).reversed());

        return ResultUtils.success(interfaceInfoVOList);
    }

}
