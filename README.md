This Project is based on creating a linkPreview tool, which works similar to the way of Facebook, Twitter and LinkedIn.


Setup:

1. clone the repository:

2. Import the project into an IDE.

3. I have used Maven in this project, so you need to run the following command in the terminal in the appropriate directory : i.e. run the following command in "springrest" directory.
      mvn clean install

4. After setting it up, run it in src/main/java/service/ LinkPreviewService.java file.

5. Port number 8080 will be used for the API endpoint. You can send request in the local system through the browser or Postman also. Requests should be sent in the following format:
    localhost:8080/api/preview?url= 

Specify the required url after the '=' symbol.
