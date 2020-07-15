package com.ioc.datasupport.component;

import com.ioc.datasupport.common.PublicConstant;
import com.openjava.admin.user.vo.OaUserVO;
import org.ljdp.component.exception.APIException;
import org.ljdp.secure.sso.SsoContext;
import org.springframework.stereotype.Component;

/**
 * @author: lsw
 * @Date: 2019/10/29 16:13
 */
@Component
public class UserComponent {

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    public OaUserVO getUserInfo() throws APIException {
        //获取用户信息
        OaUserVO user = (OaUserVO) SsoContext.getUser();
        if (user == null) {
            //为空，抛出异常
            throw new APIException(PublicConstant.CODE_SERVER_ERR, "获取当前用户信息失败");
        }

        return user;
    }
}
