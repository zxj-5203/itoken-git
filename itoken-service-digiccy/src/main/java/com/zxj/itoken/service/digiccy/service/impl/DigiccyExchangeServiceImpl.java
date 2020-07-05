package com.zxj.itoken.service.digiccy.service.impl;

import com.zxj.itoken.common.domain.TbDigiccyExchange;
import com.zxj.itoken.common.service.impl.BaseServiceImpl;
import com.zxj.itoken.service.digiccy.mapper.TbDigiccyExchangeExtendMapper;
import com.zxj.itoken.service.digiccy.service.DigiccyExchangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DigiccyExchangeServiceImpl extends BaseServiceImpl<TbDigiccyExchange, TbDigiccyExchangeExtendMapper> implements DigiccyExchangeService {
}
