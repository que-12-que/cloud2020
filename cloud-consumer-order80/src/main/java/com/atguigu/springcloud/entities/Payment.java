package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 一个类只有实现了 Serializable 接口,它的对象才是可序列化的。
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
