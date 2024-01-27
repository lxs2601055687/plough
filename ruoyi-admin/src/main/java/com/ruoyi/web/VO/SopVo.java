package com.ruoyi.web.VO;

import edu.stanford.nlp.ling.Word;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SopVo {
    /**
     * 主语
     */
    private String subject;
    /**
     * 谓语
     */
    private String predicate;
    /**
     * 宾语
     */
    private String object;
    /**
     * 结果
     */
    private String result;

    private List<String> wordList;

    public SopVo(String subject, String predicate, String object, List<String> wordList) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.wordList = wordList;
    }

    public SopVo() {

    }

    public SopVo(String subject, String predicate) {
        this.subject = subject;
        this.predicate = predicate;
    }
}
