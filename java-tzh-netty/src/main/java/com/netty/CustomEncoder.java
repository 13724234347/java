package com.netty;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

public class CustomEncoder extends MessageToByteEncoder<Entity> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, Entity entity, ByteBuf out) throws Exception {  
        if(null == entity){  
            throw new Exception("entity is null");  
        }  
        String fileName = entity.getFileName();
        byte[] name = fileName.getBytes(Charset.forName("utf-8"));
        String fileContent = entity.getFileContent();
        byte[] content = fileContent.getBytes(Charset.forName("utf-8"));
        out.writeInt(name.length);
        out.writeBytes(name);
        out.writeInt(content.length);
        out.writeBytes(content);
    } 

    
    
    
}  