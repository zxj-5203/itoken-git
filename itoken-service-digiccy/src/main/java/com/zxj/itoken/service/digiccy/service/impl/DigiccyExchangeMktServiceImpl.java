package com.zxj.itoken.service.digiccy.service.impl;

import com.zxj.itoken.common.domain.TbDigiccyExchangeMkt;
import com.zxj.itoken.common.mapper.TbDigiccyExchangeMktMapper;
import com.zxj.itoken.common.service.impl.BaseServiceImpl;
import com.zxj.itoken.service.digiccy.service.DigiccyExchangeMktService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DigiccyExchangeMktServiceImpl extends BaseServiceImpl<TbDigiccyExchangeMkt, TbDigiccyExchangeMktMapper> implements DigiccyExchangeMktService {
}
