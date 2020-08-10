package org.shf.service;

import org.shf.model.po.Area;
import org.shf.model.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Boolean selectUserAccount(String account);

    void addUser(User user);

    List<Area> selectArea();

    Map selectUser();

    String selectPwdByAccount(String account);

    User selectUserById(Integer id);

    void deleteById(Integer id);
}
