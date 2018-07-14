package akka.yt.processlogfile;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import akka.yt.processlogfile.Aggregator.StartFile;
import akka.yt.processlogfile.Aggregator.Line;
import akka.yt.processlogfile.Aggregator.EndFile;

public class FileParser extends AbstractActor {

  static public Props props(ActorRef aggregatorActor) {
    return Props.create(FileParser.class, () -> new FileParser(aggregatorActor));
  }

  // message
  static public class Parse {
    public final String fileName;
    
    public Parse(String fileName) {
      this.fileName = fileName;
    }
  }

  //private final String message;
  private final ActorRef aggregatorActor;

  public FileParser(ActorRef aggregatorActor) {
    this.aggregatorActor = aggregatorActor;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(Parse.class, parse -> {
        // start-to-file
        aggregatorActor.tell(new StartFile(parse.fileName), getSelf());
        
        // line
        try{   
          // process each file in folder
          String path = "src/main/resources/logfile/" + parse.fileName;
          File file = new File(path);
          Scanner sc = new Scanner(file);
          // send each line to Aggregator to count words
          while(sc.hasNextLine()){
            aggregatorActor.tell(new Line(parse.fileName, sc.nextLine()), getSelf());
          }
        }catch(FileNotFoundException e){
          e.printStackTrace();
        }
        
        // end-of-file
        aggregatorActor.tell(new EndFile(parse.fileName), getSelf());
      })
      .build();
  }
}