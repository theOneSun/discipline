package com.sun.discipline.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunjian.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatUser
{
    private String id;
    private String userId;
    private String openId;

}

