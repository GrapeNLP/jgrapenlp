/*
 * GRAPENLP
 *
 * Copyright (C) 2004-2019 Javier Miguel Sastre Martínez <javier.sastre@telefonica.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 */

/*
 *  @author Javier Sastre
 */

package com.grapenlp;

import com.grapenlp.core.u_array;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UArrayTest
{
    static
    {
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

    @Test(groups = {"unit"}, dataProvider = "javaStringDataProvider", enabled = false)
    public void test_encode_string_decode_u_uarray(String javaString)
    {
        u_array nativeUArray = UArray.stringToUArray(javaString);
        String actual = UArray.uArrayToString(nativeUArray);
        Assert.assertEquals(actual, javaString);
    }
}
