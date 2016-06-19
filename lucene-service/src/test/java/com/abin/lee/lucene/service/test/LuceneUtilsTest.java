package com.abin.lee.lucene.service.test;

import com.abin.lee.lucene.common.util.json.JsonUtil;
import com.abin.lee.lucene.common.util.lucene.LuceneUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.lucene.search.Query;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-1-14
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */

public class LuceneUtilsTest {
    private static final String filePath = "D:/lucene/index";

    @Test
    public void testCreateIndex() throws IOException {
        for (int i = 0; i < 5; i++) {
            Map<String, Object> request = Maps.newHashMap();
            request.put("id", i);
            request.put("content", "我是中国人" + i);
            request.put("info", "wo are chinese");
            LuceneUtils.createIndex(filePath, request);
        }
    }

       @Test
    public void testSearch() throws Exception {
        String key = "info";
        String value = "wo are";
        LuceneUtils.search(filePath, key, value);
    }

    @Test
    public void testDeleteByTerm() throws Exception {
        String key = "id";
        String value = "0";
        ImmutableMap<String,String> build = ImmutableMap.of("id", "0");
//        LuceneUtils.delete(filePath, build);
        LuceneUtils.delete(filePath, key, value);
    }

    @Test
    public void testDeleteByQuery() throws Exception {
        String key = "id";
        String value = "0";
        Query query = LuceneUtils.query(key, value);
        System.out.println("query="+ query.toString());
        LuceneUtils.deleteIndex(filePath, query);
    }

    @Test
    public void testQuery() throws Exception {
        String key = "id";
        String value = "0";
        Query query = LuceneUtils.query(key, value);
        System.out.println("query="+ query.toString());
    }

    @Test
    public void testUpdate() throws Exception {
        Map<String, Object> request = Maps.newHashMap();
        request.put("id", 0);
        request.put("content", "我是中国人" + 00);
        request.put("info", "wo are chinese"+00);
        LuceneUtils.update(filePath, request);
    }

}
