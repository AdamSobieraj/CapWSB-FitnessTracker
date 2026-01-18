package pl.wsb.fitnesstracker.mail.internal;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
public class MailConfig {

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost() != null ? mailProperties.getHost() : "localhost");
        mailSender.setPort(mailProperties.getPort() != null ? mailProperties.getPort() : 25);
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailProperties.getProperties().getOrDefault("mail.smtp.auth", "false"));
        props.put("mail.smtp.starttls.enable", mailProperties.getProperties().getOrDefault("mail.smtp.starttls.enable", "false"));
        props.put("mail.debug", mailProperties.getProperties().getOrDefault("mail.debug", "false"));

        return mailSender;
    }
}