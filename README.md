# VideoOne

Break down of the various activities during the app development (devlog)

# Use cases
1. App gets a list of videos from an api
2. App displays the videos on a list view
3. User picks a video from the  list  and views it
4. App allows user to play, pause and seek  the video
5. App shows  video duration  and current  time
6. User navigates back to video list and picks another video for viewing



#Tasks based on the use cases:

App gets a list of videos from an online source

1. Mock a json api. Mock api will provide video api (actual API not available ). estimated time 1 hour actual time 30  minutes
The plan was to have a json file (named according to API endpoint) with mocked responses stored in the assets folder. API calls will be intercepted and responses loaded from file depending on the endpoint name
2. Code an api  class to interact with mock api (use retrofit + okhttp)  estimated time 1 hour actual time 40 minutes
3. Code a repository class  to  handle data operations and sources  (include a data class to hold video information). Started working at 14:45 finished at 15:20  estimated time was 1 hour actual time 35 minutes

App displays the videos on a list view
1. Build user interface. In this case a fragment  with a list view and adapter to show videos retrieved from api.
   Started at 16: 15  ended 16:48 estimated time was 1 hour

2. Code a ViewModel class to manage interaction between UI and repository   
started at  15:40 ended at 16: 15, estimated time was 1hours
3. Add code to fragment to retrieve video from repository via viewModel and display it on the list. Should include code to act on items clicked on the list view (moved to controller customization task)  estimate time 2 hours

User picks a video from the list and views

   1. Build user interface for viewing the selected video. Should include means of transitioning back to the list                                               2 hours
   
App allows user to play. Pause and seek video (customization task)
  1. Implement a custom video controller with play , pause and seeking functionality. Please note is should display duration and time 4 hours
