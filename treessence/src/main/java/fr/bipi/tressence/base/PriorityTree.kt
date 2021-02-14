package fr.bipi.tressence.base

import fr.bipi.tressence.common.filters.Filter
import fr.bipi.tressence.common.filters.NoFilter
import fr.bipi.tressence.common.filters.PriorityFilter
import timber.log.Timber

/**
 * Base class to filter logs by priority
 */
@Suppress("MemberVisibilityCanBePrivate")
open class PriorityTree @JvmOverloads constructor(
    priority: Int,
    val filter: Filter = NoFilter.INSTANCE
) : Timber.DebugTree() {
    val priorityFilter: PriorityFilter = PriorityFilter(priority)

    override fun isLoggable(priority: Int): Boolean {
        return isLoggable("", priority)
    }

    public override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priorityFilter.isLoggable(priority, tag) && filter.isLoggable(priority, tag)
    }

    /**
     * Use the additional filter to determine if this log needs to be skipped
     *
     * @param priority Log priority
     * @param tag      Log tag
     * @param message  Log message
     * @param t        Log throwable
     * @return true if needed to be skipped or false
     */
    protected fun skipLog(priority: Int, tag: String?, message: String, t: Throwable?): Boolean {
        return filter.skipLog(priority, tag, message, t)
    }
}
