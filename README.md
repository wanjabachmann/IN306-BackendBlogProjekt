# Backend Blog IN306 Verteilte Systeme

As part of the course we will implement a backend for a blog.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/blog-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.


# Manual Testing

The swagger ui can be opened by the following link:
http://localhost:8080/q/swagger-ui

# Token-Auth concept

| Tag      | Methods | Permission / Role |
| -------- | ------- | ----------------- |
| Authors  | GET     | public            |
| Authors  | POST    | user              |
| Authors  | PUT     | user              |
| Authors  | DELETE  | administrator     |
| Blogs    | GET     | public            |
| Blogs    | POST    | user              |
| Blogs    | PUT     | user              |
| Blogs    | DELETE  | user              |
| Comments | GET     | public            |
| Comments | POST    | public            |
| Comments | PUT     | administrator     |
| Comments | DELETE  | administrator     |


## httpie
### Blogs
Get Blogs
```Shell
http :8080/blogs
```

Post Blogs
```Shell
http -v POST :8080/blogs title="Blog Title" content="This is a blog content"
```

```Shell
http -v POST :8080/blogs title="Blog Title 2" content="This is a blog content 2"
```

```Shell
http -v POST :8080/blogs title="Blog Title 3" content="This is a blog content 3"
```

Delete Blog
```Shell
http -v DELETE :8080/blogs/1
```

PUT
```Shell
http -v POST :8080/blogs title="Blog Title 2" content="This is a blog content 2" 

http -v PUT :8080/blogs/1 title="Blog PUT" content="PUT new content"
```

### Authors
Get Authors
```Shell
http -v GET :8080/authors
```

Post Authors
```Shell
http -v POST :8080/authors firstname="Wanja" lastname="Bachmann" 
```

Delete Authors
```Shell
http -v DELETE :8080/authors/1
```

Search for an Author by URL Parameter
```URL
http://localhost:8080/authors?search=Wanja
```

PUT
```Shell
http -v POST :8080/authors firstname="Wanja" lastname="Bachmann" 

http -v PUT :8080/authors/1 firstname="Thomas" lastname="Smith"
```

### Comments
Post Comments
```Shell
http -v POST :8080/blogs title="Blog Title" content="This is a blog content"

http -v POST :8080/blogs/1/comments content="This is a comment." creator="Hans Müller"

http -v POST :8080/blogs/1/comments content="This is a second comment." creator="Max Muster"

```

Get Comments
```Shell
http -v GET :8080/blogs/1/comments
```

PUT Comments
```Shell
http -v PUT :8080/blogs/comments/1 content="Updated comment" creator="Thomas Müller"
```

Delete Comments
```Shell
http -v DELETE :8080/blogs/comments/2
```

# API Design

```yaml
openapi: 3.0.0
info:
  title: Blog API
  version: 1.0.0
paths:
  /authors:
    get:
      summary: Retrieve all authors
      operationId: getAllAuthors
      responses:
        '200':
          description: Query Successful
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Author'
    post:
      summary: Create a new author
      operationId: createAuthor
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Author'
      responses:
        '201':
          description: Author created
  /authors/{id}:
    get:
      summary: Retrieve an author by ID
      operationId: getAuthorById
      parameters:
        - in: path
          name: id
          schema:
            type: integer
          required: true
          description: ID of the author to retrieve
      responses:
        '200':
          description: Query Successful
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Author'
        '404':
          description: Author not found
    put:
      summary: Update an author
      operationId: updateAuthor
      parameters:
        - in: path
          name: id
          schema:
            type: integer
          required: true
          description: ID of the author to update
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Author'
      responses:
        '200':
          description: Author updated successfully
        '404': 
          description: Author not found
    delete:
      summary: Delete an author
      operationId: deleteAuthor
      parameters:
        - in: path
          name: id
          schema:
            type: integer
          required: true
          description: ID of the author to delete
      responses:
        '204':
          description: Author successfully deleted
        '404': 
          description: Author not found
          
  /blogs:
    get:
      summary: Retrieve all blogs
      operationId: getAllBlogs
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Blog'
    post:
      summary: Create a new blog
      operationId: createBlog
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Blog'
      responses:
        '201':
          description: Blog created
  /blogs/{id}:
    get:
      summary: Retrieve a specific blog
      operationId: getBlogById
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: Successful
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Blog'
        '404':
          description: Blog not found
    put:
      summary: Update a specific blog
      operationId: updateBlog
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Blog'
      responses:
        '200':
          description: Blog successfully updated
        '404':
          description: Blog not found
    delete:
      summary: Delete a specific blog
      operationId: deleteBlog
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '204':
          description: Blog successfully deleted
        '404':
          description: Blog not found
components:
  schemas:
    Author:
      type: object
      properties:
        id:
          type: integer
        firstname:
          type: string
        lastname:
          type: string
        creationDate:
          type: string
          format: date-time
        blogs:
          type: array
          items:
            $ref: '#/components/schemas/Blog'
    Blog:
      type: object
      properties:
        id:
          type: integer
        title:
          type: string
        content:
          type: string
        authors:
          type: array
          items:
            $ref: '#/components/schemas/Author'

```

