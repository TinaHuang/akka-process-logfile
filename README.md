akka-process-logfile
--------------
Java application with Akka framework. Aims to process log files and count words in the files.


Getting Started
---------------

1. Install Java (1.7 or later) and maven: (In my case, I use [Homebrew](https://docs.brew.sh/Installation) to install)

        $ brew cask install java
        $ brew install maven

2. Clone this project:

        $ git clone git@github.com:TinaHuang/processlogfile.git 

3. At the command prompt, execute the project by maven:

        $ mvn compile exec:exec

4. And you'll see the output like this:

   (partial) 
        
        [file_1.txt] has [341] words.
        [file_2.txt] has [1] words.
        [file_3.txt] has [7] words.
         
   (full) 
        
        It's my first akka project!
        File count: 3
        [file_1.txt, file_2.txt, file_3.txt]
        >>> Press ENTER to exit <<<
        [INFO] [07/15/2018 00:26:48.369] [process-log-file-akka.actor.default-dispatcher-5] [akka://process-log-file/user/aggregatorActor] [file_1.txt] has [341] words.
        [INFO] [07/15/2018 00:26:48.373] [process-log-file-akka.actor.default-dispatcher-5] [akka://process-log-file/user/aggregatorActor] [file_2.txt] has [1] words.
        [INFO] [07/15/2018 00:26:48.374] [process-log-file-akka.actor.default-dispatcher-4] [akka://process-log-file/user/aggregatorActor] [file_3.txt] has [7] words.

Documentation
-------------
### Actors and their messages
- `FileScanner`: `Scan`
- `FileParser`: `Parse`
- `Aggregator`: `StartFile`, `Line`, `EndFile`

![flow](https://user-images.githubusercontent.com/2779350/42731455-3349b138-8840-11e8-80ce-5594bbcd3fc5.PNG)
### Code path 
[src/main/java/akka/yt/processlogfile](https://github.com/TinaHuang/akka-process-logfile/tree/master/src/main/java/akka/yt/processlogfile) 
### Sample logfile (.txt)
[src/main/resources/logfile/](https://github.com/TinaHuang/akka-process-logfile/tree/master/src/main/resources/logfile)

Reference
---------
- [Akka documentation](http://doc.akka.io/docs/akka/current/java.html)
- [Akka Quickstart with Java](https://developer.lightbend.com/guides/akka-quickstart-java/?_ga=2.186991080.1584947361.1531634165-505394620.1531291631?_ga=2.186991080.1584947361.1531634165-505394620.1531291631?_ga=2.186991080.1584947361.1531634165-505394620.1531291631?_ga=2.186991080.1584947361.1531634165-505394620.1531291631)
