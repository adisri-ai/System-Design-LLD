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
