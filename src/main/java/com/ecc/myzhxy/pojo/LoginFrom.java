package com.ecc.myzhxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author sunyc
 * @create 2022-06-13 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class LoginFrom {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}