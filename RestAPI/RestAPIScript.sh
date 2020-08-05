#!/bin/bash

cd "$SPARK_HOME"/bin
./spark-submit.cmd --class Influenza  --master local --jars file:///C:/Users/pc/Downloads/FluNetInteractiveReport/target/FluNetInteractiveReport-1.0-SNAPSHOT.jar Influenza file:///C:/Users/pc/Downloads/SampleDemo/src/input file:///C:/output

cd ~/Desktop/RestAPI
source env/Scripts/activate
#python app.py C:/Users/pc/Downloads/SampleDemo/src/output/FluNetInteractiveReport/
python app.py C:/Users/pc/Downloads/FluNetInteractiveReport/src/main/resources/output/


