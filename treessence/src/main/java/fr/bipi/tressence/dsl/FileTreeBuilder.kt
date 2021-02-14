package fr.bipi.tressence.dsl

import fr.bipi.tressence.common.filters.mergeFilters
import fr.bipi.tressence.common.formatter.LogcatFormatter
import fr.bipi.tressence.file.FileLoggerTree
import timber.log.Timber


typealias FileTreeDeclaration = FileTreeScope.() -> Unit

class FileTreeScope : TreeScope() {
    var fileName = "log"
    var dir = ""
    var sizeLimit = FileLoggerTree.Builder.SIZE_LIMIT
    var fileLimit = FileLoggerTree.Builder.NB_FILE_LIMIT
    var appendToFile = true
}

/**
 * Builder for [FileLoggerTree]
 */
object FileTreeBuilder {
    fun build(data: FileTreeScope): Timber.Tree {
        return FileLoggerTree.Builder().apply {
            with(data) {
                withDirName(dir)
                withFileName(fileName)
                withMinPriority(level)
                withSizeLimit(sizeLimit)
                withFileLimit(fileLimit)
                withFilter(filters.mergeFilters())
                withFormatter(formatter ?: LogcatFormatter.INSTANCE)
                appendToFile(appendToFile)
            }
        }.build()
    }
}
