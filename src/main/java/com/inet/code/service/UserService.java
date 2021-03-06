package com.inet.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.code.entity.user.dto.UserBaseDomain;
import com.inet.code.entity.user.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inet.code.entity.user.vo.UserFanView;
import com.inet.code.entity.user.vo.UserFiveLikeView;
import com.inet.code.entity.user.vo.UserSearchView;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户的基本信息 服务类
 * </p>
 *
 * @author HCY
 * @since 2020-12-11
 */
public interface UserService extends IService<User> {

    /**
     * 登录操作
     *
     * @author HCY
     * @since 2020/12/12 上午 10:54
     * @param account: 账号
     * @param password: 密码
     * @return com.inet.code.entity.user.dto.UserBaseDomain
    */
    UserBaseDomain getLogin(String account, String password);

    /**
     * 查找该邮箱的用户
     *
     * @author HCY
     * @since 2020/12/12 下午 03:14
     * @param email: 邮箱
     * @return com.inet.code.entity.user.po.User
    */
    User getEmailRepeat(String email);

    /**
     * 查看关注的人
     *
     * @author HCY
     * @since 2020/12/13 下午 03:52
     * @param userEmail: 用户邮箱
     * @param pages: 页数
     * @return java.util.List<com.inet.code.entity.user.vo.UserFanView>
     */
    List<UserFanView> getCheckFocus(String userEmail, Integer pages);

    /**
     * 查看关注的人条目数
     *
     * @author HCY
     * @since 2020/12/13 下午 09:04
     * @param userEmail: 用户邮箱
     * @return java.lang.Integer
    */
    Integer getCheckFocusTotal(String userEmail);

    /**
     * 查看关注自己的用户
     *
     * @author HCY
     * @since 2020/12/13 下午 09:27
     * @param userEmail: 用户邮箱
     * @param pages: 页数
     * @return java.util.List<com.inet.code.entity.user.vo.UserFanView>
    */
    List<UserFanView> getCheckFans(String userEmail, Integer pages);

    /**
     * 查看关注自己的用户的条目数
     *
     * @author HCY
     * @since 2020/12/13 下午 09:32
     * @param userEmail: 用户邮箱
     * @return java.lang.Integer
    */
    Integer getCheckFansTotal(String userEmail);

    /**
     * 通过邮箱继续登陆操作
     * @author HCY
     * @since 2020/12/14 5:20 下午
     * @param email: 用户邮箱
     * @return com.inet.code.entity.user.dto.UserBaseDomain
    */
    UserBaseDomain getByRoleEmail(String email);

    /**
     * 该日期新增多少人数
     *
     * @author HCY
     * @since 2020/12/24 9:59 下午
     * @param day: 日期
     * @return java.lang.Integer
    */
    Integer getNewUsers(String day);

    /**
     * 查看点赞数最高的五个用户
     *
     * @author HCY
     * @since 2020/12/30 上午11:04
     * @return java.util.List<com.inet.code.entity.user.vo.UserFiveLikeView>
    */
    List<UserFiveLikeView> getListFiveUsers();

    /**
     * 访客项目，在访客模式下可以查看十个点赞数目多的用户
     *
     * @author HCY
     * @since 2020/12/30 下午3:51
     * @return java.util.List<com.inet.code.entity.user.vo.UserFiveLikeView>
    */
    List<UserFiveLikeView> getListTenUser();

    /**
     * 判断用户的账号是否重复
     *
     * @author HCY
     * @since 2021/1/8 下午1:13
     * @param userEmail: 账号
     * @return java.lang.Boolean
    */
    User getByEmail(String userEmail);

    /**
     * 分页查看用户
     *
     * @author HCY
     * @since 2021/1/8 下午2:34
     * @param userPage: 分页条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.inet.code.entity.user.po.User>
    */
    IPage<User> getPage(Page<User> userPage);

    /**
     * 通过用户名字搜索用户
     *
     * @author HCY
     * @since 2021/1/12 9:16 PM
     * @param userName: 用户名字
     * @return java.util.List<com.inet.code.entity.user.vo.UserSearchView>
    */
    List<UserSearchView> getSearchUser(String userName);
}
