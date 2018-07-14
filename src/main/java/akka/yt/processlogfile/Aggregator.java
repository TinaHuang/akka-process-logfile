package akka.yt.processlogfile;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class Aggregator extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  static public Props props() {
    return Props.create(Aggregator.class, () -> new Aggregator());
  }

  // messages
  static public class StartFile {
    public final String fileName;
    public StartFile(String fileName) {
      this.fileName = fileName;
    }
  }

  static public class EndFile {
    public final String fileName;
    public EndFile(String fileName) {
      this.fileName = fileName;
    }
  }
  public static Map countMap = new HashMap<String, String>();

  static public class Line {
    public final String fileName;
    public Integer counter;
    
    public Line(String fileName) {
      this.fileName = fileName;
      
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
      }catch(FileNotFoundException e)
      {
        e.printStackTrace();
      }
    }
  }

  public Aggregator() {
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(StartFile.class, startFile -> {
        log.info("[" + startFile.fileName + "] start-to-file");
      })
      .match(Line.class, line -> {
        log.info("[" + line.fileName + "] line");
      })
      .match(EndFile.class, endFile -> {
        log.info("[" + endFile.fileName +"] end-to-file" + " / Count: " + countMap.get(endFile.fileName));
      })
      .build();
  }
}