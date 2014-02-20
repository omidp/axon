package org.axon.model;


public class CommentModel
{

    private Long id;
    private DocumentModel doc;
    private String description;

    
    
    public CommentModel(Long id, DocumentModel doc, String description)
    {
        this.id = id;
        this.doc = doc;
        this.description = description;
    }

    public CommentModel()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DocumentModel getDoc()
    {
        return doc;
    }

    public void setDoc(DocumentModel doc)
    {
        this.doc = doc;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public class Test{
        private String name;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
        
    }

}
