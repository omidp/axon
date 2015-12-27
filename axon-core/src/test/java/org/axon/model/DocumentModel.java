package org.axon.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentModel
{

    private long id;
    private String name;
    private DocumentModel parent;
    private List<DocumentModel> children = new ArrayList<DocumentModel>();
    private List<CommentModel> comments = new ArrayList<CommentModel>();

    
    
    public DocumentModel(long id, String name, DocumentModel parent)
    {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public DocumentModel(String name, DocumentModel parent, List<DocumentModel> children, List<CommentModel> comments)
    {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.comments = comments;
    }

    public DocumentModel()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DocumentModel getParent()
    {
        return parent;
    }

    public void setParent(DocumentModel parent)
    {
        this.parent = parent;
    }

    public List<DocumentModel> getChildren()
    {
        return children;
    }

    public void setChildren(List<DocumentModel> children)
    {
        this.children = children;
    }

    public List<CommentModel> getComments()
    {
        return comments;
    }

    public void setComments(List<CommentModel> comments)
    {
        this.comments = comments;
    }

}
