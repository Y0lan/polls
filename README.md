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
1. Launch the database
   
    `docker-compose up -d`
2. Launch the back end
   - fill application.properties.example in polls/resources
   - fill information config.env.example
   
   `mvn spring-boot:run`
3. Launch the front end
   
   `// TODO`

## Database
The mysql server is inside a docker. 📦

The data of the database is persistant inside the sql/database folder. :rocket: 

You can put SQL script inside sql/scripts and they will be executed when the docker start up. :smile:





# Example Use
// TODO