package com.xangqun.springcloud.component.base.id;

import java.io.Serializable;

/**
 * ID生成器接口
 */
public interface IdGenerator {
    
    /**
     * 生成long型ID
     * @return long
     */
    Serializable generateId();

}
