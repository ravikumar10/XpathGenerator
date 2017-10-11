# XpathGenerator

This tool will generate Xpaths for the Given Json and Xml files , and print them in the console.It will take multiple keys and print them with index 

I have made some changes in the java-json.jar to pick "content" as key in order to print content as node in the generated xpaths.

HOW TO USE ::
Copy the .jar files and the library, run the below command :

Json::   java -cp XpathGenerator.jar XpathGenerator -json test.json

Xml ::   java -cp XpathGenerator.jar XpathGenerator -xml test.xml

Sample Output :: java -cp XpathGenerator.jar XpathGenerator -json ../data.json

