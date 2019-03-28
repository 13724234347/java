package com.tzh.upload_content;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server_side {
private static final int PORT= BaseConfig.PORT;
	
	public static void main(String[] args) throws IOException {
 
		IoAcceptor acceptor = new NioSocketAcceptor();
		//acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
		acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(  new Server_sideHander() );
		
		acceptor.getSessionConfig().setReadBufferSize( 2048 );
                acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10);
		
		acceptor.bind(new InetSocketAddress(PORT));
		
	}
}
