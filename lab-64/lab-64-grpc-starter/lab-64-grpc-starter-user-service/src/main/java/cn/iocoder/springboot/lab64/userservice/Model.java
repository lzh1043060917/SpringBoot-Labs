package cn.iocoder.springboot.lab64.userservice;

import java.io.Serializable;

import lombok.Data;


@Data
public class Model implements Serializable {
    private static final long serialVersionUID = -5426941834241865946L;

    private Integer id;

    private String name;

    private Integer gender;
}
