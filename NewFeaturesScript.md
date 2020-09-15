#New Script Features
## Variables
Set a (global) variable:

    #name = "John Smith"
    text inputIdOne #name

Inside a subscript return a variable

    return #name

Or return a constant

    return "Hello John"
    
Receive the content of the return

    #result = run getInputValue
    
Get a text from a inputField and insert it in a variable

    #textValue = getValue inputIdOne
    
Assert the content of a variable variable

    assertText #textValue "Hello John"
    
Assert that the content is not equal the variable (!)

    assertTextNotExist #textValue "Hello John"
    
Show the content of all variables (which results in an image)

    listVariables

Read content of textfile into variable (identify dynamically the encoding)

    #json = readFile mySettings.json
    
Replace something inside a variable. In this example it replace the name John with Jack inside the "#json" variable.

    #json = replace #json "John" "Jack"
    
Write a variable to a file (Default is UTF-8)

    writeFile myNewFile.json #json 

## HTTP(s) Connection
### CRUD REST operations
All typical CRUD operations should be accessible in a easy way

#### CREATE

    #json = readFile mySettings.json
    #url = https://localhost:8080/item
    POST #url #json
    
#### READ
     
    #listOfItems = GET https://localhost:8080/item
    assertText #listOfItems "John"
    #itemJohn = = GET https://localhost:8080/item/1
    assertText #itemJohn "John"

#### UPDATE
    #json = replace #json "John" "Jack"
    PUT https://localhost:8080/item #json
    
#### DELETE

    DELETE https://localhost:8080/item/1
    
### HTTP Header Informations
Header Informations are "prepared" in advanced for the REST Call (or just HTTP Call) with a json file.

    #header = readFile myHeader.json 
    HEADER #header
    
And the header.json looks like this:

    {
      "Content-Type":"application/json"
      ...
    }

Or you can set single values (it is like a map)

    HEADER "Content-Type" "application/json"
    or
    HEADER Content-Type application/json
    
Naturally you need the possibility to reset the header:

    HEADER RESET
    
### Base64 Encoding/Decoding

#### Encode

    #pdf = readFile myDocument.pdf
    #base64Pdf = BASE64ENCODE #pdf
    
#### Decode
    
    #pdf = BASE64DECODE #base64Pdf
