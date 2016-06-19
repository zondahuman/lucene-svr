//package com.abin.lee.lucene.service;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.ClassicAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.LongField;
//import org.apache.lucene.document.NumericField;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.LockObtainFailedException;
//import org.apache.lucene.util.Version;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//public class Test_Index {
//
//    private Analyzer analyzer=new StandardAnalyzer();
//    private static Directory directory=null;
//    static{
//        try {
//            directory=FSDirectory.open(new File("f:/lucene/Index07").toPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static Directory getDirectory()
//    {
//        return directory;
//    }
//    public void index(boolean hasNew)
//    {
//        IndexWriter writer=null;
//        try {
//            writer=new IndexWriter(directory,new IndexWriterConfig(analyzer));
//            if(hasNew)
//            {
//                writer.deleteAll();
//            }
//            File file=new File("F:/lucene/lucenes");
//            Document doc=null;
//            for(File f:file.listFiles())
//            {
//                doc=new Document();
//                doc.add(new StringField("content",new FileReader(f)));//添加内容
//                doc.add(new Field("filename",f.getName(),Field.Store.YES,
//                        Field.Index.NOT_ANALYZED));//添加Name
//                doc.add(new Field("path",f.getAbsolutePath(),Field.Store.YES,
//                        Field.Index.NOT_ANALYZED));
//
//                doc.add(new StringField("path",file.getPath(),Field.Store.YES));
//
////                doc.add(new LongField("date", Field.Store.YES, true).setLongValue(f.lastModified());
//                doc.add(new LongField("date",file.lastModified(),Field.Store.YES));
////                doc.add(new NumericField("size",Field.Store.YES,true).setIntValue((int)f.length()));
//                doc.add(new LongField("size",file.length(),Field.Store.YES));
//                writer.addDocument(doc);
//            }
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (LockObtainFailedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally
//        {
//            if(writer!=null)
//            {
//                try {
//                    writer.close();
//                } catch (CorruptIndexException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}