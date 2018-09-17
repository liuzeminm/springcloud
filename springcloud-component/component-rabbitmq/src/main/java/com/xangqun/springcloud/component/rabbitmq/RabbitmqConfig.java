/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author laixiangqun
 * @since 2018-8-1
 */

public class RabbitmqConfig {
    protected static Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
    /**
     * 实例化rabbitTemplate
     */
    @Bean(name = "rabbitTemplate")
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
        // 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
//        template.setMandatory(true);

        template.setMessageConverter(new Gson2JsonMessageConverter());
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        //发布确认
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            //消息发送到queue时就执行
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
//                   throw new RuntimeException("send error " + cause);
                }
            }
        });
        return template;
    }
}
