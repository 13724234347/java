package com.netty;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
          
    }  
  
}  