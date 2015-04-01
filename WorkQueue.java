import java.util.*;
 
public class WorkQueue {
 
  private LinkedList<String> my_work_queue;
 private boolean check;  
 private int check_size;  
 
 public WorkQueue() {
  my_work_queue = new LinkedList<String>();
  check = false;
  check_size = 0;
 }
 
 public synchronized void adding(String s) {
	 
  my_work_queue.add(s);
  check_size++;
  notifyAll();
 }
 
 public synchronized String delete() {
  String str;
  while (!check && check_size == 0) {
   try {
    wait();
   } catch (Exception e) {};
  }
  if (check_size > 0) {
   str = my_work_queue.remove();
   check_size--;
   notifyAll();
  } else
   str = null;
  return str;
 }
 
 public synchronized void end() {
  check = true;
  notifyAll();
 }
}