package org;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;

public class TestByteBufHolder {
    static ByteBufHolder holder = new DefaultByteBufHolder(Unpooled.buffer());

    public static void nn() {
        System.out.println(holder.toString());
    }


    public static void main(String[] args) {
        ByteBuf content = holder.content();
        nn();
        System.out.println(content);
        System.out.println(holder.toString());
        //引用计数减一一
//        System.out.println(holder.release(1));
        //引用计数加一
        holder.retain();
        System.out.println(holder.refCnt());
    }
}
