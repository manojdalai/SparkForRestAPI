Prequisite:
----------
Spark
Java
Flask
flask_restful
python
InteliJ or Eclipse
Maven

Environment Used:
-----------------
Windows 10
Spark 2.x
Java 1.8
Python 3
GitBash
cmd
Postman



OBJECTIVE 1 (Job executed on GitBash)
	COMMAND TO BE EXECUTED IN WINDOW LOCAL MODE:
	-------------------------------------------
	cd "$SPARK_HOME"/bin
	./spark-submit.cmd --class Influenza  --master local --jars <location of the jar file> Influenza <provide Input file location> <provide output path location> 

	EXAMPLE: COMMAND USED IN MY SYSTEM IN GITBASH:
	----------------------------------------------
	cd "$SPARK_HOME"/bin
	./spark-submit.cmd --class Influenza  --master local --jars file:///C:/Users/pc/Downloads/FluNetInteractiveReport/target/FluNetInteractiveReport-1.0-SNAPSHOT.jar Influenza file:///C:/Users/pc/Downloads/SampleDemo/src/input file:///C:/Users/pc/Downloads/FluNetInteractiveReportsrc/main/resources/output



OBJECTIVE 2 (Job Executed on cmd)
		# Create and change Directory RestAPI on Windows
		mkdir RestAPI
		cd RestAPI
		
		# Create a new virtual environment named "env"
		python -m venv env

		# Turn virtual environment on (Windows)
		env\Scripts\activate
		
		# Install flask
		pip install flask
		pip install flask_restful
		
		# set flask app
		SET FLASK_APP=app.py
		
		# Copy app.py from mail to RestAPI directory
		
		# Run the app.py 
		python app.py <Provide path to SPark job output directory>
		Example: python app.py C:\Users\pc\Downloads\FluNetInteractiveReport\src\main\resources\output
		
		# access api using endpoint http://127.0.0.1:5000/get_flu_per_country
		
		# POST to endpoint using Postman
		

MAIL ATTACHMENTS FOR YOUR REFERENCE:
---------------------------------
Spark Artifact
app.py
Snapshot or Rest POST call
get_flu_per_country_output.json