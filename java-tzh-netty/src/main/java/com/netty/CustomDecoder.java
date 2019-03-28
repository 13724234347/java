package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class CustomDecoder extends LengthFieldBasedFrameDecoder {  
      
    private static final int HEADER_SIZE = 8;  
      
    private String fileName;

	private int fileNameLenth;
	
	private String fileContent;
	
	private int fileContentLenth;  
    /** 
     *  
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度 
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置 
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度 
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 
     * @param initialBytesToStrip 解析的时候需要跳过的字节数 
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常 
     */ 
    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,  
            int lengthAdjustment, int initialBytesToStrip, boolean failFast) {  
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,  
                lengthAdjustment, initialBytesToStrip, failFast);  
    }  
      
    @Override  
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {  
        if (in == null) {  
            return null;  
        }  
	        if (in.readableBytes() < HEADER_SIZE) {  
	            throw new Exception("可读信息段比头部信息都小，你在逗我？");  
	        }  
          fileNameLenth = in.readInt();
	      if (in.readableBytes() < fileNameLenth) {  
	    	  throw new Exception("fileName字段你告诉我长度是"+fileNameLenth+",但是真实情况是没有这么多，你又逗我？");  
	      }  
		  ByteBuf buf = in.readBytes(fileNameLenth);  
		  byte[] req = new byte[buf.readableBytes()];  
		  buf.readBytes(req);  
		  
		  fileName = new String(req); 
		  
		  
		  fileContentLenth = in.readInt();
		  
		  if (in.readableBytes() < fileContentLenth) {  
	    	  throw new Exception("fileContent字段你告诉我长度是"+fileContentLenth+",但是真实情况是没有这么多，你又逗我？");  
	      }  
		  ByteBuf buf1 = in.readBytes(fileContentLenth);  
		  byte[] req1 = new byte[buf1.readableBytes()];  
		  buf1.readBytes(req1);  
		  fileContent = new String(req1); 
		  
		  Entity entity = new Entity(fileNameLenth,fileName,fileContentLenth,fileContent);
		  return entity;
    }  
}  