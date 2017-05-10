/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author Claudie
 */
public class EmailVerificationImpl implements EmailVerification {

    //@Autowired
    //private MailSender mailSender;
    @Autowired
    private JavaMailSender mailSender;


    public EmailVerificationImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(String to, int base, int currentValue, int recommendedTime) {
        //SimpleMailMessage verificationEmail = new SimpleMailMessage();
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setSubject(":: SmartJi Alert ::");
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            String htmlMsg = "<h2>:: SmartJi Alert ::</h2>"
                    + "The current <i>Heat Index</i> value is <b>" + currentValue + " Degree CelsiusC</b>.<br/>" 
                    + "The specified optimum <i>Heat Index</i> is set at <b>" + base + " Degree Celsius</b>.<br/>"
                    + "<br/>It is recommeneded to turn on the cooling system for <b>" + recommendedTime + " mins</b>.<br/>";
            message.setContent(htmlMsg, "text/html");
            helper.setFrom("SmartJi");
            helper.setTo(to);
            //helper.setText("This is an Alert\n\nBase Value is: " + base + "\nCurrent Value: " + currentValue + "\nRecommended Time: " + recommendedTime + " mins", true);
            mailSender.send(message);

        } catch (MessagingException ex) {
            ex.getMessage();
        }
        
//        verificationEmail.setFrom("SmartJi");
//        verificationEmail.setTo("claudie.ang.2014@sis.smu.edu.sg");
//        verificationEmail.setSubject(":: SmartJi Alert ::");
//        verificationEmail.setText("This is an Alert\n\nBase Value is: " + base 
//            + "\nCurrent Value: " + currentValue + "\nRecommended Time: " + recommendedTime + " mins");
//        try {
//            mailSender.send(verificationEmail);
//        } catch (MailException me) {
//            me.printStackTrace();
//        }
    }

}
