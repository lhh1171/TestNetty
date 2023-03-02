package org;

import io.netty.buffer.*;
import io.netty.util.ByteProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;

@jdk.nashorn.internal.runtime.logging.Logger
public class TestByteBuf {
    static Logger logger = LoggerFactory.getLogger(TestByteBuf.class);
    public static void create(String[] args) {
        logger.info("-------------------创建堆缓冲区-------------------");
        ByteBuf heapBuf = Unpooled.buffer(10);
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            //0,0
            logger.info("offset:{},length:{}", offset, length);
        }

        logger.info("-------------------直接内存缓冲区-------------------");
        ByteBuf directBuffer = Unpooled.directBuffer(10);
        if (!directBuffer.hasArray()) {
            int length = directBuffer.readableBytes();
            byte[] array = new byte[length];
            ByteBuf bytes = directBuffer.getBytes(directBuffer.readerIndex(), array);
            //0,0
            logger.info("offset:{},length:{}",bytes.readerIndex() , array.length);
        }
    }

    public static void putAndGet(String[] args) {
        //读取所有可读的字节
        ByteBuf buffer = Unpooled.buffer(10);
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
        //可写字符串
        ByteBuf heapBuf = Unpooled.buffer(10);
        //this.capacity - this.writerIndex > 4
        while (heapBuf.writableBytes() > 4) {
            heapBuf.writeInt(new Random().nextInt());
        }
    }
    public static void testFind() {
        logger.info("-------------------查找操作-------------------");
        ByteBuf heapBuf = Unpooled.buffer(13);

        heapBuf.writeByte(12);
        heapBuf.writeByte(33);
        heapBuf.writeByte(33);
        heapBuf.writeBytes("\r\n".getBytes());
        //在0到12区间里找到\r的Index
        int i = heapBuf.indexOf(0, 12, (byte) '\r');
        //2
        System.out.println(i);
        //在CR ('\r')或LF ('\n')上中止
        i = heapBuf.forEachByte(ByteProcessor.FIND_CRLF);
        //2
        System.out.println(i);
    }


    public static void testSlice() {
        logger.info("slice和复制。。。。。");
        ByteBuf byteBuf = Unpooled.copiedBuffer("jannal", Charset.forName("utf-8"));
        //返回此缓冲区的子区域的一部分，不是复制而是真实的指针
        ByteBuf slice = byteBuf.slice(0, 6);
        //jannal
        System.out.println(slice.toString(Charset.forName("utf-8")));
        byteBuf.setByte(0, (byte) 'J');
//        assert byteBuf.getByte(0) == slice.getByte(0);
        //J
        System.out.println((char)byteBuf.getByte(0));
        //J
        System.out.println((char)slice.getByte(0));

        //复制

        ByteBuf copy = byteBuf.copy(0, 6);
        System.out.println(copy.toString(Charset.forName("utf-8")));

        byteBuf.setByte(0, (byte) 'j');
        //j
        System.out.println((char)byteBuf.getByte(0));
        //J
        System.out.println((char)copy.getByte(0));
    }



//    当向ByteBuf写入数据时，发现容量不足时，会触发扩容，而具体的扩容规则是
//
//    假设ByteBuf初始容量是10。
//
//
//    如果写入后数据大小未超过512个字节，则选择下一个16的整数倍进行库容。
//    比如写入数据后大小为12，则扩容后的capacity是16。
//    如果写入后数据大小超过512个字节，则选择下一个2^n。
//    比如写入后大小是512字节，则扩容后的capacity是2^10^=1024 。
//    （因为2^9^=512，长度已经不够了）
//    扩容不能超过maxCapacity，否则会报错。
    public static void testAlloc() {
        //默认256
        //最大容量是Int.max
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();//可自动扩容

        System.out.println(buf.maxCapacity());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            //演示的时候，可以把循环的值扩大，就能看到扩容效果
            sb.append(" - " + i);
        }
        buf.writeBytes(sb.toString().getBytes());
    }


    public static void testPool(){
        //true是直接内存，false是heap内存
        AbstractByteBufAllocator abstractByteBufAllocator=new AbstractByteBufAllocator(true) {
            @Override
            protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
                return null;
            }

            @Override
            protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
                return null;
            }

            public boolean isDirectBufferPooled() {
                return false;
            }
        };

        ByteBuf byteBuf1=PooledByteBufAllocator.DEFAULT.buffer();
        ByteBuf byteBuf2=PooledByteBufAllocator.DEFAULT.directBuffer();
        ByteBuf byteBuf3=PooledByteBufAllocator.DEFAULT.heapBuffer();
    }
    public static void main(String[] args) {
        testAlloc();
    }
}
