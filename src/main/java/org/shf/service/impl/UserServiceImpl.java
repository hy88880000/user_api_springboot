package org.shf.service.impl;

import org.shf.dao.AreaDao;
import org.shf.dao.UserDao;
import org.shf.model.po.Area;
import org.shf.model.po.User;
import org.shf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//声明事物
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @Override
    public Boolean selectUserAccount(String account) {
        String ac = userDao.selectUserAccount(account);
        Boolean result;
        if (ac != null){
            result = false;
        }else {
            result = true;
        }
        return result;
    }

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }

    @Override
    public List<Area> selectArea() {
        return areaDao.selectList(null);
    }

    @Override
    public Map selectUser() {
        Map map = new HashMap();
        List<User> list = userDao.selectList(null);

        String areaName;
        for (int i = 0; i < list.size(); i++) {
            areaName = areaDao.selectAreaName(list.get(i).getAreaId());
            list.get(i).setAreaName(areaName);
        }
        map.put("data",list);
        return map;
    }

    @Override
    public String selectPwdByAccount(String account) {
        String pw = userDao.selectPwdByAccount(account);
        return pw;
    }

    @Override
    public User selectUserById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }
}
