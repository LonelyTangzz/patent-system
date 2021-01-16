package com.tang.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/8.
 * @name: UserInfoVo
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {

    private Integer id;

    private String username;

}
