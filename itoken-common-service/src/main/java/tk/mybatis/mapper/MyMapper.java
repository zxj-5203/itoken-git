package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zxj
 * @date 2020/06/16
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}