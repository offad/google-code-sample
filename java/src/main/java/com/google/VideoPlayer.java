package com.google;

import java.io.Console;
import java.util.*;

public class VideoPlayer {

  /** 
   * -----------
   *  CONSTANTS
   * -----------
   */

  private static final String PLAYING_VIDEO = "Playing video";
  private static final String PLAYING_VIDEO_PLAYING = "Currently playing";
  private static final String PAUSING_VIDEO = "Pausing video";
  private static final String CONTINUING_VIDEO = "Continuing video";
  private static final String STOPPING_VIDEO = "Stopping video";

  private static final String VIDEO_ALREADY_PAUSED = "Video already paused";
  private static final String VIDEO_NOT_PAUSED =  "Cannot continue video: Video is not paused";

  private static final String VIDEO_FLAG_NO_REASON =  "Not supplied";

  private static final String VIDEO_NOT_FOUND = "Cannot play video: Video does not exist";
  private static final String VIDEO_NOT_PLAYED =  "No video is currently playing";
  private static final String VIDEO_NOT_FOUND_PAUSED = "Cannot pause video: No video is currently playing";
  private static final String VIDEO_NOT_FOUND_CONTINUED = "Cannot continue video: No video is currently playing";
  private static final String VIDEO_NOT_FOUND_STOPPED =  "Cannot stop video: No video is currently playing";

  private static final String LIBRARY_LIST = "Here's a list of all available videos:";
  private static final String LIBRARY_NOT_FOUND = "No videos available";

  private static final String PLAYLIST_CREATED = "Successfully created new playlist";

  private static final String PLAYLIST_ALREADY_EXISTS = "Cannot create playlist: A playlist with the same name already exists";

  /** 
   * ------------
   *  ATTRIBUTES
   * ------------
   */

  private Random randomGenerator;

  private final VideoLibrary videoLibrary;

  private Video video;

  // Ensure playlists are in lexicographical order by name
  private TreeMap<String, VideoPlaylist> playlists;

  /** 
   * -------------
   *  CONSTRUCTOR
   * -------------
   */

  public VideoPlayer() {

    this.randomGenerator = new Random();

    this.videoLibrary = new VideoLibrary();

    this.playlists = new TreeMap<>();

  }

  /** 
   * ---------
   *  METHODS
   * ---------
   */

  /** 
   * ------------------------------------
   * PART ONE
   * ------------------------------------
   */

   /** 
   * Print the number of videos in the library
   */
  public void numberOfVideos() {

    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    
  }


  /** 
   * Print all the videos in the library to the standard output in the format: “title (video_id) [tags]”
   */
  public void showAllVideos() {

    List<Video> currentVideos = videoLibrary.getVideos();

    // Sort in lexicographical order by title
    Collections.sort(currentVideos);

    System.out.println(LIBRARY_LIST);

    currentVideos.forEach(System.out::println);

  }

  
  /** 
   * Play the specified video. 
   * 
   * If a video is currently playing, display a note that this video will bestopped, 
   * even if the same video is already playing.
   * 
   * If the video doesn’t exist, display a warning message (and don’t stop the currently playing video).
   * 
   * @param videoId
   */
  public void playVideo(String videoId) {

    Video requestedVideo = videoLibrary.getVideo(videoId);
    
    if (requestedVideo != null) {

      if (requestedVideo.isFlagged()) {

        System.out.printf("Cannot play video: Video is currently flagged (reason: %s)%n", requestedVideo.getFlagReason());
  
        return;
  
      }

      if (video != null) {

        video.resume();
        System.out.printf(STOPPING_VIDEO + ": %s%n", video.getTitle());
        video = null; // Added this for peace of mind even though I know there is no point.

      }

      System.out.printf(PLAYING_VIDEO + ": %s%n", requestedVideo.getTitle());
      video = requestedVideo;
      video.resume();

    } else {

      System.out.println(VIDEO_NOT_FOUND);

    }

  }


  /** 
   * Stop the current playing video. 
   * 
   * If no video is currently playing, display a warning message 
   * “Cannot stop video: No video is currently playing” and do nothing.
   * 
   */
  public void stopVideo() {
    
    if (video != null) {

      video.resume();
      System.out.printf(STOPPING_VIDEO + ": %s%n", video.getTitle());
      video = null; // Added this for peace of mind even though I know there is no point.
      
    } else {

      System.out.println(VIDEO_NOT_FOUND_STOPPED);

    }

  }


