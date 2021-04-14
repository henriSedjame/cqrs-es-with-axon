package io.fleet.reservationservice.configuration

import com.rabbitmq.client.Channel
import io.fleet.coremq.BookingEventProcessor
import io.fleet.coremq.CarEventProcessor
import org.axonframework.extensions.amqp.eventhandling.DefaultAMQPMessageConverter
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource
import org.axonframework.serialization.Serializer
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MqConfiguration {

    @Bean("carMessageSource")
    fun bookingMessageSource(serializer: Serializer) : SpringAMQPMessageSource {
        val converter = DefaultAMQPMessageConverter.builder().serializer(serializer).build()

        return object : SpringAMQPMessageSource(converter) {

            @RabbitListener(queues = [CarEventProcessor.QUEUE])
            override fun onMessage(message: Message?, channel: Channel?) {
                super.onMessage(message, channel)
            }
        }
    }
}
