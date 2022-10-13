package com.parseridea.powerpointparser.Iterator;

/**
 * Iteration interface
 */
public interface Iterator {
    /**
     * @return has next object
     */
    boolean hasNext(int mode);

    /**
     * @return next object
     */
    Object next();

    /**
     * @param num of object in object-list
     * @return replaced object
     */
    Object replace(int num);

    /**
     * go to first object
     */
    void preview();

    /**
     * @return has previous object
     */
    boolean hasPrev(int mode);

    /**
     * @return go to previous object
     */
    Object prev();

    /**
     * @return position in object-list
     */
    int getCurrent();

}