package com.grapenlp;

import com.grapenlp.core.*;

public class GrammarEngine
{
    private ualxiw_manager nativeGrammarEngine;

    public void resetModels(String grammarPathName, String binDelafPathName)
    {
        nativeGrammarEngine = new ualxiw_manager(rtno_type.LEXMASK_X_WEIGHTED_EXTRACTION_RTNO, grammarPathName, binDelafPathName);
    }

    public GrammarEngine(String grammarPathName, String binDelafPathName)
    {
        resetModels(grammarPathName, binDelafPathName);
    }

    public uaui_simple_segment_array_x_weight_array tag(String sentence)
    {
        u_array native_sentence = UArray.stringToUArray(sentence);
        nativeGrammarEngine.process(native_sentence.const_begin(), native_sentence.const_end(),
                rtno_parser_type.TO_FPRTN_AND_TOP_BLACKBOARD_EXTRACT_RTNO_PARSER, true, false, assoc_container_impl_choice.LRB_TREE, assoc_container_impl_choice.STD);
        return nativeGrammarEngine.get_simplified_weighted_output();
    }
}
