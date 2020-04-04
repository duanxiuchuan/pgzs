package cn.com.common.shiro;

import cn.com.entity.admin.Admin;
import cn.com.entity.admin.Resources;
import cn.com.service.admin.AdminService;
import cn.com.service.admin.ResourcesService;
import cn.com.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author LiDaDa
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;

    @Resource
    private ResourcesService resourcesService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object object = SecurityUtils.getSubject().getPrincipal();
        Admin admin = new Admin();
        if(object instanceof Admin) {
            admin = (Admin) object;
        } else {
            admin = JSON.parseObject(JSON.toJSON(object).toString(), Admin.class);
        }
        List<Resources> resourcesList = resourcesService.loadAdminResources(admin.getId());
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Resources resources : resourcesList) {
            if (StringUtils.isNotEmpty(resources.getUrl())) {
                info.addStringPermission(resources.getUrl());
            }
        }
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号.
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = String.valueOf(token.getUsername());
        String password = new String((char[]) token.getCredentials());
        String md5Pwd = new Md5Hash(password, username).toHex();
        Admin admin = adminService.findByUserName(username);
        if (admin == null) {
            throw new UnknownAccountException();
        }
        if (1 == admin.getIsEnable()) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        // 获取盐值，即用户名
        ByteSource salt = ByteSource.Util.bytes(username);
        if (Objects.equals(admin.getPassword(), md5Pwd)) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    //用户
                    admin,
                    //密码
                    md5Pwd,
                    salt,
                    getName()
            );
            // 当验证都通过后，把用户信息放在session里
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userSession", admin);
            session.setAttribute("userSessionId", admin.getId());
            return authenticationInfo;
        } else {
            throw new AuthenticationException("密码错误");
        }
    }


    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     *
     * @param userIds 已经修改了权限的userId
     */
    public void clearUserAuthByUserId(List<Long> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return;
        }
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                Admin admin = new Admin();
                if (null != obj) {
                    if(obj instanceof Admin) {
                        admin = (Admin) obj;
                    } else {
                        admin = JSON.parseObject(JSON.toJSON(obj).toString(), Admin.class);
                    }

                    System.out.println("user:" + admin);
                    System.out.println("userIds" + userIds);
                    //比较用户ID，符合即加入集合
                    if (null != admin && userIds.contains(admin.getId())) {
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
