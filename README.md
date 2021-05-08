<!-- Project Logo -->
<h3 align="center">
<a href="/" style="text-decoration: none">
  <img src='readme-image/img.png' width=1200>
  <br/>
  polls<sup>&#153;</sup>
</a></h3>

<!-- Bullets -->
<p align="center">
  Simple &bull;
  Useful &bull;
  Pretty
</p>

-----
# Polls API
Polls is a made up polls API. It provides features like:
- Create account
- Create and manage polls
- Share the polls
- //TODO find another API to use

# Documentation
## Start the app
### Edit the configuration files
`cp db.env.example db.env`

**Edit db.env to add the root of mysql password**


`cp src/main/resources/application.properties.example src/main/resources/application.properties`

### Build the app 

`mvn clean package -DskipTests`

### Start the app
 `docker-compose up -d`
   
3. Launch the front end
   
   `// TODO`

## Database
The mysql server is inside a docker. 📦

The data of the database is persistant inside the sql/database folder. :rocket: 


# Example Use
// TODO
