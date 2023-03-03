package org;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;

public class TestByteBufHolder {
    public static void main(String[] args) {
        ByteBufHolder holder = new DefaultByteBufHolder(Unpooled.buffer());
        ByteBuf content = holder.content();
        System.out.println(content);
        System.out.println(holder.toString());
        //引用计数加一
        System.out.println(holder.release());
        //引用计数
        System.out.println(holder.refCnt());
    }
}
