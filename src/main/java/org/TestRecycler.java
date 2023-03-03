package org;


import io.netty.util.internal.ObjectPool;

public class TestRecycler {
    //productObjectPoolRecycle是Product类型的对象池
    static ObjectPool<Product> productObjectPoolRecycle =  ObjectPool.newPool(new ObjectPool.ObjectCreator<Product>() {
        @Override
        public Product newObject(ObjectPool.Handle<Product> handle) {
            return new Product(handle);
        }
    });

    static class Product{
        ObjectPool.Handle<Product> handle ;

        Product(ObjectPool.Handle<Product> handle){
            this.handle = handle ;
        }

        void recycle(){
            this.handle.recycle(this);
        }

    }

    public static void main(String[] args) {
        Product p1 = productObjectPoolRecycle.get();
        p1.recycle();
        Product p2 = productObjectPoolRecycle.get();
        Product p3 = productObjectPoolRecycle.get();
        p3.recycle();
        Product p4 = productObjectPoolRecycle.get();
        System.out.println(p1==p2);
        System.out.println(p3==p4);
    }
}
