package com.matf.filemanager.versions

/**
 * Support interface for objects that keep a history of states
 *
 * @param <T> Type of the object whose history to keep
 */
interface Versionable<T> {

    /**
     * Current version of the object
     */
    fun getCurrentInstance(): T?

    /**
     * Transition to a new state
     *
     * @param newElement New state to go to
     * @return Was the state change successful
     */
    fun goTo(newElement: T): Boolean

    /**
     * Return to the previous state
     *
     * @return Was the state change successful
     */
    fun goBack(): Boolean

    /**
     * Return to the next state
     * Possible only if goBack was called previously
     *
     * @return Was the state change successful
     */
    fun goForward(): Boolean

}