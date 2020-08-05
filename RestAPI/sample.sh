
cd "$SPARK_HOME"

./spark-submit.cmd --class Influenza  --master local --jars /Users/pc/Downloads/FluNetInteractiveReport/target/FluNetInteractiveReport-1.0-SNAPSHOT.jar local /Users/pc/Downloads/SampleDemo/src/input /output
