//package com.abin.lee.lucene.service;
//
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.Filter;
//import org.apache.lucene.search.NumericRangeFilter;
//import org.apache.lucene.search.QueryWrapperFilter;
//import org.apache.lucene.search.Sort;
//import org.apache.lucene.search.SortField;
//import org.apache.lucene.search.TermRangeFilter;
//import org.apache.lucene.search.WildcardQuery;
//import org.junit.Before;
//import org.junit.Test;
//
//public class Test_All {
//    Test_Index index=null;
//    Test_Search search =null;
//    @Before
//    public void init()
//    {
//        index=new Test_Index();
//        search =new Test_Search();
//    }
//
//    @Test
//    public void test_index()
//    {
//        index.index(true);
//    }
//    @Test
//    public void test_search01()
//    {
////		search.Searcher("java", null);
////		//按照默认评分排序
////		search.Searcher("java", new Sort().RELEVANCE);
////		//通过文件ID排序
////		search.Searcher("java", new Sort().INDEXORDER);
////		//通过文件大小排序
////		search.Searcher("java", new Sort(new SortField("size",SortField.INT)));
//        //通过文件日期排序
////		search.Searcher("java", new Sort(new SortField("date",SortField.LONG)));
//        //通过文件名称排序,第三个参数设置排序方式（true为降序）
////		search.Searcher("java", new Sort(new SortField("filename",SortField.STRING
////			,true)));
//        //多条件排序
//        search.Searcher("java",new Sort(new SortField("filename",SortField.STRING
//        ),SortField.FIELD_SCORE));
//    }
//
//    @Test
//    public void test_search02()
//    {
//        Filter trf=new TermRangeFilter("filename", "Tomcat环境变量配置.kk",
//                "Tomcat环境变量配置.txt.kk", true,true);
//        trf=NumericRangeFilter.newIntRange("size", 2, 100000,
//                true, true);
//        //通过一个query进行过滤
//        trf=new QueryWrapperFilter(new WildcardQuery(new Term("filename","*.kk")));
//        search.Searcher("java", trf);
//    }
//
//}
