# Octus Java Developer Test

## Spec

>Both spec (frontend and backend) is developed in the same solution, they are integrated each other.
>It is builded over maven (https://github.com/salomax/octus-test/blob/master/pom.xml).
>
>The solution is a simple and basic CRUD of texts, or texts items, you can list all, search by partial text, insert new one or update it, besides remove it too. 

### Frontend
	
Create web page that shows a list of items. The list must be:

  1. Editable
    a. Specifically by double clicking the list item
  2. Searchable
  3. Sortable
	
You can use any open-source framework you like, although jquery & backbone.marionette are preferred.

> The Frontend aims a responsive design and is single-page application designed.
>
> It is composed of:
>   - HTML5, CSS3 and JS
>   - JQuery (plugins jquery.cookie)
>   - Bootstrap (plugins bootstrap-table)
>   - modernizr
>
> The third lib is linked by CDNs.
> All features requested above is implemented.
> Chrome and Firefox browser validated the version. 

### Backend

Develop a Rest API that has the following functions:

1. Receive a JSON String from a web page and store it in a mongoDB collection
2. Displays all stored strings
3. Delete one of the stored strings
4. Edit one of stored strings

Suggested Tools: maven, spring-webmvc, mongo-java-driver, junit, mockito

* The result, should be a solution that compiles, builds and runs with nothing other than maven
* Focus on testing, we will be looking for the signs of TDD :)
* As you will be focusing on testable code, we will looking for clean separation of concerns between the layers
* You can use any library that is available in maven central
* Extra points will be awards for a complete solution that uses token based authentication

> The backend is a multi-layer application and it exposes its features by a REST API.
>
> It is composed of:
>   - Spring MVC
>   - Spring Security
>   - MongoDB
>   - TDD (unit test and integration test)
>   - Libs (commons-lang3, mockito, hamcrest, jackson)
>   - The layers are separated by 
>      - View (HTML pages)
>      - Controller (Rest)
>      - Model (Service/DAO)
>   - The security layer is builded with spring security and the authentication is token-based as resquested.

## API

List all text item

	Request GET /api/text
	Response [  { "id":"", "text":""  } ]

Insert or Update

	Request POST /api/text
	Response { "id":"", "text":""  }
	
	PUT /api/text/{id}
	No Response

DELETE

	DELETE /api/text/{id}
	No Response
	
## Security Layer

The REST API requests a valid token by the header param "X-Auth-Token". 

After login the user is authenticated and it received a token. From there, this token is enough to request any REST API method, in any client (web browser, mobile device, etc...). 

	
# Notes

## Download Version
	
In the project dir:

	$ git clone https://github.com/salomax/octus-test.git

## To compile and pack

	$ mvn clean install

Install the war file on your java web server.

## Installing mongodb in ubuntu 15.04

I faced problems installing mongodb 3.2 in ubuntu 15.04. See https://jira.mongodb.org/browse/SERVER-17742.

I've decided to downgrade mongodb to 2.6 using debian repo as answer in http://askubuntu.com/questions/617097/mongodb-2-6-does-not-start-on-ubuntu-15-04

	$ sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
	$ echo 'deb http://downloads-distro.mongodb.org/repo/debian-sysvinit dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
	$ sudo apt-get update
	$ sudo apt-get install mongodb-org
	$ sudo service mongod start
	
Others utils cmds

	$ sudo service mongod stop
	$ mongod --version
	$ tail -f /var/log/mongodb/mongod.log #check if is ok
	
	$ netstat -tulpn | grep 27017
	(Not all processes could be identified, non-owned process info
	 will not be shown, you would have to be root to see it all.)
	tcp        0      0 127.0.0.1:27017         0.0.0.0:*               LISTEN      - 
	
## Ready

Access 
	(http://localhost:8080/octus-salomax-test)
