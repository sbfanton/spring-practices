package com.sbfanton.oauth.oauthclient.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendMailVerificationMessage(String to, String linkUrl) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Verificación de correo");

        String htmlMsg = "<h3>¡Hola!</h3>" +
                "<p>Por favor, haz click en el siguiente enlace para validar tu correo electrónico, recuerda que el mismo es válido por 24hs:</p>" +
                "<a href=\"" + linkUrl + "\">Verificación de email</a>" +
                "<br><br>Saludos" +
                "<br><br><i>Equipo de OAuth Test Project<i>";

        helper.setText(htmlMsg, true);

        mailSender.send(message);

        return "Enviado OK";
    }
}
