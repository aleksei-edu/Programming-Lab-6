package com.lapin.network.obj;

import java.io.*;

public class Serializer {
    public static byte[] serialize(Serializable objectToSerialize) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);
        objectOutput.writeObject(objectToSerialize);
        objectOutput.flush();

        return byteArrayOutputStream.toByteArray();
    }
    public static Serializable deserialize(byte[] serializedObject) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
        ObjectInputStream objectInput = new ObjectInputStream(byteArrayInputStream);
        try {
            Serializable obj = (Serializable) objectInput.readObject();
            return obj;
        }
        catch (EOFException e) {
            return null;
        }
        catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }
}
