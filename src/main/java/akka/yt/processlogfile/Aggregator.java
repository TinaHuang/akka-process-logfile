package akka.yt.processlogfile;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.*;


public class Aggregator extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  static public Props props() {
    return Props.create(Aggregator.class, () -> new Aggregator());
  }

  // messages
  // start-to-file
  static public class StartFile {
    public final String fileName;
    public StartFile(String fileName) {
      this.fileName = fileName;
    }
  }

  // end-to-file
  static public class EndFile {
    public final String fileName;
    public EndFile(String fileName) {
      this.fileName = fileName;
    }
  }

  // line
  public static Map countMap = new HashMap<String, Integer>();

  static public class Line {
    public final String fileName;
    public final String line;
    public Integer counter;
    
    public Line(String fileName, String line) {
      
      this.fileName = fileName;
      this.line = line;
      counter = countMap.containsKey(fileName) ? (Integer) countMap.get(fileName) : 0;

      
      String [] words = line.split(" ");
      counter+= words.length;
      
      countMap.put(fileName, counter);
      
      /*
      try{
        
        counter = 0;    
        String path = "src/main/resources/logfile/" + fileName;
        File file = new File(path);

        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()){
          String [] words = sc.next().split(" ");
          counter+= words.length;
        }

        countMap.put(fileName, String.valueOf(counter));

      }catch(FileNotFoundException e){
        e.printStackTrace();
      }
      */
    }
  }

  public Aggregator() {
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(EndFile.class, endFile -> {
        log.info("[" + endFile.fileName +"] has [" + countMap.get(endFile.fileName) + "] "+ "words.");
      })
      .build();
  }
}