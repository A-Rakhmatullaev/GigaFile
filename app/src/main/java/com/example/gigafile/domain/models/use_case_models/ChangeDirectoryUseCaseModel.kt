package com.example.gigafile.domain.models.use_case_models

class ChangeDirectoryUseCaseModel(
    val directoryAction: DirectoryAction,
    val directoryPath: ArrayList<String>,
    val storageRootPath: String?
)

sealed class DirectoryAction(val directoryName: String) {
    class ToDirectory(directoryName: String): DirectoryAction(directoryName)
    class UpToPrevious(directoryName: String): DirectoryAction(directoryName)
    class ToRoot(directoryName: String): DirectoryAction(directoryName)
    class ToHiddenDirectory(directoryPath: String): DirectoryAction(directoryPath)
}