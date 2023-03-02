package org;

import java.nio.ByteBuffer;

public class TestByteBuffer {
    public static void create(String[] args) {
        System.out.println("----------Test allocate--------");
        System.out.println("before alocate:" + Runtime.getRuntime().freeMemory());

        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
        // 要超过多少内存大小JVM才能感觉到？
        ByteBuffer buffer = ByteBuffer.allocate(102400);
        System.out.println("buffer = " + buffer);

        System.out.println("after alocate:"
                + Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct alocate:"
                + Runtime.getRuntime().freeMemory());

        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];
        //将字节数组包装到缓冲区HeapByteBuffer中
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);

    }

    /*
    limit(), limit(10)等	其中读取和设置这4个属性的方法的命名和jQuery中的val(),val(10)类似，一个负责get，一个负责set
    reset()	把position设置成mark的值，相当于之前做过一个标记，现在要退回到之前标记的地方
    clear()	position = 0;limit = capacity;mark = -1;  有点初始化的味道，但是并不影响底层byte数组的内容
    flip()	limit = position;position = 0;mark = -1;  翻转，也就是让flip之后的position到limit这块区域变成之前的0到position这块，翻转就是将一个处于存数据状态的缓冲区变为一个处于准备取数据的状态
    rewind()	把position设为0，mark设为-1，不改变limit的值
    remaining()	return limit - position; 返回limit和position之间相对位置差
    hasRemaining()	return position < limit返回是否还有未读内容
    compact()	把从position到limit中的内容移到0到limit-position的区域内，position和limit的取值也分别变成limit-position、capacity。如果先将positon设置到limit，再compact，那么相当于clear()
    get()	相对读，从position位置读取一个byte，并将position+1，为下次读写作准备
    get(int index) 	绝对读，读取byteBuffer底层的bytes中下标为index的byte，不改变position
    get(byte[] dst, int offset, int length)	从position位置开始相对读，读length个byte，并写入dst下标从offset到offset+length的区域
    put(byte b)	相对写，向position的位置写入一个byte，并将postion+1，为下次读写作准备
    put(int index, byte b)	绝对写，向byteBuffer底层的bytes中下标为index的位置插入byte b，不改变position
    put(ByteBuffer src)	用相对写，把src中可读的部分（也就是position到limit）写入此byteBuffer
    put(byte[] src, int offset, int length)	从src数组中的offset到offset+length区域读取数据并使用相对写写入此byteBuffer
    * */
    public static void putAndget(String args[]){
        ByteBuffer buffer = ByteBuffer.allocate(102400);
        System.out.println("--------Test reset-----把position设置成mark的值，相当于之前做过一个标记，现在要退回到之前标记的地方-----");
        buffer.clear();
        buffer.position(5);
        //mark=position
        buffer.mark();
        buffer.position(10);
        System.out.println("before reset:" + buffer);
        buffer.reset();
        System.out.println("after reset:" + buffer);

        System.out.println("--------Test rewind-----把position设为0，mark设为-1，不改变limit的值---");
        buffer.clear();
        buffer.position(10);
        buffer.limit(15);
        System.out.println("before rewind:" + buffer);
        buffer.rewind();
        System.out.println("before rewind:" + buffer);

        System.out.println("--------Test compact(把前面)------把从position到limit中的内容移到0到limit-position的区域内，" +
                "position和limit的取值也分别变成limit-position、capacity。如果先将position设置到limit，再compact，那么相当于clear()--");
        buffer.clear();
        buffer.put("abcd".getBytes());
        System.out.println("before compact:" + buffer);
        System.out.println(new String(buffer.array()));
        System.out.println("--------------------flip-----------------------limit = position;position = 0;mark = -1;  " +
                "翻转，也就是让flip之后的position到limit这块区域变成之前的0到position这块，翻转就是将一个处于存数据状态的缓冲区变为一个处于准备取数据的状态");
        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println("after three gets:" + buffer);
        System.out.println("\t" + new String(buffer.array()));
        buffer.compact();
        System.out.println("after compact:" + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("------Test get-------------");
        buffer = ByteBuffer.allocate(32);
        buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd')
                .put((byte) 'e').put((byte) 'f');
        System.out.println("before flip()" + buffer);
        // 转换为读取模式
        buffer.flip();
        System.out.println("before get():" + buffer);
        System.out.println((char) buffer.get());
        System.out.println("after get():" + buffer);
        // get(index)不影响position的值
        System.out.println((char) buffer.get(2));
        System.out.println("after get(index):" + buffer);
        byte[] dst = new byte[10];
        buffer.get(dst, 0, 2);
        System.out.println("after get(dst, 0, 2):" + buffer);
        System.out.println("\t dst:" + new String(dst));
        System.out.println("buffer now is:" + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("--------Test put-------");
        ByteBuffer bb = ByteBuffer.allocate(32);
        System.out.println("before put(byte):" + bb);
        System.out.println("after put(byte):" + bb.put((byte) 'z'));
        System.out.println("\t" + bb.put(2, (byte) 'c'));
        // put(2,(byte) 'c')不改变position的位置
        System.out.println("after put(2,(byte) 'c'):" + bb);
        System.out.println("\t" + new String(bb.array()));
        // 这里的buffer是 abcdef[pos=3 lim=6 cap=32]
        bb.put(buffer);
        System.out.println("after put(buffer):" + bb);
        System.out.println("\t" + new String(bb.array()));
    }
}
