package com.inet.code.realize.Impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inet.code.entity.slideshow.po.Slideshow;
import com.inet.code.entity.slideshow.vo.SlideshowBaseView;
import com.inet.code.entity.user.dto.UserBaseDomain;
import com.inet.code.entity.user.dto.UserLandingDomain;
import com.inet.code.entity.user.dto.UserLoginDomain;
import com.inet.code.entity.label.vo.LabelBaseView;
import com.inet.code.entity.type.vo.TypeBaseView;
import com.inet.code.entity.user.po.User;
import com.inet.code.mapper.UserMapper;
import com.inet.code.realize.BasedService;
import com.inet.code.service.LabelService;
import com.inet.code.service.SlideshowService;
import com.inet.code.service.TypeService;
import com.inet.code.service.UserService;
import com.inet.code.utils.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基础模块的操作
 *
 * @author HCY
 * @since 2020/12/11 下午 06:25
*/
@Service
public class BasedServiceImpl implements BasedService {

    @Resource
    private UserService userService;

    @Resource
    private TypeService typeService;

    @Resource
    private LabelService labelService;

    @Resource
    private SlideshowService slideshowService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录操作
     *
     * @author HCY
     * @since 2020/12/11 下午 06:33
     * @param userLoginDomain: 登录的实体类(含有账号和密码)
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getLogin(UserLoginDomain userLoginDomain, String path) {
        //将密码进行加密处理,在进行登录的查找
        UserBaseDomain userBaseDomain = userService.getLogin(
                  userLoginDomain.getAccount()
                , DigestUtil.md5Hex(userLoginDomain.getPassword()));
        //账号或者密码产生了错误
        if (userBaseDomain == null){
            return new Result().result404("您的账号或者密码错误",path);
        }
        //设置产生token的条件
        Map<String, String> map = new HashMap<>(2);
        map.put("userName",userBaseDomain.getUserName());
        map.put("roleName",userBaseDomain.getRoleName());
        //产生token
        String token = JwtUtils.getToken(map);
        //存入Redis,存储时间为7天
        redisTemplate.opsForValue().set(token,userBaseDomain,7, TimeUnit.DAYS);
        //设置返回值
        Map<String, Object> results = new HashMap<>(3);
        results.put("info","登录成功");
        results.put("token",token);
        results.put("user",userBaseDomain);
        return new Result().result200(results,path);
    }

    /**
     * 登出操作
     *
     * @author HCY
     * @since 2020/12/11 下午 07:58
     * @param token: 令牌
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getLogout(String token, String path) {
        if (redisTemplate.delete(token)){
            return new Result().result200(
                    "退出成功"
                    ,path);
        }
        return new Result().result500(
                "退出失败"
                ,path);
    }

    /**
     * 查看所有的类别
     *
     * @author HCY
     * @since 2020/12/11 下午 08:02
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getListType(String path) {
        return new Result().result200(
                 CloneUtil.batchClone(typeService.list(),TypeBaseView.class)
                ,path);
    }

    /**
     * 查看所有的标签
     *
     * @author HCY
     * @since 2020/12/11 下午 08:49
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getListLabel(String path) {
        return new Result().result200(
                 CloneUtil.batchClone(labelService.list(), LabelBaseView.class)
                ,path);
    }

    /**
     * 通过邮箱发送验证码进行登陆操作
     *
     * @author HCY
     * @since 2020/12/14 4:07 下午
     * @param email: 邮箱
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getVerificationLanding(String email, String path) {
        //判断邮箱是否合法
        if (!Validator.isEmail(email)){
            return new Result().result401("邮箱不合法，无法进行发送验证码",path);
        }
        //产生验证码
        String code = RandomUtil.randomString(5);
        //发送邮件
        MailUtil.send(
                  FromMailUtil.getMail()
                , email
                , "博语编程社区验证码"
                , "登陆验证码为:" + code + ",有效时长为5分钟"
                , false);
        //将验证码存入Redis
        redisTemplate.opsForValue().set(
                email
                ,code
                ,60 * 5
                , TimeUnit.SECONDS);
        //递交返回值
        return new Result().result200("验证码发送成功",path);
    }

    /**
     * 通过验证码登陆
     *
     * @author HCY
     * @since 2020/12/14 5:11 下午
     * @param userLandingDomain: 验证码登陆的实体类
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getLanding(UserLandingDomain userLandingDomain, String path) {
        //通过邮箱获取验证码
        String verification = redisTemplate.opsForValue().get(userLandingDomain.getEmail()) == null ? "" : (String) redisTemplate.opsForValue().get(userLandingDomain.getEmail());
        //判断验证码是否正确
        if(!userLandingDomain.getCode().equals(verification)){
            return new Result().result403("验证码错误，无法进行登陆",path);
        }
        UserBaseDomain userBaseDomain = userService.getByRoleEmail(userLandingDomain.getEmail());
        //设置产生token的条件
        Map<String, String> map = new HashMap<>(2);
        map.put("userName",userBaseDomain.getUserName());
        map.put("roleName",userBaseDomain.getRoleName());
        //产生token
        String token = JwtUtils.getToken(map);
        //存入Redis
        redisTemplate.opsForValue().set(
                 token
                ,userBaseDomain
                ,7
                , TimeUnit.DAYS);
        //设置返回值
        Map<String, Object> results = new HashMap<>(3);
        results.put("info","登录成功");
        results.put("token",token);
        results.put("user",userBaseDomain);
        return new Result().result200(results,path);
    }

    /**
     * 文件上传，返回的是文件的URL地址
     *
     * @author HCY
     * @since 2020/12/14 1:33 下午
     * @param file: 上传的文件
     * @param path: URL路径
     * @return com.inet.code.utils.Result
     */
    @Override
    public Result getUploadFiles(MultipartFile file, String path) {
        return FileUtils.getUploading(file,path);
    }


}
