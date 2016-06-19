//package com.abin.lee.lucene.service;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.search.Filter;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.Sort;
//import org.apache.lucene.search.TopDocs;
//import org.wltea.analyzer.lucene.IKQueryParser;
//import org.wltea.analyzer.lucene.IKSimilarity;
//
//public class Test_Search {
//
//    static IndexReader reader=null;
//    static{
//        try {
//            reader=IndexReader.open(Test_Index.getDirectory());
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public IndexSearcher getSearcher()
//    {
//        try {
//            reader=IndexReader.open(Test_Index.getDirectory());
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new IndexSearcher(reader);
//    }
//    //返回Searcher
//    public void Searcher(String keyword,Sort sort)
//    {
//        try {
//            IndexSearcher searcher=getSearcher();
//            //使文件评分显示出来
//            searcher.setDefaultFieldSortScoring(true, false);
//            //在搜索器中使用IKSimilarity相似度评估器
//            searcher.setSimilarity(new IKSimilarity());
//            //创建IK。。 Query
//            Query query =IKQueryParser.parse("content",keyword);
//            TopDocs topDocs=null;
//            if(sort!=null)
//            {
//                topDocs=searcher.search(query, 50, sort);
//            }else {
//                topDocs=searcher.search(query, 50);
//            }
//            //设置时间格式
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            for(ScoreDoc sds:topDocs.scoreDocs)
//            {
//                Document document=searcher.doc(sds.doc);
//                System.out.println(sds.doc+"-->"+document.get("filename")
//                        +"【"+document.get("path")+"】"+"["+document.get("size")
//                        +"]"+"("+sds.score+")"+"-->"+sdf.format
//                        (new Date(Long.valueOf(document.get("date")))));
//            }
//            searcher.close();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    //过滤
//    public void Searcher(String keyword,Filter filter)
//    {
//        try {
//            IndexSearcher searcher=getSearcher();
//            //使文件评分显示出来
//            searcher.setDefaultFieldSortScoring(true, false);
//            //在搜索器中使用IKSimilarity相似度评估器
//            searcher.setSimilarity(new IKSimilarity());
//            //创建IK。。 Query
//            Query query =IKQueryParser.parse("content",keyword);
//            TopDocs topDocs=null;
//            if(filter!=null)
//            {
//                topDocs=searcher.search(query,filter,50);
//            }else {
//                topDocs=searcher.search(query, 50);
//            }
//            //设置时间格式
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            for(ScoreDoc sds:topDocs.scoreDocs)
//            {
//                Document document=searcher.doc(sds.doc);
//                System.out.println(sds.doc+"-->"+document.get("filename")
//                        +"【"+document.get("path")+"】"+"["+document.get("size")
//                        +"]"+"("+sds.score+")"+"-->"+sdf.format
//                        (new Date(Long.valueOf(document.get("date")))));
//            }
//            searcher.close();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}