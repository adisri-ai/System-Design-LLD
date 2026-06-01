import java.util.*;

// class for defining song
class Song {
    String lyrics;
    String singer;
    String language;
    String title;

    public Song(String title, String singer, String language, String lyrics) {
        this.title = title;
        this.singer = singer;
        this.language = language;
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return title + " by " + singer;
    }
}

// interface for song arrangement strategy
interface SongArrangementStrategy {
    void arrange(List<Song> songs);
}

// Performing Sequential Arrangement
class SequenceStrategy implements SongArrangementStrategy {
    @Override
    public void arrange(List<Song> songs) {
        System.out.println("Implemented Sequential Arrangement");
    }
}

class RandomStrategy implements SongArrangementStrategy {
    @Override
    public void arrange(List<Song> songs) {
        Collections.shuffle(songs);
        System.out.println("Implemented Random Arrangement");
    }
}

// Class for implementing SongSequencing
// Strategy Design Pattern
abstract class SongSequencer {
    SongArrangementStrategy strategy;

    public SongSequencer(SongArrangementStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract Song nextSong();

    public abstract Song prevSong();
}

class PlaylistSequencer extends SongSequencer {

    private List<Song> songs;
    private int currentIndex = 0;

    public PlaylistSequencer(List<Song> songs, SongArrangementStrategy strategy) {
        super(strategy);
        this.songs = songs;
        strategy.arrange(songs);
    }

    @Override
    public Song nextSong() {
        if (songs.isEmpty()) return null;

        currentIndex = (currentIndex + 1) % songs.size();
        return songs.get(currentIndex);
    }

    @Override
    public Song prevSong() {
        if (songs.isEmpty()) return null;

        currentIndex = (currentIndex - 1 + songs.size()) % songs.size();
        return songs.get(currentIndex);
    }
}

// Command Design Pattern
interface Command {
    void execute();

    void undo();
}

class AddCommand implements Command {

    private Playlist playlist;
    private Song song;

    public AddCommand(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song;
    }

    @Override
    public void execute() {
        playlist.songs.add(song);
        System.out.println("Added new song: " + song);
    }

    @Override
    public void undo() {
        playlist.songs.remove(song);
        System.out.println("Undo Add Song");
    }
}

class RemoveCommand implements Command {

    private Playlist playlist;
    private Song song;

    public RemoveCommand(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song;
    }

    @Override
    public void execute() {
        playlist.songs.remove(song);
        System.out.println("Removed song: " + song);
    }

    @Override
    public void undo() {
        playlist.songs.add(song);
        System.out.println("Undo Remove Song");
    }
}

// Class for implementing Playlist
abstract class Playlist {

    List<Song> songs = new ArrayList<>();
    Player songPlayer;
    SongSequencer sequencer;

    public abstract void startPlaylist();

    public abstract void stopPlaylist();
}

class UserPlaylist extends Playlist {

    public UserPlaylist(Player player, SongSequencer sequencer) {
        this.songPlayer = player;
        this.sequencer = sequencer;
    }

    @Override
    public void startPlaylist() {
        Song song = sequencer.nextSong();
        songPlayer.currentSong = song;
        songPlayer.play();
    }

    @Override
    public void stopPlaylist() {
        songPlayer.pause();
        System.out.println("Playlist stopped");
    }
}

// class for defining music player
abstract class Player {

    Song currentSong;

    public abstract void play();

    public abstract void pause();
}

// Singleton Design Pattern
class MediaPlayer extends Player {

    private static MediaPlayer instance;

    private MediaPlayer() {
    }

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }

    @Override
    public void play() {
        if (currentSong != null)
            System.out.println("Playing: " + currentSong);
        else
            System.out.println("No song selected");
    }

    @Override
    public void pause() {
        System.out.println("Paused");
    }
}

// Interface for output device
interface OutputDevice {
    void playAudio(Song song);
}

class BluetoothDevice implements OutputDevice {

    @Override
    public void playAudio(Song song) {
        System.out.println("Playing through Bluetooth: " + song);
    }
}

class WirelessDevice implements OutputDevice {

    @Override
    public void playAudio(Song song) {
        System.out.println("Playing through Wireless Device: " + song);
    }
}

// Factory design Pattern
interface OutputDeviceFactory {
    OutputDevice connectDevice();
}

class BluetoothDeviceFactory implements OutputDeviceFactory {

    @Override
    public OutputDevice connectDevice() {
        return new BluetoothDevice();
    }
}

class WirelessDeviceFactory implements OutputDeviceFactory {

    @Override
    public OutputDevice connectDevice() {
        return new WirelessDevice();
    }
}

// Adapter Design-pattern
interface OutputDeviceAdapter {
    void play(Song song);
}

class BluetoothAdapter implements OutputDeviceAdapter {

    private BluetoothDevice bluetoothDevice;

    public BluetoothAdapter(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public void play(Song song) {
        bluetoothDevice.playAudio(song);
    }
}

class WirelessAdapter implements OutputDeviceAdapter {

    private WirelessDevice wirelessDevice;

    public WirelessAdapter(WirelessDevice wirelessDevice) {
        this.wirelessDevice = wirelessDevice;
    }

    @Override
    public void play(Song song) {
        wirelessDevice.playAudio(song);
    }
}

// Factory Design-pattern for adapters
interface AdapterFactory {
    OutputDeviceAdapter createAdapter();
}

class BluetoothAdapterFactory implements AdapterFactory {

    @Override
    public OutputDeviceAdapter createAdapter() {
        return new BluetoothAdapter(new BluetoothDevice());
    }
}

class WirelessAdapterFactory implements AdapterFactory {

    @Override
    public OutputDeviceAdapter createAdapter() {
        return new WirelessAdapter(new WirelessDevice());
    }
}

// Singleton Design Pattern
class MediaOutputInterface {

    private static MediaOutputInterface instance;

    MediaPlayer player;
    OutputDeviceAdapter target;

    private MediaOutputInterface() {
        player = MediaPlayer.getInstance();
    }

    public static MediaOutputInterface getInstance() {
        if (instance == null) {
            instance = new MediaOutputInterface();
        }
        return instance;
    }

    public void setOutputDevice(OutputDeviceAdapter target) {
        this.target = target;
    }

    public void sendAudio() {
        if (player.currentSong != null && target != null) {
            target.play(player.currentSong);
        } else {
            System.out.println("No output device/song available");
        }
    }
}

// Facade Design Pattern
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

public class Main {

    public static void main(String[] args) {

        Song s1 = new Song("Believer", "Imagine Dragons", "English", "...");
        Song s2 = new Song("Shape of You", "Ed Sheeran", "English", "...");

        MediaPlayer player = MediaPlayer.getInstance();

        List<Song> songList = new ArrayList<>();
        songList.add(s1);
        songList.add(s2);

        SongSequencer sequencer =
                new PlaylistSequencer(songList, new SequenceStrategy());

        UserPlaylist playlist =
                new UserPlaylist(player, sequencer);

        AddCommand addCommand = new AddCommand(playlist, s1);
        addCommand.execute();

        AddCommand addCommand2 = new AddCommand(playlist, s2);
        addCommand2.execute();

        SpotifyApp app = new SpotifyApp();

        app.openApp();

        AdapterFactory adapterFactory =
                new BluetoothAdapterFactory();

        OutputDeviceAdapter adapter =
                adapterFactory.createAdapter();

        MediaOutputInterface.getInstance()
                .setOutputDevice(adapter);

        app.playSong(s1);

        app.pause();

        app.closeApp();
    }
}
