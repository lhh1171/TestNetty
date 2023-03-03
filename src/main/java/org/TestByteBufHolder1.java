package org;

import io.netty.buffer.*;

import java.nio.charset.Charset;

public class TestByteBufHolder1 {


    public static void main(String[] args) {
        ByteBuf recvBuffer =Unpooled.copiedBuffer("jannal", Charset.forName("utf-8"));
        if(recvBuffer.isDirect()){
            System.err.println(true);
        }
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        ByteBuf sendBuffer = allocator.buffer();//申请池化直接内存
        System.err.println("sendBuffer的引用计数:"+sendBuffer.refCnt());
        sendBuffer.retain();
        System.err.println("sendBuffer的引用计数:"+sendBuffer.refCnt());
        sendBuffer.release();
        System.err.println("sendBuffer的引用计数:"+sendBuffer.refCnt());
        try {
            byte[] bytesReady = new byte[recvBuffer.readableBytes()];
            recvBuffer.readBytes(bytesReady);
            System.out.println("channelRead收到数据："+ new String(bytesReady));
            byte[] sendBytes = new byte[] {0x7E,0x01,0x02,0x7e};
            sendBuffer.writeBytes(sendBytes);
            System.err.println("sendBuffer的引用计数:"+sendBuffer.refCnt());
        }catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }finally {
            System.err.println("recvBuffer的引用计数:"+recvBuffer.refCnt());
            recvBuffer.release(); //此处需要释放
            System.err.println("recvBuffer的引用计数:"+recvBuffer.refCnt());
        }
    }
}
