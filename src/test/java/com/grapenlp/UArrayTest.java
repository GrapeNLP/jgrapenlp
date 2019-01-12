package com.grapenlp;

import com.grapenlp.core.u_array;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UArrayTest
{
    static
    {
        System.loadLibrary("grapenlp");
        System.loadLibrary("jgrapenlp");
    }

    @DataProvider(name = "javaStringDataProvider")
    public Object[][] create_javaString()
    {
        return new Object[][]
                {
                        {""},
                        {"a"},
                        {"á"},
                        {"öü"},
                        {"Probando probando un, dos, tres."}
                };
    }

    @Test(groups = {"unit"}, dataProvider = "javaStringDataProvider")
    public void test_encode_string_decode_u_uarray(String javaString)
    {
        u_array nativeUArray = UArray.stringToUArray(javaString);
        String actual = UArray.uArrayToString(nativeUArray);
        Assert.assertEquals(actual, javaString);
    }
}
