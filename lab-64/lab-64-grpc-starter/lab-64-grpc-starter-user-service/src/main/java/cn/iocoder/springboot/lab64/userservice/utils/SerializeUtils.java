package cn.iocoder.springboot.lab64.userservice.utils;

import static com.google.common.base.Throwables.throwIfUnchecked;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.google.common.base.Preconditions;
import com.google.protobuf.ByteString;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaoming <zhaoming@kuaishou.com>
 * Created on 2019-11-07
 */
@Slf4j
public class SerializeUtils {

    public static ByteString serialize(Object object) {
        Preconditions.checkArgument(object instanceof Serializable, "object should be serializable");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return ByteString.copyFrom(baos.toByteArray());
        } catch (Exception ex) {
            log.error("serialize error", ex);
            throwIfUnchecked(ex);
            throw new RuntimeException("serialize error", ex);
        }
    }

    public static <T> T unserialize(ByteString byteString) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteString.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception ex) {
            log.error("unserialize error", ex);
            throwIfUnchecked(ex);
            throw new RuntimeException("unserialize error", ex);
        }
    }

    public static byte[] serializeObject(Object object) {
        Preconditions.checkArgument(object instanceof Serializable, "object should be serializable");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception ex) {
            log.error("serialize error", ex);
            throwIfUnchecked(ex);
            throw new RuntimeException("serialize error", ex);
        }
    }

    public static Object unserializeByte(byte[] byteString) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteString);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception ex) {
            log.error("unserialize error", ex);
            throwIfUnchecked(ex);
            throw new RuntimeException("unserialize error", ex);
        }
    }

    public static <T> T unserializeByteString(ByteString byteString) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteString.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception ex) {
            log.error("unserialize error", ex);
            throwIfUnchecked(ex);
            throw new RuntimeException("unserialize error", ex);
        }
    }
}