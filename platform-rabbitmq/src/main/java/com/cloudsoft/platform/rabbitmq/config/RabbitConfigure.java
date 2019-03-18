package com.cloudsoft.platform.rabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhujianrong
 * @date 2018-09-28 10:04
 */
@Configuration
public class RabbitConfigure {

    // 队列名称(支付)
    public final static String PAY_QUEUE = "pay-queue-intelligence";
    // 交换机名称(支付)
    public final static String PAY_EXCHANGE = "pay-exchange-intelligence";
    // 绑定的值(支付)
    public static final String PAY_BIND_KEY = "pay-bind-key-intelligence";
    // 队列名称(用户)
    public final static String USER_QUEUE = "user-queue-intelligence";
    // 交换机名称(用户)
    public final static String USER_EXCHANGE = "user-exchange-intelligence";
    // 绑定的值(用户)
    public static final String USER_BIND_KEY = "user-bind-key-intelligence";
    // 队列名称(商户)
    public final static String MERCHANT_QUEUE = "merchant-queue-intelligence";
    // 交换机名称(商户)
    public final static String MERCHANT_EXCHANGE = "merchant-exchange-intelligence";
    // 绑定的值(商户)
    public static final String MERCHANT_BIND_KEY = "merchant-bind-key-intelligence";
    // 队列名称(第三方)
    public final static String THIRDPARTY_QUEUE = "thirdparty-queue-intelligence";
    // 交换机名称(第三方)
    public final static String THIRDPARTY_EXCHANGE = "thirdparty-exchange-intelligence";
    // 绑定的值(第三方)
    public static final String THIRDPARTY_BIND_KEY = "thirdparty-bind-key-intelligence";
    // 队列名称(物品渠道)
    public final static String GOODS_QUEUE = "goods-queue-intelligence";
    // 交换机名称(物品渠道)
    public final static String GOODS_EXCHANGE = "goods-exchange-intelligence";
    // 绑定的值(物品渠道)
    public static final String GOODS_BIND_KEY = "goods-bind-key-intelligence";


    // 队列名称(业务渠道1)
    public final static String BUSINESS_ONE_QUEUE = "business-one-queue-intelligence";
    // 绑定的值(业务渠道1)
    public static final String BUSINESS_ONE_BIND_KEY = "business-one-bind-key-intelligence";
    // 队列名称(业务渠道2)
    public final static String BUSINESS_TWO_QUEUE = "business-two-queue-intelligence";
    // 绑定的值(业务渠道2)
    public static final String BUSINESS_TWO_BIND_KEY = "business-two-bind-key-intelligence";
    // 队列名称(业务渠道3)
    public final static String BUSINESS_THREE_QUEUE = "business-three-queue-intelligence";
    // 绑定的值(业务渠道3)
    public static final String BUSINESS_THREE_BIND_KEY = "business-three-bind-key-intelligence";
    // 交换机名称(业务渠道)
    public final static String BUSINESS_EXCHANGE = "business-exchange-intelligence";



    //支付专用队列
    @Bean
    Queue payQueueIntelligence() {
        return new Queue(PAY_QUEUE, true);
    }
    @Bean
    TopicExchange payExchangeIntelligence() {
        return new TopicExchange(PAY_EXCHANGE);
    }
    @Bean
    Binding payBindingIntelligence() {
        return BindingBuilder.bind(payQueueIntelligence()).to(payExchangeIntelligence()).with(PAY_BIND_KEY);
    }



    //用户专用队列
    @Bean
    Queue userQueueIntelligence() {
        return new Queue(USER_QUEUE, true);
    }
    @Bean
    TopicExchange userExchangeIntelligence() {
        return new TopicExchange(USER_EXCHANGE);
    }
    @Bean
    Binding userBindingIntelligence() {
        return BindingBuilder.bind(userQueueIntelligence()).to(userExchangeIntelligence()).with(USER_BIND_KEY);
    }




    //商户专用队列
    @Bean
    Queue merchantQueueIntelligence() {
        return new Queue(MERCHANT_QUEUE, true);
    }
    @Bean
    TopicExchange merchantExchangeIntelligence() {
        return new TopicExchange(MERCHANT_EXCHANGE);
    }
    @Bean
    Binding merchantBindingIntelligence() {
        return BindingBuilder.bind(merchantQueueIntelligence()).to(merchantExchangeIntelligence()).with(MERCHANT_BIND_KEY);
    }



    //第三方专用队列
    @Bean
    Queue thirdpartyQueueIntelligence() {
        return new Queue(THIRDPARTY_QUEUE, true);
    }
    @Bean
    TopicExchange thirdpartyExchangeIntelligence() {
        return new TopicExchange(THIRDPARTY_EXCHANGE);
    }
    @Bean
    Binding thirdpartyBindingIntelligence() {
        return BindingBuilder.bind(thirdpartyQueueIntelligence()).to(thirdpartyExchangeIntelligence()).with(THIRDPARTY_BIND_KEY);
    }





    //物品专用队列
    @Bean
    Queue goodsQueueIntelligence() {
        return new Queue(GOODS_QUEUE, true);
    }
    @Bean
    TopicExchange goodsExchangeIntelligence() {
        return new TopicExchange(GOODS_EXCHANGE);
    }
    @Bean
    Binding goodsBindingIntelligence() {
        return BindingBuilder.bind(thirdpartyQueueIntelligence()).to(thirdpartyExchangeIntelligence()).with(GOODS_BIND_KEY);
    }





    //业务交换器
    @Bean
    TopicExchange businessExchangeIntelligence() {
        return new TopicExchange(BUSINESS_EXCHANGE);
    }
    //业务1
    @Bean
    Queue businessOneQueueIntelligence() {
        return new Queue(BUSINESS_ONE_QUEUE, true);
    }
    @Bean
    Binding businessOneBindingIntelligence() {
        return BindingBuilder.bind(businessOneQueueIntelligence()).to(businessExchangeIntelligence()).with(BUSINESS_ONE_BIND_KEY);
    }
    //业务2
    @Bean
    Queue businessTwoQueueIntelligence() {
        return new Queue(BUSINESS_TWO_QUEUE, true);
    }
    @Bean
    Binding businessTwoBindingIntelligence() {
        return BindingBuilder.bind(businessTwoQueueIntelligence()).to(businessExchangeIntelligence()).with(BUSINESS_TWO_BIND_KEY);
    }
    //业务3
    @Bean
    Queue businessThreeQueueIntelligence() {
        return new Queue(BUSINESS_THREE_QUEUE, true);
    }
    @Bean
    Binding businessThreeBindingIntelligence() {
        return BindingBuilder.bind(businessThreeQueueIntelligence()).to(businessExchangeIntelligence()).with(BUSINESS_THREE_BIND_KEY);
    }




}
