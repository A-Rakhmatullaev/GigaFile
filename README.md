# Final Submission

## App: GigaFile

## Members: Akbarbek Rakhmatullaev

### Does it accomplish the stated objective?
- Objective was to create an open-source file manager native to Android OS and learn from that process. Basic main functions of the file manager were implemented and newly learned concepts were applied

### Does it deliver on the “value proposition”?
- Value was that file manager will be open-source and complement features that other file managers lacked. Since it was really hard to find new features to implement in a file manager (there are countless file managers available on the market, and each contributed in its own way), I came up with only a new addition that would not allow users to delete or modify folders containing system files (such as 'Android' folder), unlike other file managers. Also, this project is completely open-source. However, connection to a cloud storages was not implemented due to the time constraints, and I decided to focus more on working with local internal files

### Does it respect user privacy/is it secure?
- Yes, app is secure to use, it uses obfuscation of its code by default. Also, no user-sensitive data is collected or being stored.

### Does it have proper app lifecycle/state management?
- Yes, I worked on that a lot. Each screen (fragment) has corresponding ViewModel instance, that ensures states are being stored properly. Additionally, in 'Files' screen, I ensured that FileObserver, that monitors changes in files in a given directory, gets destroyed when user changes a directory and another instance is being instantiated for a new directory. This is done using callbackFlow, that ensures that FileObserver gets finalized on awaitClose. Also, 'Files' screen saves the state of the current directory, and even after switching between screens from bottom navigation bar users can see the contents of the directory they opened last. Internally, states are being observed from ViewModel using LiveData by a screen, and even a special custom generic one-time event LiveData ('SingleLiveEvent') class was created to support one-time notificaiton and message displaying 

### Does it address at least three additional challenges (other than privacy and state management) unique to mobile app development?
- Yes, here they are:
  1. Optimization and performance: a lot of time and effort was put to optimize directory and file retrieval process, as well as observing the directory in a real-time. Apart from that, all layouts were implemented to be as basic as possible, in order for UI to get displayed faster (such as using Linear or Frame Layouts instead of Constraint layouts, etc.)
  2. Multi-threading: a thorough work has been done to utilize Kotlin coroutines instead of threads, as it is a best-practice in native Android development. This ensures that functions and operations can be suspended, leaving more resources to operate faster, unlike threads that consume a lot of resources. This includes using 'suspend' functions, coroutine Flows, etc. 
  3. Code maintainability: the whole app is divided into designated layers: 'app', 'core', 'data', 'di', 'domain', and 'presentation'. Each has its own responsibility, and was introduced as a part of a layered architecture. For example, 'domain' layer contains all the business logic, that is not platform specific, and can be used with any other platform that supports Kotlin/Java. A lot of generic base classes were implemented including BaseUseCase, BaseScreen, FileSystemElement etc. Out of them all, the most time and effort was spent on BaseUseCase, which then however allowed to have highly maintainble use cases. Also, to allow readibility of the code, effort was put to develop convenient extension-functions, that could drastically reduce number of code lines (e.g. File.isFileInUse() and many more). A lot of time was also spent to create BaseAdapter, BaseAdapterCallback and BaseViewHolder to have a generalized way to display a set of items in RecylcerView (you can check 'directory_adapter' under 'presentation/utils/adapters')

### Does it have a good user experience?
- Yes, all the transitions are smooth and consume as few computing resources as possible. Insertion/deletion/modification and all other operations with files/folders (even the ones triggered by the system) result in smooth animations on UI. Switching between tabs in bottom navigation bar is fast, as well as between directories. Users can get a nice bottom sheet fragment UI popping up when they want to create, delete or edit files/folders. Each of these fragments has its custom implementation (yet united under the generic BaseBottomSheet for better maintainability), so user can get a maximum context of what's happening (e.g. showing which file is being operated on, etc.). Additionally, colors were chosen with accordance to Material Design 3.0 and applied according to the use-case (e.g different colors for 'delete' and 'cancel' buttons)



