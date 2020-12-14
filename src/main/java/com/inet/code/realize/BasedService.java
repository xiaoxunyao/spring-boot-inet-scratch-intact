package com.inet.code.realize;

import com.inet.code.entity.user.dto.UserLandingDomain;
import com.inet.code.entity.user.dto.UserLoginDomain;
import com.inet.code.utils.Result;

/**
 * 基础的业务操作
 * @author HCY
 * @since 2020/12/11 下午 06:24
*/
public interface BasedService {
    /**
     * 登录操作
     *
     * @author HCY
     * @since 2020/12/11 下午 06:33
     * @param userLoginDomain: 登录的实体类(含有账号和密码)
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getLogin(UserLoginDomain userLoginDomain, String path);

    /**
     * 登出操作
     *
     * @author HCY
     * @since 2020/12/11 下午 07:58
     * @param token: 令牌
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getLogout(String token, String path);

    /**
     * 查看所有的类别
     *
     * @author HCY
     * @since 2020/12/11 下午 08:02
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getListType(String path);

    /**
     * 查看所有的标签
     *
     * @author HCY
     * @since 2020/12/11 下午 08:49
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getListLabel(String path);

    /**
     * 通过邮箱发送验证码进行登陆操作
     *
     * @author HCY
     * @since 2020/12/14 4:07 下午
     * @param email: 邮箱
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getVerificationLanding(String email, String path);

    /**
     * 通过验证码登陆
     *
     * @author HCY
     * @since 2020/12/14 5:11 下午
     * @param userLandingDomain: 验证码登陆的实体类
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getLanding(UserLandingDomain userLandingDomain, String path);

    /**
     * 分页查看轮播图
     *
     * @author HCY
     * @since 2020/12/14 9:46 下午
     * @param current: 页数
     * @param total: 条目数
     * @param path: URL路径
     * @return com.inet.code.utils.Result
    */
    Result getSlideshowPagination(Integer current, Integer total, String path);
}
