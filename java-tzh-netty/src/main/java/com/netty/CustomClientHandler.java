package com.netty;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
public class CustomClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	String fileName = "liucong";
    	
    	String fileContent = "刘聪是个傻逼"; 
    	
    	Entity entity = new Entity(fileName.length(), fileName, fileContent.length(), fileContent);
        ctx.writeAndFlush(entity);  
    }  
  
}  