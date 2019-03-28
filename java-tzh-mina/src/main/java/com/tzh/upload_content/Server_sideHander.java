package com.tzh.upload_content;

import java.util.Date;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class Server_sideHander implements IoHandler{

	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("client与:"+session.getRemoteAddress().toString()+"建立连接");
	}

	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("打开链接");
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("client与:"+session.getRemoteAddress().toString()+"断开连接");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println( "IDLE " + session.getIdleCount( status ));
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		
		System.out.println("接受到的消息:"+str);
		
        if( str.trim().equalsIgnoreCase("quit") ){
            session.close(true);
            return;
        }
        Date date = new Date();
        session.write( date.toString() );
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("发送信息:"+message.toString());
	}

}
