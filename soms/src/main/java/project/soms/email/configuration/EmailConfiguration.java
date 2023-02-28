package project.soms.email.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import project.soms.email.repository.EmailRepository;
import project.soms.email.repository.EmailRepositoryImpl;

@Configuration
@RequiredArgsConstructor
public class EmailConfiguration {

  private final JavaMailSender javaMailSender;
  private final MailProperties mailProperties;

  @Bean
  public EmailRepository emailRepository() {
    return new EmailRepositoryImpl(javaMailSender, mailProperties);
  }
}
