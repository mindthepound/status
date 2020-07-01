package io.spin.status.util.aws;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import io.spin.status.domain.basic.email.Email;
import io.spin.status.enumeration.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SESHelper {

    @Value("${spring.aws.email-sender}")
    private String emailSender;

    private AmazonSimpleEmailService amazonSimpleEmailService;

    public SESHelper(
            AmazonSimpleEmailService amazonSimpleEmailService
    ) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    public SendEmailResult sendEmail(Email email) {
        // TODO 만약 EmailType 블랙 리스트 등에 대한 검증이 필요 하다면 먼저 실행 한다.

        Destination destination = new Destination().withToAddresses(email.getTo());

        Content subject = new Content().withData(email.getSubject());
        Body body = email.getType() == EmailType.HTML ?
                new Body().withHtml(new Content().withData(email.getBody())) :
                new Body().withText(new Content().withData(email.getBody()));

        Message message = new Message().withSubject(subject).withBody(body);

        return amazonSimpleEmailService.sendEmail(new SendEmailRequest()
                .withSource(emailSender)
                .withDestination(destination)
                .withMessage(message));
    }

    public List<SendEmailResult> sendEmails(List<Email> emails) {

        List<SendEmailResult> results = new ArrayList<>();

        for (Email email : emails)
            results.add(sendEmail(email));

        return results;
    }

}
