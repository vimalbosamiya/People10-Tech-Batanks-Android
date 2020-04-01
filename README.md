# Batanks

Process to generate Swagger Model

Download the Swagger jar from the below URL
wget https://oss.sonatype.org/content/repositories/releases/io/swagger/swagger-codegen-cli/2.2.1/swagger-codegen-cli-2.2.1.jar

Once we have the above Jar locally 
use the following command to generate the model

java -jar swagger-codegen-cli-2.2.1.jar generate -i http://93.90.204.56/doc/?format=openapi -l java --library=retrofit2
