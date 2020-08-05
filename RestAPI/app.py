#!flask/bin/python
from flask import Flask, jsonify, abort, request, make_response, url_for
from flask_restful import Resource, Api
from flask_httpauth import HTTPBasicAuth
import os, json
import sys

app = Flask(__name__)
api = Api(app)
#app.json_encoder = MyJSONEncoder


filenames = []

## FETCH DIRECTORY FROM COMMAND LINE ARGUMENTS, WHICH CONTAINS DATA SET: GENERATED FROM SPARK CODE.
directory = sys.argv[1]+"\\"
print(directory)

## FETCH ALL PART FILES FROM THE DIRECTORY
for file in os.listdir(directory):
    filename = os.fsdecode(file)
    if filename.endswith('.json'): # whatever file types you're using...
        filenames.append(filename)
print(filenames)

## READ ALL THE FILE CONTENTS (JSON DATA) LOAD TO REST API
output =[]

for f in filenames:
	data = [json.loads(line) for line in open(directory+f, 'r')]
	#print(data)
	i = 0
	while i < len(data):
		output.append(data[i])
		i += 1
finaloutput = {"get_flu_per_country":output}

#for f in filenames:
#	with open(directory+f) as f:
#		if len(f.readlines()) != 0:
#			f.seek(0)
#			print(f)
#			jsondata = json.load(f)
			#output.append(jsondata)
#finaloutput = {"get_flu_per_country":"output"}


class FluPerCountry(Resource):
	def get(self):
		return jsonify(finaloutput)

	def post(self):
		data = request.get_json()
		totalCountry = []
		for attrs in finaloutput['get_flu_per_country']:
			if str(attrs['Country'].lower()) == data['Country'].lower():
				Country = str(attrs['Country'])
				print(Country)
				Year = str(attrs['Year'])
				Total_AH1 = str(attrs['Total_AH1'])
				Total_AH1N12009 = str(attrs['Total_AH1N12009'])
				Total_AH3 = str(attrs['Total_AH3'])
				Total_AH5 = str(attrs['Total_AH5'])
				Total_ANOTSUBTYPED = str(attrs['Total_ANOTSUBTYPED'])
				MostActiveInfluenza = str(attrs['MostActiveInfluenza'])
				NumberOfSpecimenProcessed = str(attrs['NumberOfSpecimenProcessed'])
				TotalNumberOfInfluenzaPositiveVirus = str(attrs['TotalNumberOfInfluenzaPositiveVirus'])
				
				filteredRecord = {"Country":Country, "Year":Year, "MostActiveInfluenza":MostActiveInfluenza, "NumberOfSpecimenProcessed":NumberOfSpecimenProcessed, "TotalNumberOfInfluenzaPositiveVirus":TotalNumberOfInfluenzaPositiveVirus, "Total_AH1":Total_AH1, "Total_AH1N12009":Total_AH1N12009, "Total_AH3":Total_AH3, "Total_AH5": Total_AH5, "Total_ANOTSUBTYPED": Total_ANOTSUBTYPED }
				print(filteredRecord)
				totalCountry.append(filteredRecord)
		#return jsonify(totalCountry)
		if(not totalCountry):
			print("No Country Found")
			return {"Error": "No country found, please try another"}
		else:
			print("inside if")
			return jsonify(totalCountry)	

api.add_resource(FluPerCountry,'/get_flu_per_country')
    
if __name__ == '__main__':
    app.run(debug = True)