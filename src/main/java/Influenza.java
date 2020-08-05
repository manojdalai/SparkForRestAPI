import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.input_file_name;
import static org.apache.spark.sql.types.DataTypes.StringType;


public class  Influenza {
    public static void main(String args[]) {

        /*String path2 = "src/main/resources/input/";
        String path3 = "src/main/resources/output/";

        String inputdatapath = new File(path2).getAbsolutePath();
        String outputdatapath = new File(path3).getAbsolutePath();*/

        String inputdatapath = args[0];
        System.out.println(inputdatapath);
        System.out.println("--------------------------------");
        String outputdatapath = args[1];


        System.out.println(inputdatapath);
        System.out.println(outputdatapath);

        /*Creted Empty list to hold column name after parsing DML file*/
        List<String> schema=new ArrayList<String>();
        Map<String,String> datahashmap = new HashMap<>();
        /*Created Spark session*/
        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("Influenza")
                .getOrCreate();


        /*Reading DML file*/
        Dataset<Row> df = spark.read()
                .format("com.databricks.spark.csv")
                .option("header", "true") // Use first line of all files as header
                .option("inferSchema", "true") // Automatically infer data types
                .option("delimiter", ",")
                .load(inputdatapath);

        //df.show(false);

        df.registerTempTable("tempFluView1");

        //df = spark.sql("SELECT query, stringLengthTest(filename) as filename from temp");
        Dataset<Row> df2 = spark.sql("SELECT " +
                "Country, Year, " +
                "sum(SPEC_PROCESSED_NB) as NumberOfSpecimenProcessed, " +
                "sum(ALL_INF) as TotalNumberOfInfluenzaPositiveVirus, " +
                "COALESCE(sum(AH1),0) as Total_AH1, " +
                "COALESCE(sum(AH1N12009),0) as Total_AH1N12009, " +
                "COALESCE(sum(AH3),0) as Total_AH3, " +
                "COALESCE(sum(AH5),0) as Total_AH5, " +
                "COALESCE(sum(ANOTSUBTYPED),0) as Total_ANOTSUBTYPED " +
                "from tempFluView1 " + //"where Country in(\"Norway\",\"Finland\",\"Denmark\",\"Sweden\") " +
                "WHERE Country in('Finland', 'Norway', 'Denmark', 'Sweden') " +
                "GROUP BY Country, Year");

        df2.show(false);
        df2.registerTempTable("tempFluView2");
        Dataset<Row> df3 = spark.sql("SELECT "+
                "Country, Year, " +
                "Total_AH1N12009, " +
                "Total_AH3, " +
                "Total_AH5, " +
                "Total_ANOTSUBTYPED, " +
                "Total_AH1, " +
                "NumberOfSpecimenProcessed, " +
                "TotalNumberOfInfluenzaPositiveVirus, " +
                "CASE " +
                    "WHEN Total_AH1 >= Total_AH1N12009 " +
                        "AND Total_AH1 >= Total_AH3 " +
                        "AND Total_AH1 >= Total_AH5 " +
                        "AND Total_AH1 >= Total_ANOTSUBTYPED THEN 'AH1' " +
                    "WHEN Total_AH1N12009 >= Total_AH1 " +
                        "AND Total_AH1N12009 >= Total_AH3 " +
                        "AND Total_AH1N12009 >= Total_AH5 " +
                        "AND Total_AH1N12009 >= Total_ANOTSUBTYPED THEN 'AH1N12009' " +
                    "WHEN Total_AH3 >= Total_AH1 " +
                        "AND Total_AH3 >= Total_AH1N12009 " +
                        "AND Total_AH3 >= Total_AH5 " +
                        "AND Total_AH3 >= Total_ANOTSUBTYPED THEN 'AH3' " +
                    "WHEN Total_AH5 >= Total_AH1 " +
                        "AND Total_AH5 >= Total_AH3 " +
                        "AND Total_AH5 >= Total_AH1N12009 " +
                        "AND Total_AH5 >= Total_ANOTSUBTYPED THEN 'AH5' " +
                    "ELSE 'ANOTSUBTYPED' " +
                "END as MostActiveInfluenza "+
                "FROM tempFluView2");
        //df3.show(false);

        df3.write().json(outputdatapath);

    }
}
