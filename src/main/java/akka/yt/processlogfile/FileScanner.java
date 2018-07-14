package akka.yt.processlogfile;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.io.File;
import java.util.*;

import akka.yt.processlogfile.FileParser.Parse;

public class FileScanner extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  static public Props props(ActorRef fileParserActor) {
    return Props.create(FileScanner.class, () -> new FileScanner(fileParserActor));
  }

  // message
  static public class Scan {
    public List<String> fileNames = new ArrayList<String>();

    public Scan() {

      File dir = new File("src/main/resources/logfile");
      File[] dirArr = dir.listFiles();
      
      System.out.println("File count: " + dirArr.length);

      if (dirArr != null) {
        for (File file : dirArr) {
          fileNames.add(file.getName());
        }
        System.out.println(fileNames.toString());
      }else{ // need to enhance 
        System.out.println("Cannot find any file in folder.");
      }
    }
  }

  private final ActorRef fileParserActor;
  public FileScanner(ActorRef fileParserActor) {
    this.fileParserActor = fileParserActor;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(Scan.class, scan -> {
        // filescanner send message
        for(String fileName : scan.fileNames){
          fileParserActor.tell(new Parse(fileName), getSelf());
        }   
      })
        .build();
  }
}