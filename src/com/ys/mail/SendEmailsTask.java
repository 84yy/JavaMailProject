package com.ys.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Huang Zengmeng
 * @description 启动配置类
 * @date 2018/9/9
 */
public class SendEmailsTask implements Runnable {


    SendEmailsTask(List<String> senderList){
        super();
        this.senderList=senderList;
    }

    //发件人地址
    public static String senderAddress = "gp_test_01@163.com";
    //收件人地址
    public static String recipientAddress = "gp_test_01@163.com";
    //发件人账户名
    public static String senderAccount = "gp_test_01";
    //发件人账户密码
    public static String senderPassword = "替换成验证密码";

    List<String> senderList;

    @Override
    public void run() {

        try {
            Properties props = new Properties();
            //设置用户的认证方式
            props.setProperty("mail.smtp.auth", "true");
            //设置传输协议
            props.setProperty("mail.transport.protocol", "smtp");
            //设置发件人的SMTP服务器地址
            props.setProperty("mail.smtp.host", "smtp.163.com");
            //2、创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getInstance(props);
            //设置调试信息在控制台打印出来
            session.setDebug(true);
            //3、创建邮件的实例对象
            System.out.println("dddd");

            for (String s : senderList) {
                Message msg = getMimeMessage(session,s);
                //4、根据session对象获取邮件传输对象Transport
                Transport transport = session.getTransport();
                //设置发件人的账户名和密码
                transport.connect(senderAccount, senderPassword);
                //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
                transport.sendMessage(msg,msg.getAllRecipients());
                transport.close();
            }
            //如果只想发送给指定的人，可以如下写法
            //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

            //5、关闭邮件连接


        }catch (Exception e){
            e.printStackTrace();
        }


    }



    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session,String senderAddress) throws Exception{
        //创建一封邮件的实例对象




        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("这是一份简单的邮件","UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮件 请不要推线！", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        //结束

        return msg;
    }
}
