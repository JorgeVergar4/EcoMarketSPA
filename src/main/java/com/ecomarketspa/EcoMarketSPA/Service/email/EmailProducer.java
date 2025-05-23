package com.ecomarketspa.EcoMarketSPA.Service.email;

import com.ecomarketspa.EcoMarketSPA.Dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;
    private final EmailService emailService;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate, EmailService emailService) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailService = emailService;
    }

    public void sendEmail(EmailDto emailDto) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, emailDto);
            log.info("Email enviado a la cola para: {}", emailDto.getTo());
        } catch (Exception e) {
            log.error("Error enviando email a la cola: {}", e.getMessage());
            throw new RuntimeException("Error en cola de emails", e);
        }
    }
}