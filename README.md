# XpathGenerator

This tool will generate Xpaths for the Given Json and Xml files , and print them in the console.It will take muyltiple keys and print them with index ie; for eg, rcs-data[3]
I have made some changes in the java-json.jar to pick "content" as key in order to print content as node in the generated xpaths.

HOW TO USE ::
Copy the .jar files and the library, run the below command :

Json::   java -cp XpathGenerator.jar XpathGenerator -json test.json

Xml ::   java -cp XpathGenerator.jar XpathGenerator -xml test.xml

Sample Output :: java -cp XpathGenerator.jar XpathGenerator -json ../data.json

//rcs-data[1]/sip-call-id[1]/text()='00001000000001145806829'
//rcs-data[1]/feature-tag[1]/text()='urn:urn-7:3gpp-service.ims.icsi.oma.cpm.largemsg'
//rcs-data[1]/conversation-id[1]/text()='005056884776-56e4-52c85700-5-56e85b35-a692a'
//rcs-data[1]/p-asserted-service[1]/text()='urn:urn-7:3gpp-service.ims.icsi.oma.cpm.largemsg'
//rcs-data[1]/contribution-id[1]/text()='005056884776-56e4-52c85700-6-56e85b35-a694f'
