package com.zzh.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * 只是在原有的密码基础上增加了"1"字符
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString()+"1";
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if ((rawPassword.toString()+"1").equals(encodedPassword)){
                return true;
            }
        }
        return false;
    }
}
