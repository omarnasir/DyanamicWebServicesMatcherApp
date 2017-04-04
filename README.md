## Web Service Matcher

The project automatically matches output of an operation of a Web service with inputs of an operation of another Web service. It utilises two concepts: Syntactic matching, based on how similar 2 strings are in terms of the characters they contain and Semantic matching, based on how similar 2 strings are in terms of the meaning associated with each.

The algorithm for Syntactic matching is called "Edit Distance". More details can be found here:
http://www.algorithmist.com/index.php/Edit_Distance

As for Sematic matching, OWL web ontology language is used. OWlAPI is used to classify the ontology file, which contains all the annotations from sample WSDL files. The same API is used to perform the comparison, based on how similar the two strings are.

Initial parsing of all WSDL files is done using a generic DOM parser through recursive functions. It finds associated elements with each operation, and stores them in a locally hosted PostgreSQL database, which is later called when the actual comparison has to be performed.

<b>Further details can be found in the Project Report PDF.</b>
