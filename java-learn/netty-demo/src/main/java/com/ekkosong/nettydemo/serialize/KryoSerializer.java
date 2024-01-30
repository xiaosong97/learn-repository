package com.ekkosong.nettydemo.serialize;


import com.ekkosong.nettydemo.dto.RpcRequest;
import com.ekkosong.nettydemo.dto.RpcResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer {
    /**
     * 由于 Kryo 不是线程安全的。每个线程都应该有自己的 Kryo，Input 和 Output 实例。
     * 所以，使用 ThreadLocal 存放 Kryo 对象
     */
    private static final ThreadLocal<Kryo> kyroThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        kryo.setReferences(true);// 默认值为false,是否关闭循环引用，可以提高性能，但是一般不推荐设置为 true
        kryo.setRegistrationRequired(false);// 默认值为true,是否关闭注册行为,关闭之后可能存在序列化问题，一般推荐设置为 true
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kyroThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kyroThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException("序列化失败");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kyroThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            T object = kryo.readObject(input, clazz);
            kyroThreadLocal.remove();
            return object;
        }catch (Exception e) {
            throw new SerializeException("反序列化失败");
        }
    }
}