  /** 
   * Play a random video. 
   * 
   * If a video is currently playing,display a note that this video will be stopped,
   * even if the same video is already playing.
   * 
   */
  public void playRandomVideo() {

    // Get an editable version of the list
    List<Video> currentVideos = videoLibrary.getVideos();

    Iterator<Video> iterator = currentVideos.iterator();

    // Remove videos that are flagged
    while (iterator.hasNext()) {

      Video video = iterator.next();

      if (video.isFlagged()) {
        iterator.remove();
      }

    }    
  
    if (currentVideos.size() > 0) {

      int index = randomGenerator.nextInt(currentVideos.size());
    
      Video randomVideo = currentVideos.get(index);

      playVideo(randomVideo.getVideoId());

    } else {

      System.out.println(LIBRARY_NOT_FOUND);

    }

  }


  /** 
   * Play a random video. 
   * 
   * If a video is currently playing,display a note that this video will be stopped,
   * even if the same video is already playing.
   * 
   */
  public void pauseVideo() {
    
    if (video != null) {

      // Check that the video is not already paused
      if (!video.isPaused()) {

        System.out.printf(PAUSING_VIDEO + ": %s%n", video.getTitle());
        video.pause();

      } else {

        System.out.printf(VIDEO_ALREADY_PAUSED + ": %s%n", video.getTitle());

      }

    } else {

      System.out.println(VIDEO_NOT_FOUND_PAUSED);

    }

  }

   /** 
   * Continues a currently paused video.
   * 
   * If the currently playing video is not paused, display awarning message and do nothing. 
   * 
   * If no video is playing at all, also display a warning messageand do nothing.
   * 
   */
  public void continueVideo() {
    
    if (video != null) {

      // Check that the video is paused
      if (video.isPaused()) {

        System.out.printf(CONTINUING_VIDEO + ": %s%n", video.getTitle());
        video.resume();

      } else {

        System.out.printf(VIDEO_NOT_PAUSED + ": %s%n", video.getTitle());

      }

    } else {

      System.out.println(VIDEO_NOT_FOUND_CONTINUED);

    }

  }

  /** 
   * Displays the title, video_id, video tags and paused status of the video that is currently playing. 
   * 
   * If no video is currently playing, display a message.
   * 
   */
  public void showPlaying() {

    if (video != null) {

      System.out.println(PLAYING_VIDEO_PLAYING + ": " + video.toString());

    } else {

      System.out.println(VIDEO_NOT_PLAYED);

    }

  }

  /** 
   * ------------------------------------
   * PART TWO
   * ------------------------------------
   */
  
