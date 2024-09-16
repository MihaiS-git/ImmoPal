package ubb.graduation24.immopal.person_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.appointment.direct.exchange}")
    private String appointmentDirectExchangeName;

    @Value("${rabbitmq.bid.exchange}")
    private String bidDirectExchangeName;

    @Value("${rabbitmq.person.new.appointment.routingkey}")
    private String personNewAppointmentRoutingKey;

    @Value("${rabbitmq.person.delete.appointment.routingkey}")
    private String personDeleteAppointmentRoutingKey;

    @Value("${rabbitmq.person.bid.routingkey}")
    private String personBidRoutingKey;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private Integer port;

    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;

    @Value("${rabbitmq.reply.timeout}")
    private Integer replyTimeout;

    @Value("${rabbitmq.concurrent.consumers}")
    private Integer concurrentConsumers;

    @Value("${rabbitmq.max.concurrent.consumers}")
    private Integer maxConcurrentConsumers;


    @Bean
    public Queue personNewAppointmentDirectQueue(){
        return new Queue("person.new.appointment.queue");
    }

    @Bean
    public Queue personDeleteAppointmentDirectQueue(){
        return new Queue("person.delete.appointment.queue");
    }

    @Bean
    public Queue personBidDirectQueue(){
        return new Queue("person.bid.queue");
    }

    @Bean
    public DirectExchange appointmentDirectExchange() {
        return new DirectExchange(appointmentDirectExchangeName, false, false);
    }

    @Bean
    public DirectExchange bidDirectExchange() {
        return new DirectExchange(bidDirectExchangeName, false, false);
    }

    @Bean
    public Binding personNewAppointmentDirectBinding(DirectExchange appointmentDirectExchange) {
        return BindingBuilder.bind(personNewAppointmentDirectQueue())
                .to(appointmentDirectExchange)
                .with(personNewAppointmentRoutingKey);
    }

    @Bean
    public Binding personDeleteAppointmentDirectBinding(DirectExchange appointmentDirectExchange) {
        return BindingBuilder.bind(personDeleteAppointmentDirectQueue())
                .to(appointmentDirectExchange)
                .with(personDeleteAppointmentRoutingKey);
    }

    @Bean
    public Binding personBidDirectBinding(DirectExchange bidDirectExchange) {
        return BindingBuilder.bind(personBidDirectQueue()).to(bidDirectExchange).with(personBidRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
    }

    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        private final Logger logger = LogManager.getLogger(getClass());

        @Override
        public boolean isFatal(Throwable t) {
            if (t instanceof ListenerExecutionFailedException) {
                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
                logger.error("Failed to process inbound message from queue "
                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                        + "; failed message: " + lefe.getFailedMessage(), t);
            }
            return super.isFatal(t);
        }
    }
}
