public class Counter{
     
     volatile int count = 0;
    
     public void add(long value){
       this.count += value;
     }
  }