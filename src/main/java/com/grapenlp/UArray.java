package com.grapenlp;

import com.grapenlp.core.SWIGTYPE_p_unsigned_char;
import com.grapenlp.core.jgrapenlp;
import com.grapenlp.core.u_array;
import java.nio.charset.StandardCharsets;

public class UArray
{
    static u_array stringToUArray(String s)
    {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_16LE);
        u_array uArray = new u_array(bytes.length >> 1);
        SWIGTYPE_p_unsigned_char native_bytes = uArray.get_bytes();
        for (int i = 0; i < bytes.length; ++i)
            jgrapenlp.byte_array_setitem(native_bytes, i, bytes[i]);
        return uArray;
    }

    static String uArrayToString(u_array uArray)
    {
        SWIGTYPE_p_unsigned_char native_bytes = uArray.get_bytes();
        int byteCount = (int) uArray.size_in_bytes();
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; ++i)
            bytes[i] = (byte) jgrapenlp.byte_array_getitem(native_bytes, i);
        return new String(bytes, StandardCharsets.UTF_16LE);
    }
}
