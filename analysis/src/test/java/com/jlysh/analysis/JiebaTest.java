package com.jlysh.analysis;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 江冷易水寒
 * @data 2021/5/20 19:09
 */



public class JiebaTest {
    private static final JiebaSegmenter JIEBA_SEGMENTER = new JiebaSegmenter();

    @Test
    public Map<String, Integer> testDemo() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        Map<String, Integer> map = new HashMap<>();
        String sentence = "这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。";
        List<String> process = segmenter.sentenceProcess(sentence);
        for (String p : process) {
            System.out.println(process);
            if (map.containsKey(p)) {
                map.put(p,map.get(p)+1);
            }else{
                map.put(p,1);
            }
        }
        return map;
    }
}