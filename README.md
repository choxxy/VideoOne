# VideoOne
# Use cases
1. App gets a list of videos from an api
2. App displays the videos on a list view
3. User picks a video from the  list  and views it
4. App allows user to play, pause and seek  the video
5. App shows  video duration  and current  time
6. User navigates back to video list and picks another video for viewing



#Tasks based on the use cases:

App gets a list of videos from an online source

1. Mock a json api  that will provide video api (actual API not available ). Work started at 13:20 am , the plan is to have a json file (named according to API endpoint) with mocked responses stored in the asset folder, then intercept API calls and load the response from file depending on the endpoint name  (finished at 14:30 cause I combined with task two)      1 hour
2. Code an api  class to interact with mock api (use retrofit + okhttp)     1  hour. (Covered in task 1)
3. Code a repository class  to  handle data operations and source  (include a data class to hold video information). Started working at 14:45 finished at 15:22  1  hour

App displays the videos on a list view
   1. Code a ViewModel class to manage interaction between UI  interface and repository      started at  15:40           ended at 16: 15                                                                               2 hours
2. Build user interface. In this case a  fragment  with a list view to show video retrieved from api     started at 16: 15               ended 16:48                                                        1 hour
3. Add code to fragment to retrieve video from repository via  viewModel and display it on the list. Should include code to act on items clicked on the list view                                                                                                   1 hour

User picks a video from the list and views

   1. Build user interface for viewing the selected video a video. Should include means of transitioning back to the list                                               2 hours
   2. Since the plan is to use a custom controller , I will need to code a custom view too. I plan to use a surfaceview to achieve that                        2 hours

App allows user to play. Pause and seek  video
  1. Implement a custom video controller with play , pause and seeking functionality
                                                                                                                 6 hours
App shows video durations and time
  1. Add time display to the custom video controller                            2 hours

