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

## httpie

### Blogs
Get all Blogs
```Shell
http :8080/blogs
```

Post Blogs
```Shell
http -v POST :8080/blogs title="Blog Title" content="This is a blog content"
```

Delete a Post by specify the id
```Shell
http -v DELETE :8080/blogs/1
```

### Authors
Get Authors
```Shell
http -v GET :8080/blogs/authors
```

Post Authors
```Shell
http -v POST :8080/blogs/authors firstname="Wanja" lastname="Bachmann" 
```

Delete Authors
```Shell
http -v DELETE :8080/blogs/authors/1
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
- feat: :necktie: Adds methode getAuthorById and updateAuthor for get and put call ands a customized APIRepsonse
- refactor: :heavy_minus_sign: Removes unused import
- feat: :necktie: Adds methode getBlogById and updateBlog for get and put call
- feat: :necktie: Adds creation date to Blog and Author
- test: :test_tube: Updates the Blog and Author tests to support creation date as additional variable


## Old
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

# Stuff not working / unfinished
- At the moment is just one Custom Response implemented (for authors)
- I was not able to get the Author relationship working