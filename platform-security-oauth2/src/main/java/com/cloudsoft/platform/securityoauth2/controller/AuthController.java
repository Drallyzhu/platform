package com.cloudsoft.platform.securityoauth2.controller;

import com.cloudsoft.common.controller.BaseController;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.Base64Utils;
import com.cloudsoft.platform.securityoauth2.domain.dto.JwtAuthRequestDTO;
import com.cloudsoft.platform.securityoauth2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController extends BaseController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public String createAuthenticationToken( @RequestBody JwtAuthRequestDTO authenticationRequest) {
            ResultData resul = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            if(ResultData.OK.equals(resul.getCode())){
                return getSuccessByData(resul);
            }else{
                return getErrorMsgInfo(resul);
            }
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public String refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        ResultData rd = authService.refresh(token);
        if(ResultData.OK.equals(rd.getCode())){
            return getSuccessByData(rd);
        }else{
            return getFailureInfo("刷新失败！");
        }
    }

}
