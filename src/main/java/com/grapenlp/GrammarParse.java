package com.grapenlp;

import com.grapenlp.core.uau_simple_segment_array;
import com.grapenlp.core.uaui_simple_segment_array_x_weight;
import com.grapenlp.core.uaui_simple_segment_array_x_weight_array;

public class GrammarParse
{
    private Segment[] segments;
    private int weight;

    public GrammarParse(uaui_simple_segment_array_x_weight nativeGrammarParse)
    {
        uau_simple_segment_array nativeSegments = nativeGrammarParse.getSsa();
        int segmentCount = (int) nativeSegments.size();
        segments = new Segment[segmentCount];
        for (int i = 0; i < segmentCount; ++i)
            segments[i] = new Segment(nativeSegments.get_elem_at(i));
        weight = nativeGrammarParse.getW();
    }

    public Segment[] getSegments()
    {
        return segments;
    }

    public void setSegments(Segment[] segments)
    {
        this.segments = segments;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    static GrammarParse[] nativeGrammarParsesToJava(uaui_simple_segment_array_x_weight_array nativeGrammarParses)
    {
        int grammarParseCount = (int) nativeGrammarParses.size();
        GrammarParse[] grammarParses = new GrammarParse[grammarParseCount];
        for (int i = 0; i < grammarParseCount; ++i)
            grammarParses[i] = new GrammarParse(nativeGrammarParses.get_elem_at(i));
        return grammarParses;
    }
}