  /** 
   * Create a new (empty) playlist with a unique name.
   * 
   * If a playlist with the same name already exists, display a warning to the user and do nothing.
   * 
   * @param playlistName
   */
  public void createPlaylist(String playlistName) {

    String searchName = playlistName.toLowerCase();

    if (playlists.get(searchName) != null) {

      System.out.println(PLAYLIST_ALREADY_EXISTS);

    } else {

      VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);

      playlists.put(searchName, newPlaylist);

      System.out.printf(PLAYLIST_CREATED + ": %s%n", playlistName);

    }

  }

  /** 
   * Adds the specified video to a playlist. 
   * 
   * If either the video or the playlist don’t exist, show a warning message. 
   * 
   * If both don’t exist, display the warning message for the playlist first. 
   * The playlist should not allow duplicate videos and display a warning message if a video is already present in the playlist.
   * 
   * @param playlistName
   * @param videoId
   */
  public void addVideoToPlaylist(String playlistName, String videoId) {
    
    String searchName = playlistName.toLowerCase();

    VideoPlaylist requestedPlaylist = playlists.get(searchName);

    if (requestedPlaylist != null) {

      Video requestedVideo = videoLibrary.getVideo(videoId);
    
      if (requestedVideo != null) {

        if (requestedVideo.isFlagged()) {

          System.out.printf("Cannot add video to %s: Video is currently flagged (reason: %s)%n", playlistName, requestedVideo.getFlagReason());
    
          return;
    
        }

        boolean success = requestedPlaylist.addVideo(requestedVideo);
        
        if (success) {

          System.out.printf("Added video to %s: %s%n", playlistName, requestedVideo.getTitle());

        } else {

          System.out.printf("Cannot add video to %s: Video already added%n", playlistName);

        }

      } else {

        System.out.printf("Cannot add video to %s: Video does not exist%n", playlistName);

      }

    } else {

      System.out.printf("Cannot add video to %s: Playlist does not exist%n", playlistName);

    }

  }


  /** 
   * Show all the available playlists (name only).
   */
  public void showAllPlaylists() {
    
    if (playlists.size() > 0) {

      System.out.println("Showing all playlists:");

      for (VideoPlaylist playlist: playlists.values()) {
        System.out.println(playlist.getName());  
      }

    } else {

      System.out.println("No playlists exist yet");

    }
    
  }

  
  /** 
   * Show all the videos in the specified playlist.
   * 
   * The videos should belisted in the same order they were added.
   * 
   * @param playlistName
   */
  public void showPlaylist(String playlistName) {
    
    String searchName = playlistName.toLowerCase();

    VideoPlaylist requestedPlaylist = playlists.get(searchName);

    if (requestedPlaylist != null) {

      System.out.printf("Showing playlist: %s%n", playlistName);

      Set<Video> playlistVideos = requestedPlaylist.getVideos();

      if (playlistVideos.size() > 0) {

        playlistVideos.forEach(System.out::println);

      } else {

        System.out.println("No videos here yet");  

      }

    } else {

      System.out.printf("Cannot show playlist %s: Playlist does not exist%n", playlistName);

    }

  }


  /** 
   * Remove the specified video from the specified playlist.
   * 
   * If either does not exist, display a warning message.
   * 
   * @param playlistName
   * @param videoId
   */
  public void removeFromPlaylist(String playlistName, String videoId) {

    String searchName = playlistName.toLowerCase();

    VideoPlaylist requestedPlaylist = playlists.get(searchName);

    if (requestedPlaylist != null) {

      Video requestedVideo = videoLibrary.getVideo(videoId);
    
      if (requestedVideo != null) {

        boolean success = requestedPlaylist.removeVideo(requestedVideo);
        
        if (success) {

          System.out.printf("Removed video from %s: %s%n", playlistName, requestedVideo.getTitle());

        } else {

          System.out.printf("Cannot remove video from %s: Video is not in playlist%n", playlistName);

        }

      } else {

        System.out.printf("Cannot remove video from %s: Video does not exist%n", playlistName);

      }

    } else {

      System.out.printf("Cannot remove video from %s: Playlist does not exist%n", playlistName);

    }

  }

  
  /** 
   * Removes all the videos from the playlist, but doesn’t delete the playlist itself. 
   * 
   * If the playlist doesn’t exist, display a warning message.
   * 
   * @param playlistName
   */
  public void clearPlaylist(String playlistName) {
    
    String searchName = playlistName.toLowerCase();

    VideoPlaylist requestedPlaylist = playlists.get(searchName);

    if (requestedPlaylist != null) {

      requestedPlaylist.clear();

      System.out.printf("Successfully removed all videos from %s%n", playlistName); 

    } else {

      System.out.printf("Cannot clear playlist %s: Playlist does not exist%n", playlistName);

    }
    
  }

  
  /** 
   * @param playlistName
   */
  public void deletePlaylist(String playlistName) {
    
    String searchName = playlistName.toLowerCase();

    VideoPlaylist requestedPlaylist = playlists.get(searchName);

    if (requestedPlaylist != null) {

      playlists.remove(searchName);

      System.out.printf("Deleted playlist: %s%n", playlistName); 

    } else {

      System.out.printf("Cannot delete playlist %s: Playlist does not exist%n", playlistName);

    }

  }

  
  /** 
   * ------------------------------------
   * PART THREE
   * ------------------------------------
   */

  /** 
   * Display all videos in the library whose titles containthe specified search term. 
   * 
   * Display a list in lexicographical order (by title) and ask the user if they’d like to play one of the videos.
   * 
   * Read in the answer from the standard input - the number in the list - then play that video, otherwise 
   * assume the answer is no and do nothing.
   * 
   * @param searchTerm
   */
  public void searchVideos(String searchTerm) {
    
    String search = searchTerm.toLowerCase();

    // Get an editable version of the list
    List<Video> currentVideos = videoLibrary.getVideos();

    // Sort in lexicographical order by title before searching
    Collections.sort(currentVideos);

    Iterator<Video> iterator = currentVideos.iterator();

    // Remove videos without the seach term
    while (iterator.hasNext()) {

      Video video = iterator.next();

      if (!video.getTitle().toLowerCase().contains(search) || video.isFlagged()) {
        iterator.remove();
      }

    }

    if (currentVideos.size() > 0) {

      System.out.printf("Here are the results for %s:%n", searchTerm);

      // List out the query
      for (int i = 0; i < currentVideos.size(); i++) {

        Video currentVideo = currentVideos.get(i);

        System.out.printf("%d) %s%n", (i + 1), currentVideo.toString());

      }

      // Prompt the user
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");

      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      // Read from the console
      Scanner scanner = new Scanner(System.in);

      String line = scanner.next();

      Integer position = null;

      // Check if the line is a number
      try {

        position = Integer.parseInt(line);

      } catch (NumberFormatException e) {
        
        // --

      }

      scanner.close();

      if (position != null && currentVideos.size() >= position.intValue() && position > 0) {

        Video currentVideo = currentVideos.get(position.intValue() - 1);

        playVideo(currentVideo.getVideoId());

      }

    } else {

      System.out.printf("No search results for %s%n", searchTerm);

    }
    
  }

  
  /** 
   * Show all videos whose list of tags contains the specified hashtag.
   * 
   * @param videoTag
   */
  public void searchVideosWithTag(String videoTag) {
    
    String searchTag = videoTag.toLowerCase();

    // Get an editable version of the list
    List<Video> currentVideos = videoLibrary.getVideos();

    // Sort in lexicographical order by title before searching
    Collections.sort(currentVideos);

    Iterator<Video> iterator = currentVideos.iterator();

    // Remove videos without the seach term
    while (iterator.hasNext()) {

      Video video = iterator.next();

      if (!video.getTagsString().toLowerCase().contains(searchTag) || video.isFlagged()) {
        iterator.remove();
      }

    }

    // Check that the tag has the correct syntax - lazy method
    if (currentVideos.size() > 0 && videoTag.contains("#")) {

      System.out.printf("Here are the results for %s:%n", videoTag);

      // List out the query
      for (int i = 0; i < currentVideos.size(); i++) {

        Video currentVideo = currentVideos.get(i);

        System.out.printf("%d) %s%n", (i + 1), currentVideo.toString());

      }

      // Prompt the user
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");

      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      // Read from the console
      Scanner scanner = new Scanner(System.in);

      String line = scanner.next();

      Integer position = null;

      // Check if the line is a number
      try {

        position = Integer.parseInt(line);

      } catch (NumberFormatException e) {
        
        // --

      }

      scanner.close();

      if (position != null && currentVideos.size() >= position.intValue() && position >= 0) {

        Video currentVideo = currentVideos.get(position.intValue() - 1);

        playVideo(currentVideo.getVideoId());

      }

    } else {

      System.out.printf("No search results for %s%n", videoTag);

    }

  }

  /** 
   * ------------------------------------
   * PART FOUR
   * ------------------------------------
   */

  /** 
   * Mark a video as flagged with the default of “Not supplied”.
   * If a video is already flagged or does not exist, display a warning message.
   * 
   * @param videoId
   */
  public void flagVideo(String videoId) {

    flagVideo(videoId, VIDEO_FLAG_NO_REASON);

  }

  
  /** 
   * Mark a video as flagged with a supplied reason. 
   * The flag-reason is optional - if no reason is supplied by the user, it should default to “Not supplied”.
   * If a video is already flagged or does notexist, display a warning message.
   * 
   * @param videoId
   * @param reason
   */
  public void flagVideo(String videoId, String reason) {

    Video requestedVideo = videoLibrary.getVideo(videoId);
    
    if (reason == null){
      reason = VIDEO_FLAG_NO_REASON;
    }

    if (requestedVideo != null) {

      if (requestedVideo.equals(video)) {
        stopVideo();
      }

      boolean success = requestedVideo.flag(reason);

      if (success) {
        System.out.printf("Successfully flagged video: %s (reason: %s)%n", requestedVideo.getTitle(), reason);
      } else {
        System.out.println("Cannot flag video: Video is already flagged");
      }

    } else {

      System.out.println("Cannot flag video: Video does not exist");

    }

  }

  
  /** 
   * Attempts to allow (un-flag) a video. 
   * 
   * If a video doesn’t exist or is not currently flagged, display a warning message.
   * 
   * @param videoId
   */
  public void allowVideo(String videoId) {
    
    Video requestedVideo = videoLibrary.getVideo(videoId);

    if (requestedVideo != null) {

      boolean success = requestedVideo.unflag();

      if (success) {
        System.out.printf("Successfully removed flag from video: %s%n", requestedVideo.getTitle());
      } else {
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }

    } else {

      System.out.println("Cannot remove flag from video: Video does not exist");

    }
    
  }

}