package com.ocean.ishareeconomy_android.utils

/**
 * Part of *utils*.
 *
 * Enables the safe creation of repositories that take arguments in their
 * constructors;
 *
 * Usage: set the repo's constructor to private and
 * make companion object of [ReusableRepositorySingleton] to get the singleton instance
 *
 * Implementation from: https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 *
 * @author Christophe Beyls

 */
open class ReusableRepositorySingleton<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    /**
     * Provides the safe singleton instance.
     * @return generic instance of type T.
     */
    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}