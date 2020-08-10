package org.shf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shf.model.po.User;
import org.springframework.stereotype.Repository;
//声明dao层
@Repository
public interface UserDao extends BaseMapper<User> {


    String selectUserAccount(String account);

    String selectPwdByAccount(String account);
}
