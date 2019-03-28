package com.tzh.upload_content;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client {
	public static void main(String[] args) {
		IoConnector connector = new NioSocketConnector();
//		connector.getFilterChain().addLast( "logger", new LoggingFilter() ); //可要可不要
		connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(new ClientHander());
		
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(BaseConfig.ADDR,BaseConfig.PORT));
		//等待建立连接
		connectFuture.awaitUninterruptibly();
		System.out.println("连接成功");
		
		IoSession session = connectFuture.getSession();
		
		for (int i = 0; i < 10; i++) {
			session.write("刘聪是个智障");
		}
		/*Scanner sc = new Scanner(System.in);
		
		boolean quit = false;
		
		while(!quit){
			
			String str = sc.next();
			if(str.equalsIgnoreCase("quit")){
				quit = true;
			}
			session.write(str);
		}*/
		
		//关闭
		if(session!=null){
			if(session.isConnected()){
				session.getCloseFuture().awaitUninterruptibly();
			}
			
			connector.dispose(true);
		}
		
		
	}
}
