package io.spin.status.config;

import io.lettuce.core.ReadFrom;
import io.spin.status.util.redis.MessagePublisher;
import io.spin.status.util.redis.impl.RedisMessagePublisher;
import io.spin.status.util.redis.impl.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.passwd}")
    private String redisPasswd;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Value("${spring.redis.cluster.nodes}")
    private String redisClusterNodes;

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RedisConfig(
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        LettuceConnectionFactory lettuceConnectionFactory = null;

        if( redisClusterNodes != null )
        {
            // cluster를 사용할 경우
            LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                    //.readFrom(ReadFrom.SLAVE_PREFERRED)
                    .readFrom(ReadFrom.REPLICA_PREFERRED)
                    .build();

            // RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(Arrays.asList( redisClusterNodes.split(",") ));

            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            redisClusterConfiguration.clusterNode(redisClusterNodes, redisPort);

            // redisClusterConfiguration.setPassword(redisPasswd); // aws 에서는 passwd 를 사용하지 않음. 이미 VPC -> private 안에 들어가 있으므로.


            lettuceConnectionFactory =  new LettuceConnectionFactory(redisClusterConfiguration , clientConfig);
        } // end if( redisHosts != null )

        else
        {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(redisHost);
            redisStandaloneConfiguration.setPort(redisPort);
            redisStandaloneConfiguration.setPassword(redisPasswd);

            lettuceConnectionFactory =  new LettuceConnectionFactory(redisStandaloneConfiguration);
        } // end else

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(
                new RedisMessageSubscriber(
                        redisTemplate(),
                        simpMessagingTemplate
                )
        );
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("messageQueue");
    }

    @Bean
    public MessagePublisher messagePublisher() {
        return new RedisMessagePublisher(redisTemplate(), channelTopic());
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter(), channelTopic());
        return redisMessageListenerContainer;
    }

}
