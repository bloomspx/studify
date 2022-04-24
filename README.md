## Description
Studify is designed to make group studying more efficient and convenient for all by providing a virtual, collaborative platform that allows individuals to be more productive with friends, or even alone. 

Take charge and create your own private room as an admin. Invite more peers to join your room and study together and customise group tasks to encourage everyone to strive towards a common goal. Need more motivation and focus? Set Pomodoro timers to motivate everyone to complete them on time. Once you are done with a task, delete them with ease or add more time to the room and extend your studying period with friends.

## App Screenshots<br>
<img src="images/Layout1.png" width="750dp"> <br>
<img src="images/Layout2.png" width="750dp"> <br>
<img src="images/Layout3.png" width="750dp"> <br>

## Resources
<a href="https://www.figma.com/file/VLmQeGFdPmF4EEZmRTI5M2/App-Layout">
Figma App Designs <a>
<br>
<a href=https://docs.google.com/document/d/14fCwcmqJ00WbVjJvHaYVmXaJhs1Pl9lmSTjH77cuVwY/edit#heading=h.7v9j2i4f9rgq>
Final Report</a>

## External Dependencies <br>
<a href="https://github.com/bumptech/glide">
Glide </a> - used to display and fetch user profile picture
<br>
<a href="https://github.com/hdodenhof/CircleImageView">
CircleImageView </a> - fast circular ImageView used for user profile images
<br>

## Commit <br>
**2 Activities:** MainActivity, AuthActivity <br>
**11 Fragments:** EditProfileFragment, ForgetPasswordFragment, LoginFragment, MainFragment,<br> PomodoroFragment, ProfileFragment, RegisterFragment, RoomAdminFragment, RoomFragment, RoomList Fragment, TaskListFragment <br>
**Package Name:** com.example.studify <br>

<!-- ### data
> *UserDatabase* <br>
> * empty -->

### models
> *authentication*
> > *AuthAppRepository* <br>
> > * methods: register, addUser, login, logOut, resetPassword, deleteProfile 
> >
> *room* 
> > *AddTaskModel*<br>
> > * data class to help add tasks for a specific room
> >
> > *RandomString*<br>
> > * class to help generate random strings for Room ID allocation
> >
> > *RoomModel*<br>
> > * data class to help build rooms
> > *GroupTimeRepository* <br>
> >* room methods: timer
> >
> > *RoomAppRepository* <br>
> > * methods: createroom
> >
> > *TimerRepository* <br>
> > * methods: startStop,startTimer,stopTimer,updateTimer, getTimerLeftLiveData,getIsFinished
> >
> > *GroupTimerRepository*<br>
> > * methods: join,startTimer,startBreakTimer,updaterTimer,updateBreakTimer,getCount,getTimerLeftLiveData
> >
> *user*
> > *UserProfileModel* <br>
> > * data class to build new users models
> >
> > *UserAppRepository* <br>
> > * methods: updateProfile & getUserDetails


### navigation
> *auth*<br>
> * navigates between LoginFragment, <br>ForgetPasswordFragment & RegisterFragment
>
> *main*<br>
> * navigates between EditProfileFragment, MainFragment, PomodoroFragment, <br>ProfileFragment, RoomAdminFragment, RoomFragment,<br> RoomList Fragment &TaskListFragment

### viewmodels
> *LoginViewModel* <br>
> * authentication methods: login, <br>register, resetPassword
>
> *MainActivityViewModel* <br>
> * room methods: timer
>
> *RoomViewModel* <br>
> * room methods: createRoom, startGroupTimer, getGroupTimerLeftLiveDate
>
> *UserViewModel* <br>
> * authentication methods: logOut, deleteProfile, getLoggedOutLiveData
> * user methods: updateProfile, getUserProfileLiveData

### ui
> *adapter*
> > Adapter
> > * recyclerview adapter for displaying tasks in the room fragment
>
> *authentication*
> > *login*<br>
> > LoginFragment
>
> > *register*<br>
> > RegisterFragment
>
> > *forgot password*<br>
> > ForgotPasswordFragment 
>
> AuthActivity
> 
> *main*
> > *room*<br>
> > > *pomodoro timer*<br>
> > > PomodoroFragment
> > > 
> >
> > > *room admin*<br>
> > > RoomAdminFragment
> > >
> >
> > > *room*<br>
> > > RoomFragment
> > > 
> >
> > > *room list*<br>
> > > RoomListFragment (firebase)
> > >
> >
> > > *task list*<br>
> > > TaskListFragment
> > > * user methods: addTask, updateTask
> >

> >
> > *user*
> > > *user profile* <br>
> > > ProfileFragment
> > > * user methods: updateProfile, logOut
> > 
> > > *edit profile* <br>
> > > EditProfileFragment<br>
> > > * user methods: updateProfilePicture, updateUserName, deleteProfile
> >
> > MainFragment <br>
> > MainActivity 
> >
