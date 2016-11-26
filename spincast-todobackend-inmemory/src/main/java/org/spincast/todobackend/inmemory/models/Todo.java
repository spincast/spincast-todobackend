package org.spincast.todobackend.inmemory.models;

/**
 * The Todo model.
 */
public interface Todo {

    /**
     * Gets the id. 
     * 
     * @return the id or <code>null</code> if the
     * Todo has not been saved in the repository yet.
     */
    public Integer getId();

    /**
     * Sets the id.
     */
    public void setId(int id);

    /**
     * Gets the title.
     */
    public String getTitle();

    /**
     * Sets the title
     */
    public void setTitle(String title);

    /**
     * Is the Todo completed?
     */
    public boolean isCompleted();

    /**
     * Sets completed or not.
     */
    public void setCompleted(boolean completed);

    /**
     * Gets the unique URL to this Todo.
     */
    public String getUrl();

    /**
     * Gets the order.
     */
    public int getOrder();

    /**
     * Sets the order.
     */
    public void setOrder(int order);

}
