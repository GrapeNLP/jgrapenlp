package com.grapenlp;

import com.grapenlp.core.u_array;
import com.grapenlp.core.u_out_bound_name_string;

public class UOutBoundTrie
{
    public static String uOutBoundTrieStringToString(u_out_bound_name_string nativeUOutBoundTrieString)
    {
        long stringSize = nativeUOutBoundTrieString.size();
        u_array nativeUArray = new u_array(stringSize);
        nativeUOutBoundTrieString.to_array(nativeUArray);
        return UArray.uArrayToString(nativeUArray);
    }
}
