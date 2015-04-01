import java.util.*;
import java.io.*;
 
public class fileCrawler {
 
  private WorkQueue my_workQ;
  static int i = 0;
 
 private class the_Worker implements Runnable {
 
  private WorkQueue my_queue;
 
  public the_Worker(WorkQueue queue) {
   my_queue = queue;
  }
 
  public void run() {
   String file_name;
   while ((file_name = my_queue.delete()) != null) {
    File my_file = new File(file_name);
    String file_list[] = my_file.list();
    if (file_list == null)
     continue;
    for (String files : file_list) {
     if (files.compareTo(".") == 0)
      continue;
     if (files.compareTo("..") == 0)
      continue;
     String print_file = file_name + "\\" + files;
     System.out.println(print_file);
    }
   }
  }
 }
 
 public fileCrawler() {
  my_workQ = new WorkQueue();
 }
 
 public the_Worker createWorker() {
  return new the_Worker(my_workQ);
 }
 
 
 public void search_Directory(String directory) {
   try{
   File file_name = new File(directory);
   if (file_name.isDirectory()) {
    String file_list[] = file_name.list();
    if (file_list != null)
     my_workQ.adding(directory);
 
    for (String search_file : file_list) {
     String sub_Directory;
     if (search_file.compareTo(".") == 0)
      continue;
     if (search_file.compareTo("..") == 0)
      continue;
     if (directory.endsWith("\\"))
      sub_Directory = directory+search_file;
     else
      sub_Directory = directory+"\\"+search_file;
     search_Directory(sub_Directory);
    }
   }}catch(Exception e){}
 }
 
 public static void main(String Args[]) {
 
  fileCrawler file_crawler = new fileCrawler();
 
 
  int number = 5;
  ArrayList<Thread> my_thread = new ArrayList<Thread>(number);
  for (int i = 0; i <number; i++) {
   Thread my_thread1 = new Thread(file_crawler.createWorker());
   my_thread.add(my_thread1);
   my_thread1.start();
  }
 

  file_crawler.search_Directory("C:/Users/Ali/Desktop");
 
 
  file_crawler.my_workQ.end();
 
  for (int i = 0; i <number; i++){
   try {

    my_thread.get(i).join();
   } catch (Exception e) {};
  }
 }
}
