package com.lc.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class EmailHelper {
    
    private static final ResourceBundle bundle = ResourceBundle.getBundle("mail");
    private static final String sendFrom = bundle.getString("email.from");
    private static final String username = bundle.getString("username");
    private static final String password = bundle.getString("password");
    private static final String host = bundle.getString("email.host");
    
    public static int sendEmail(String someone, String subject, String content){
        Properties props = new Properties();
        props.setProperty("mail.host", host);
        props.setProperty("mail.smtp.auth", "true");
        
        Authenticator authenticator = new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(sendFrom));
            message.setRecipients(RecipientType.TO,InternetAddress.parse(someone));
            //message.setRecipients(RecipientType.TO,InternetAddress.parse("测试的接收的邮件多个以逗号隔开"));
            try {
            	// 5. 创建图片“节点”
               /* MimeBodyPart image = new MimeBodyPart();
                DataHandler dh = new DataHandler(new FileDataSource(img)); // 读取本地文件
                image.setDataHandler(dh);                   // 将图片数据添加到“节点”
                image.setContentID("image_fairy_tail");     // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
            	MimeBodyPart text = new MimeBodyPart();*/
            /*	text.setContent("这是一张图片<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");
            	// 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
                MimeMultipart mm_text_image = new MimeMultipart();
                mm_text_image.addBodyPart(text);
                mm_text_image.addBodyPart(image);
                mm_text_image.setSubType("related");*/    // 关联关系
             // 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
                //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
                //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
              /*  MimeBodyPart text_image = new MimeBodyPart();
                text_image.setContent(mm_text_image);
                // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
                MimeMultipart mm = new MimeMultipart();
                mm.addBodyPart(text_image);
                mm.setSubType("mixed");*/
             // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
                //message.setContent(mm);
         
                // 13. 保存上面的所有设置
                //message.saveChanges();
                
                
            	message.setSubject(subject);
                message.setContent(content,"text/html;charset=UTF-8");
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } catch (AddressException e) {
            e.printStackTrace();
            return -1;
        } catch (MessagingException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
