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

public class GrammarEngineTest
{
    static
    {
        System.loadLibrary("jgrapenlp");
    }

    private GrammarEngine grammarEngine;
    private ObjectMapper mapper;

    @BeforeClass(groups = {"unit"})
    protected void setUp()
    {
        File resourceFolder = new File(getExecutingClassPathName(GrammarEngineTest.class));
        File grammarFile = new File(resourceFolder, "test_grammar.fst2");
        File binDelafFile = new File(resourceFolder, "test_delaf.bin");
        grammarEngine = new GrammarEngine(grammarFile.getAbsolutePath(), binDelafFile.getAbsolutePath());
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
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

    @DataProvider(name = "sentenceXJsonResultDataProvider")
    public Object[][] create_sentenceXJsonResult()
    {
        return new Object[][]
        {
            {"", "[ ]"},
            {"unrecognized sentence", "[ ]"},
            {"this is a test sentence",
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 9\n" +
                    "  } ],\n" +
                    "  \"weight\" : 70\n" +
                    "} ]"},
            {"this is another test sentence",
                    "[ {\n" +
                    "  \"segments\" : [ {\n" +
                    "    \"name\" : \"label\",\n" +
                    "    \"start\" : 8,\n" +
                    "    \"end\" : 15\n" +
                    "  } ],\n" +
                    "  \"weight\" : 70\n" +
                    "} ]"}
        };
    }

    @Test(groups = {"unit"}, dataProvider = "sentenceXJsonResultDataProvider", enabled = false)
    public void test_tag(String sentence, String expectedJson) throws JsonProcessingException
    {
        uaui_simple_segment_array_x_weight_array nativeResult = grammarEngine.tag(sentence);
        GrammarParse[] actual = GrammarParse.nativeGrammarParsesToJava(nativeResult);
        String actualJson = mapper.writeValueAsString(actual);
        Assert.assertEquals(actualJson, expectedJson);
    }
}
