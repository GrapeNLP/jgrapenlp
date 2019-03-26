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

import com.grapenlp.core.uaui_simple_segment_array_x_weight_array;

import java.io.File;
import java.io.PrintStream;

public class MainGrammarEngine
{
    static
    {
        System.loadLibrary("jgrapenlp");
    }

    private GrammarEngine grammarEngine;

    private static void printUsage(PrintStream out)
    {
        out.println("Arguments:");
        out.println("1) Path to grammar .fst2 file");
        out.println("2) Path to dictionary .bin file");
        out.println("3) Sentence to tag (use quotes if it contains spaces)");
    }

    private static File getValidInputFile(String path) throws Exception
    {
        File f = new File(path);
        if (!f.isFile())
            throw new Exception("File \"" + path + "\" does not exist");
        if (!f.exists())
            throw new Exception("\"" + path + "\" is not a file");
        if (!f.canRead())
            throw new Exception("File \"" + path + "\" is not readable");
        return f;
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length != 3)
        {
            System.err.println("Expected 3 arguments but got " + args.length);
            printUsage(System.err);
            System.exit(1);
        }
        File grammarFile = getValidInputFile(args[0]);
        File binDelafFile = getValidInputFile(args[1]);
        String sentence = args[2];

        GrammarEngine grammarEngine = new GrammarEngine(grammarFile.getAbsolutePath(), binDelafFile.getAbsolutePath());
        uaui_simple_segment_array_x_weight_array nativeResult = grammarEngine.tag(sentence);
        GrammarParse[] parses = GrammarParse.nativeGrammarParsesToJava(nativeResult);
        if (parses.length == 0)
            System.out.println("Unrecognized sentence");
        else
            System.out.println(GrammarParse.serializeParses(parses, sentence));
    }
}
