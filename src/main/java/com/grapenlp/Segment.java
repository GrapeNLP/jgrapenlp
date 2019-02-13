package com.grapenlp;

import com.grapenlp.core.uau_simple_segment;

public class Segment
{
    private String name;
    private long start;
    private long end;

    public Segment(String name, long start, long end)
    {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Segment(uau_simple_segment nativeSegment)
    {
        name = UOutBoundTrie.uOutBoundTrieStringToString(nativeSegment.getName());
        start = nativeSegment.getBegin();
        end = nativeSegment.getEnd();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getStart()
    {
        return start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }

    public long getEnd()
    {
        return end;
    }

    public void setEnd(long end)
    {
        this.end = end;
    }

    public void appendToBuilder(StringBuilder builder, String sentence)
    {
        builder.append(name);
        if (start < end)
        {
            builder.append(": ");
            builder.append(sentence, (int)start, (int)end);
            builder.append('\n');
        }
    }
}
