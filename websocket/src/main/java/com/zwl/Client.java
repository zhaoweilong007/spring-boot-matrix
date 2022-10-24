package com.zwl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/10/14 16:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String UserName;

    private Session session;
}
