package com.ecomarketspa.EcoMarketSPA.Service.email;

import com.ecomarketspa.EcoMarketSPA.Dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.email}")
    public void consume(EmailDto emailDto) {
        log.info("Mensaje recibido -> {}", emailDto.getTo());
        try {
            emailService.sendEmail(emailDto);
        } catch (Exception e) {
            log.error("Error al procesar el email: {}", e.getMessage());
        }
    }
}