# Changelog
## New
- docs: :memo: Add Token-Auth concept
- feat: :alien: Add Blog Overview dto

## Old
- feat: Add repsonse for created comments to create get the URI
- docs: :memo: Update the readme with the changes and unfinished tasks as well as the updated httpie queries
- test: :test_tube: Add junit tests for Author and  Comments and remove some fix some parts on the Blog tests
- fix: :bug: Fix changes in BlogResrouce and remove not needed imports
- build: :building_construction: add datatype dependency to make localdate running
- fix: :bug: Fix some dto's to make everything running.
- test: :test_tube: Add System test to test the REST API by RestAssured
- test: :test_tube: Add Junit Test for the BlogService
- Merge pull request #5 from wanjabachmann/add_design_and_implement_it
- docs: :memo: Update the readme with the latest httpie tests
- fix: :bug: Fix the return type for Author PUT method
- feat: :safety_vest: Implement the DTO's on the BlogResource
- feat: :safety_vest: Add DTO for Comment entity for the PUT & PATCH methods
- feat: :safety_vest: Add DTO for Blog entity for the PUT & PATCH methods
- feat: :safety_vest: Add DTO for Author entity for the PUT & PATCH methods
- feat: :safety_vest: Add validation to the entities
- feat: :necktie: Add comment entity
- feat: :goal_net: Add response/error handling for all Blog Resources
- feat: :sparkles: Add find methods for Author and Blog to search in the list by a string from a query parameter.
- feat: :safety_vest: Add the quarkus-hibernate-validator extension
- Add tag as title
- Merge pull request #4 from wanjabachmann/add_design_and_implement_it
- docs: :memo: Updates the readme file with the yaml design and the updated changelog
- feat: :necktie: Adds methode getAuthorById and updateAuthor for get and put call ands a customized APIRepsonse
- refactor: :heavy_minus_sign: Removes unused import
- feat: :necktie: Adds methode getBlogById and updateBlog for get and put call
- feat: :necktie: Adds creation date to Blog and Author
- test: :test_tube: Updates the Blog and Author tests to support creation date as additional variable
- Merge pull request #3 from wanjabachmann/add_RESTEasy
- docs: :memo: Adds the author httpie cmdlets to the README and updates the changelog
- feat: :sparkles: Implements the author anotations
- style: :art: Removes unused imports
- feat: :sparkles: adds the remove method to delete a Author by Id
- docs: :memo: Updates the README file with usefull infos about the changes
- feat: :sparkles: Adds the methods and anotations to the BlogResource
- feat: :sparkles: Adds the removeBlogById method to be able to remove blogs
- build: :heavy_plus_sign: Adds the dependency smallrye-openapi
- style: :truck: Moves all files one folder up to the main folder and move the services into the control package
- docs: :memo: Updates the Changelog within  the README.md file
- Merge pull request #1 from wanjabachmann/add_db_access
- feat: :lipstick: Change start page from hello to blog
- feat: Add the service files for the entities blog and author
- feat: add classes author and blog
- test: :white_check_mark: add the junit tests for the entities author and blog
- feat: :card_file_box: Add repository which implements PanacheRepository
- build: :heavy_plus_sign: Add the depenencies lombok and hibernate panache
- refactor: :fire: Removes unused files from previous tests and the default setup.
- feat: Creates the Quarkus project and a first customization from the Projekt-Setup task in moodle
- Initial commit

# Stuff not working / unfinished / Questions
- DTO's are implemented for all update and add methods. I tired to implement the same for the get blogs method
but it's not visible in the Swagger UI.
- Junit tests implemented for all entites but I was not able to get the PUT Junit tests working. 
As well as the testRemoveCommentById :(