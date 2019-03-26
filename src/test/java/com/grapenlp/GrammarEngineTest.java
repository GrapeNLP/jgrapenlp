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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.grapenlp.core.uaui_simple_segment_array_x_weight_array;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.TreeMap;

public class GrammarEngineTest
{
    static
    {
        System.loadLibrary("jgrapenlp");
    }

    private GrammarEngine grammarEngine;
    private ObjectMapper mapper;
    private TreeMap<String, String> empty_context;
    private TreeMap<String, String> false_context;
    private TreeMap<String, String> true_context;

    @BeforeClass(groups = {"unit"})
    protected void setUp()
    {
        File resourceFolder = new File(getExecutingClassPathName(GrammarEngineTest.class));
        File grammarFile = new File(resourceFolder, "test_grammar.fst2");
        File binDelafFile = new File(resourceFolder, "test_delaf.bin");
        grammarEngine = new GrammarEngine(grammarFile.getAbsolutePath(), binDelafFile.getAbsolutePath());
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        empty_context = new TreeMap<String, String>();
        false_context = new TreeMap<String, String>();
        false_context.put("context", "false");
        true_context = new TreeMap<String, String>();
        true_context.put("context", "true");
    }

    public static String getExecutingClassPathName(Class contextClass)
    {
        URL url = contextClass.getProtectionDomain().getCodeSource().getLocation();
        String pathName = null;
        try
        {
            pathName = URLDecoder.decode(url.getPath(), "UTF-8");
            pathName = pathName + contextClass.getPackage().getName().replace('.', '/');
        }
        //Should not throw since UTF-8 is a supported encoding
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return pathName;
    }

    @DataProvider(name = "sentenceXContextXJsonResultDataProvider")
    public Object[][] create_sentenceXContextXJsonResult()
    {
        return new Object[][]
        {
            {"", empty_context, "[ ]"},
            {"unrecognized sentence", empty_context, "[ ]"},
            {"this is a test sentence", empty_context,
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 9\n" +
                    "  } ],\n" +
                    "  \"weight\" : 70\n" +
                    "} ]"},
            {"this is another test sentence", empty_context,
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 15\n" +
                    "  } ],\n" +
                    "  \"weight\" : 70\n" +
                    "} ]"},
            {"this is a context test sentence", empty_context, "[ ]"},
            {"this is another context test sentence", empty_context, "[ ]"},
            {"this is a context test sentence", false_context, "[ ]"},
            {"this is another context test sentence", false_context, "[ ]"},
            {"this is a context test sentence", true_context,
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 9\n" +
                    "  } ],\n" +
                    "  \"weight\" : 84\n" +
                    "} ]"},
            {"this is another context test sentence", true_context,
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 15\n" +
                    "  } ],\n" +
                    "  \"weight\" : 84\n" +
                    "} ]"}
        };
    }

    @Test(groups = {"unit"}, dataProvider = "sentenceXContextXJsonResultDataProvider")
    public void test_tag(String sentence, TreeMap<String, String> context, String expectedJson) throws JsonProcessingException
    {
        uaui_simple_segment_array_x_weight_array nativeResult = grammarEngine.tag(sentence, context);
        GrammarParse[] actual = GrammarParse.nativeGrammarParsesToJava(nativeResult);
        String actualJson = mapper.writeValueAsString(actual);
        Assert.assertEquals(actualJson, expectedJson);
    }
}
