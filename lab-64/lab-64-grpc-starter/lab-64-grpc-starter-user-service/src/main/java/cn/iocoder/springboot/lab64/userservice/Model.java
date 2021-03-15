package cn.iocoder.springboot.lab64.userservice;

import java.io.Serializable;

import lombok.Data;

/**
 * @author liangzihao03 <liangzihao03@kuaishou.com>
 * Created on 2021-03-15
 */
@Data
public class Model implements Serializable {
    private static final long serialVersionUID = -5426941834241865946L;

    private Integer id;

    private String name;

    private Integer gender;
}
