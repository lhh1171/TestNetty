package org;

import io.netty.buffer.ByteBuf;

public class TestByteBufHolder2 {

    public static void nn() {
        System.out.println(TestByteBufHolder.holder.toString());
    }


    public static void main(String[] args) {
        ByteBuf content = TestByteBufHolder.holder.content();
        nn();
        System.out.println(content);
        System.out.println(TestByteBufHolder.holder.toString());
        //引用计数减一一
//        System.out.println(holder.release(1));
        //引用计数
        System.out.println(TestByteBufHolder.holder.refCnt());
        while (true){

        }
    }
}
