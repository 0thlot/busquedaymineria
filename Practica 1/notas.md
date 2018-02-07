### Index Writer

##### IndexConfigWriter
* usamos Standard Analyzer
* setOpenMode (**CREATE** o CREATE_OR_APPEND)
##### Directory 
 *(RAMDirectory o **FSDirectory** )

### Document
* Field1 -> ruta zip/carpeta/txt
* Field2 -> texto Field(name,valor,fieldType)
    * Type setStoreTerms(true)
    * Type setIndexOption(IndexOptions, DOCS_AND_FREQS)
    
**Importante cerrarlo para continuar**

### Index Reader

* IndexReader -> DirectoryReader.open(...)

### Index Search
* Necesita un Index Reader

##### Query Parse
* Necesita un Analyzer que es el _IndexConfigWriter_
