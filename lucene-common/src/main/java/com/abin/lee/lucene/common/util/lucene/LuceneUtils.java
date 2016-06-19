package com.abin.lee.lucene.common.util.lucene;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-1-14
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
public class LuceneUtils {
    public static void main(String[] args) throws Exception {
//        createIndex();
//        query();
    }

    public static void createIndex(String filePath, Map<String, Object> params) throws IOException {
        Analyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(filePath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Document document = new Document();
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            if (entry.getValue() instanceof String)
                document.add(new TextField(entry.getKey(), entry.getValue().toString(), Field.Store.YES));
            else if (entry.getValue() instanceof Long)
                document.add(new LongField(entry.getKey(), Longs.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Integer)
                document.add(new IntField(entry.getKey(), Ints.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Float)
                document.add(new FloatField(entry.getKey(), Floats.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Double)
                document.add(new DoubleField(entry.getKey(), Doubles.tryParse(entry.getValue().toString()), Field.Store.YES));

        }
        indexWriter.addDocument(document);
        indexWriter.close();
        directory.close();
    }

    /**
     * 删除索引
     *
     * @param filePath
     * @param request
     * @throws Exception
     */
    public static void delete(String filePath, Map<String, String> request) throws Exception {
        Analyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(filePath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        for (Iterator iterator = request.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            indexWriter.deleteDocuments(new Term(entry.getKey().toString(), entry.getValue().toString()));
        }
        indexWriter.commit();
        indexWriter.close();
    }

    public static void delete(String filePath, String key, String value) throws Exception {
        Analyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(filePath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.deleteDocuments(new Term[] {new Term(key, value)});
        indexWriter.commit();
        indexWriter.close();
    }

    public static Query query(String fieldName, String queryString) throws ParseException {
        QueryParser parser = new QueryParser(fieldName, new StandardAnalyzer());
        Query query = parser.parse(queryString);
        return query;
    }

    public static void deleteIndex(String filePath, Query query) {
        try {
            Analyzer standardAnalyzer = new StandardAnalyzer();
            Directory directory = FSDirectory.open(Paths.get(filePath));
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.deleteDocuments(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新索引
     *
     * @throws Exception
     */
    public static void update(String filePath, Map<String, Object> request) throws Exception {
        Analyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(filePath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Document document = new Document();
        for (Iterator iterator = request.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            if (entry.getValue() instanceof String)
                document.add(new TextField(entry.getKey(), entry.getValue().toString(), Field.Store.YES));
            else if (entry.getValue() instanceof Long)
                document.add(new LongField(entry.getKey(), Longs.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Integer)
                document.add(new IntField(entry.getKey(), Ints.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Float)
                document.add(new FloatField(entry.getKey(), Floats.tryParse(entry.getValue().toString()), Field.Store.YES));
            else if (entry.getValue() instanceof Double)
                document.add(new DoubleField(entry.getKey(), Doubles.tryParse(entry.getValue().toString()), Field.Store.YES));

        }
        indexWriter.updateDocument(new Term(request.get("id").toString(), request.get("id").toString()), document);
        indexWriter.close();
    }

    /**
     * 关键字查询
     *
     * @param filePath
     * @param key
     * @param value
     * @throws Exception
     */
    public static void search(String filePath, String key, String value) throws Exception {
        Analyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(filePath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(directoryReader);

        QueryParser parser = new QueryParser(key, standardAnalyzer);
        Query query = parser.parse(value);

        ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("id"));
            System.out.println(hitDoc.get("info"));
            System.out.println(hitDoc.get("content"));
        }
        directoryReader.close();
        directory.close();
    }

}
