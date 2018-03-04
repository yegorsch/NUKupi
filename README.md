[![Build Status](https://travis-ci.org/yegorsch/NUKupi.svg?branch=master)](https://travis-ci.org/yegorsch/NUKupi)

Please do before commit:

The auto-formatting shortcut in IntelliJ

For windows Ctrl+Alt+L.

For ubuntu Ctrl+Alt+windows+L

For mac cmd+alt+L


# Adding a product

1. Generate unique product id (length=15) or use ours at */rest/id*
1. Make a post request to *http://localhost:8080/Nukupi/rest/products* with json like:

    ```json
    {"title":"Eggs",
    "description":"and ham",
    "category":"food",
    "price":1000,
    "images":[],
    "authorID":"1",
    "ID":"uniqueIDfsfaf"}
    ```
    
If you have any images that you need to upload, post them one-by-one to *http://localhost:8080/Nukupi/rest/images/PRODUCT_ID*.
If upload is successfull, you will get a json looking like this:

   ```json
    {"date":1520173415,
    "size":231437.0, 
    "id":"2Z9gHhAIxXcIxuF"} // unix date, size in bytes, image id
   
   ```


 
You can repeat the procedure above for every image that you need to upload.
After that, the product should have the following json representation: 

   ```json
    {"title":"Eggs",
    "description":"and ham",
    "category":"food",
    "price":1000,
    "images":["2Z9gHhAIxXcIxuF"],
    "authorID":"1",
    "ID":"uniqueIDfsfaf"}
   ```
# Downloading an image
In order to download an image make a GET request to 
*http://localhost:8080/Nukupi/rest/images?id=IMAGE_ID*