package com.abin.lee.lucene.common.util.lucene;

import com.google.common.collect.Lists;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-1-16
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
public class LucenePage {
    /**
     * 多条件查询
     *
     * @param termQueryList
     * @return
     */
    public static List<Document> complexSearch(List<Query> termQueryList) {
        List<Document> docList = new ArrayList<Document>();
        try {
            Directory directory = FSDirectory.open(Paths.get("D:/lucene/index"));//打开索引文件夹
            IndexReader reader = DirectoryReader.open(directory);//读取目录
            IndexSearcher search = new IndexSearcher(reader);//初始化查询组件

            BooleanQuery query = new BooleanQuery();
            for (Query termQuery : termQueryList) {
                query.add(termQuery, BooleanClause.Occur.MUST);
            }
            TopDocs td = search.search(query, 10000);//获取匹配上元素的一个docid
            for (ScoreDoc doc : td.scoreDocs) {
                docList.add(search.doc(doc.doc));
            }
            reader.close();//关闭资源
            directory.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docList;
    }

    public static void query(){
        Term t1=new Term("info","first");
        Term t2=new Term("content","zhong1");
        TermQuery q1=new TermQuery(t1);
        TermQuery q2=new TermQuery(t2);
        List<Query> termQueryList = Lists.newArrayList();
        termQueryList.add(q1);
        termQueryList.add(q2);
        List<Document> lists = complexSearch(termQueryList);
        System.out.println(lists.size());
//        System.out.println(JsonUtil.toJson(lists.get(0).getFields()));
        print(lists);
    }

    public static void print(List<Document> lists){
        for(Iterator<Document> iterator=lists.iterator();iterator.hasNext();){
            Document document = iterator.next();
            System.out.println("info="+document.getField("info"));
            System.out.println("content="+document.getField("content"));
        }
    }

    public static void searchByPage(){
        try{
            Directory directory = FSDirectory.open(Paths.get("D:/lucene/index"));//打开索引文件夹
            IndexReader  reader=DirectoryReader.open(directory);//读取目录
            IndexSearcher search=new IndexSearcher(reader);//初始化查询组件
            TopDocs all=search.search(new MatchAllDocsQuery(), 50000);
            int offset=0;//起始位置
            int pageSize=30;//分页的条数
            int total=30;//结束条数
            int z=0;
            while(z<=50){//总分页数
                System.out.println("==============================");
                pageScoreDocs(offset,total,search, all.scoreDocs);//调用分页打印
                offset=(z*pageSize+pageSize);//下一页的位置增量
                z++;//分页数+1；
                total=offset+pageSize;//下一次的结束分页量
            }
            reader.close();//关闭资源
            directory.close();//关闭连接

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void pageScoreDocs(int offset,int total,IndexSearcher searcher,ScoreDoc[] doc) throws Exception{
        //System.out.println("offset:"+offset+"===>"+total);
        for(int i=offset;i<total;i++){
            //System.out.println("i"+i+"==>"+doc.length);
            if(i>doc.length-1){//当分页的长度数大于总数就停止
                break;
            }else{
                Document dosc=searcher.doc(doc[i].doc);
                System.out.println(dosc.get("name"));
            }
        }
    }

    public static void searchAfter(){
        try{
            Directory directory = FSDirectory.open(Paths.get("D:/lucene/index"));//打开索引文件夹
            IndexReader  reader=DirectoryReader.open(directory);//读取目录
            IndexSearcher search=new IndexSearcher(reader);//初始化查询组件

            int pageStart=0;
            ScoreDoc lastBottom=null;//相当于pageSize
            while(pageStart<10){//这个只有是paged.scoreDocs.length的倍数加一才有可能翻页操作
                TopDocs paged=null;
                paged=search.searchAfter(lastBottom, new MatchAllDocsQuery(),null,30);//查询首次的30条
                if(paged.scoreDocs.length==0){
                    break;//如果下一页的命中数为0的情况下，循环自动结束
                }
//                page(search,paged);//分页操作，此步是传到方法里对数据做处理的

                pageStart+=paged.scoreDocs.length;//下一次分页总在上一次分页的基础上
                lastBottom=paged.scoreDocs[paged.scoreDocs.length-1];//上一次的总量-1，成为下一次的lastBottom
            }
            reader.close();//关闭资源
            directory.close();//关闭连接

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
