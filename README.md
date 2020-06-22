# VideoOne

Break down of the various activities during the app development (devlog)

## Use cases
1. App gets a list of videos from an api
2. App displays the videos on a list view
3. User picks a video from the  list  and views it
4. App allows user to play, pause and seek  the video
5. App shows  video duration  and current  time
6. User navigates back to video list and picks another video for viewing



## Tasks based on the use cases:

### App gets a list of videos from an online source

|        Task                                                            | Time estimate   |  Actual time|
|------------------------------------------------------------------------|:----------------|:------------|
| Mock a json api. Mock api will provide video api (actual API not available )
The plan was to have a json file (named according to API endpoint) with mocked responses stored in the assets folder. API calls will be intercepted and responses loaded from file depending on the endpoint name | 1 hour | 1 hour |
| Code an api  class to interact with mock api (use retrofit + okhttp)| 1 hour | 40 minutes|
| Code a repository class  to  handle data operations and sources  (include a data class to hold video information).| 1 hour | 35 minutes|

### App displays the videos on a list view

|        Task                                                            | Time estimate   |  Actual time|
|------------------------------------------------------------------------|:----------------|:------------|
| Build user interface. In this case a fragment with a list view and adapter to show videos retrieved from api|  2 hour | 1.5 hours| 
| Code a ViewModel class to manage interaction between UI and repository | 1 hour  | 35 minutes  | 
| Add code to fragment to retrieve video from repository via viewModel and display it on the list. Should include code to act on items clicked on the list view | 1 hours | 50 minutes |

### User picks a video from the list and views

|        Task                                                            | Time estimate   |  Actual time|
|------------------------------------------------------------------------|:----------------|:------------|
| Build user interface for viewing the selected video. Should be fullscreen (will use an activity) | 2 hours | 2hours |
   
### App allows user to play. Pause and seek video (customization task) + App shows  video duration  and current  time + User navigates back to video list and picks another video for viewing

|        Task                                                            | Time estimate   |  Actual time|
|------------------------------------------------------------------------|:----------------|:------------| 
|Implement a custom video controller with play , pause and seeking functionality . Please note is should display duration and time  and allow back navigate. (back navigation should be manual user initiated or automatic after playlist ends ) | 4 hours | 6 hours |
|Customize launcher icon + video list item placeholder image | 30 minutes | 15 minutes |
