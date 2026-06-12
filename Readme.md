# Overview  
This is an LLD Implementation of Spotify Music app   
# Functional Requirements  
1. Users can play/pause songs  
2. Users can create playlist, add songs to playlist, Play entire playlist (sequence, random, etc.)  
3. App should support multiple output devices (Bluetooth Speaker, wired speaker)    
# UML DIAGRAM  
Here is the UML Diagram of the project:   
```text
+------------------+
|      Song        |
+------------------+
| lyrics           |
| singer           |
| language         |
| title            |
+------------------+

          ^
          |
          |

+------------------------------+
| SongArrangementStrategy      |
+------------------------------+
| + arrange()                  |
+------------------------------+
        ^               ^
        |               |
+------------------+  +------------------+
| SequenceStrategy |  | RandomStrategy   |
+------------------+  +------------------+

          ^
          |
+----------------------+
|    SongSequencer     |
+----------------------+
| strategy             |
| + nextSong()         |
| + prevSong()         |
+----------------------+
          ^
          |
+----------------------+
| PlaylistSequencer    |
+----------------------+

+----------------------+
|      Command         |
+----------------------+
| + execute()          |
| + undo()             |
+----------------------+
        ^          ^
        |          |
+--------------+  +----------------+
| AddCommand   |  | RemoveCommand  |
+--------------+  +----------------+

+----------------------+
|      Playlist        |
+----------------------+
| songs                |
| player               |
| sequencer            |
+----------------------+
| + startPlaylist()    |
| + stopPlaylist()     |
+----------------------+
          ^
          |
+----------------------+
|    UserPlaylist      |
+----------------------+

+----------------------+
|       Player         |
+----------------------+
| currentSong          |
| + play()             |
| + pause()            |
+----------------------+
          ^
          |
+----------------------+
|     MediaPlayer      |
+----------------------+

+----------------------+
|    OutputDevice      |
+----------------------+
| + playAudio()        |
+----------------------+
        ^          ^
        |          |
+------------------+  +------------------+
| BluetoothDevice  |  | WirelessDevice   |
+------------------+  +------------------+

+--------------------------+
| OutputDeviceAdapter      |
+--------------------------+
| + play()                 |
+--------------------------+
        ^              ^
        |              |
+------------------+  +------------------+
| BluetoothAdapter |  | WirelessAdapter  |
+------------------+  +------------------+

+----------------------+
|    AdapterFactory    |
+----------------------+
| + createAdapter()    |
+----------------------+
        ^              ^
        |              |
+--------------------------+  +--------------------------+
| BluetoothAdapterFactory  |  | WirelessAdapterFactory   |
+--------------------------+  +--------------------------+

+----------------------+
| MediaOutputInterface |
+----------------------+
| player               |
| target               |
| + sendAudio()        |
+----------------------+

+----------------------+
|     SpotifyApp       |
+----------------------+
| playlists            |
| songs                |
| mediaInterface       |
+----------------------+
| + openApp()          |
| + playSong()         |
| + pause()            |
| + closeApp()         |
+----------------------+
```
# Design Patterns used: 
Following are the Design patterns used in this project:  
1. **Facade Design Pattern** : To create a single point of communication
   ```
   class SpotifyApp {

    List<Playlist> playlists = new ArrayList<>();
    List<Song> songs = new ArrayList<>();

    MediaOutputInterface mediaInterface;

    public SpotifyApp() {
        mediaInterface = MediaOutputInterface.getInstance();
    }

    public void openApp() {
        System.out.println("App opened");
    }

    public void playPlaylist(Playlist playlist) {
        playlist.startPlaylist();
    }

    public void playSong(Song song) {
        MediaPlayer player = MediaPlayer.getInstance();
        player.currentSong = song;
        player.play();

        mediaInterface.sendAudio();
    }

    public void play() {
        MediaPlayer.getInstance().play();
    }

    public void pause() {
        MediaPlayer.getInstance().pause();
    }

    public void closeApp() {
        System.out.println("Closing App");
    }
   }
 
2. **Adapter Design Pattern** : To integrate external devices to the music app
3. **Singleton Design Pattern** : To maintain only 1 instance of media controller 
4. **Factory Design Pattern** : To encapsualte the creation of new songs/playlists
